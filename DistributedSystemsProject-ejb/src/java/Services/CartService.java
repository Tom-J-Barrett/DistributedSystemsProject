package Services;

import entities.Cart;
import entities.Cartitem;
import entities.Client;
import entities.Inventory;
import entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Robbie
 * 
 * Should be used to read the cart entity from the database based on a user, I guess.
 */
@Stateless
public class CartService implements CartServiceLocal {
    
    @EJB
    InventoryServiceLocal inventoryService;
    
    @EJB
    OrderServiceLocal orderService;

    @Override
    public Cart getClientCart(Client client) {
        //empty cache to ensure client gets latest cart.
        em.getEntityManagerFactory().getCache().evictAll();
        
        String sql ="SELECT c FROM Cart c WHERE c.clientid=?1";
        TypedQuery<Cart> typedQuery= em.createQuery(sql, Cart.class);
        typedQuery.setParameter(1, client);
        return typedQuery.getSingleResult();
    }

    @PersistenceContext(unitName = "DistributedSystemsProject-ejbPU")
    private EntityManager em;
    
    /** Persist a new entity object using the entity manager.*/
    @Override
    public void persist(Object object) {
        em.persist(object);
        em.flush();
    }
    
    /** Allow an object to be managed by the entity manager.*/
    @Override
    public Object merge(Object object) {
        Object temp = em.merge(object);
        
        em.flush();
        
        //empty cache
        em.getEntityManagerFactory().getCache().evictAll();
        return temp;
    }
    
    /** Delete an object from the database using the entity manager.*/
    @Override
    public void delete(Object object){
        Object entityToBeDeleted = this.merge(object);
        
        //Remove the entity from that database.
        em.remove(entityToBeDeleted);
        
        em.flush();
        
        //empty cache
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    /** Removes all items from a users cart.*/
    @Override
    public void clearCart(Cart cart){
        //Create an arrayList using the Cart Item collection in the cart entity class.
        List<Cartitem> cartItems = new ArrayList<>(cart.getCartitemCollection());
        
        //For all the items in the collection, add them to the persistence context and then
        //remove them from the database.
        cartItems.stream().map((item) -> em.merge(item)).forEachOrdered((object) -> {
            em.remove(object);
        });
        
        //Force delete to happen now
        em.flush();
        
        //empty cache
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    @Override
    public void checkoutCart(Cart cart) {
        //Ensure there is enough stock.
        if(!this.checkStock(cart))
            return;
        
        //Take stock from inventory. 
        this.calculateInventory(cart);
        
        //Generate purchase order.
        this.generatePurchaseOrder(cart);
        
        //Delete current users cart, create new one.
        this.resetCart(cart);
        
        em.flush();
        
        //empty cache
        em.getEntityManagerFactory().getCache().evictAll();
        
    }
    
    /** Check to make sure that there is enough stock.*/
    private boolean checkStock(Cart cart) {
        List<Cartitem> products = new ArrayList<>(cart.getCartitemCollection());
        
        Product product;
        int quantity;
        
        for(Cartitem item: products) {
            //Get the product in the cart item.
            product = item.getProduct();
            quantity = item.getQuantity();
            
            //Get the inventory item assoicated with this product.
            Inventory inventory = inventoryService.getMatchingInventory(product.getProductid());
            
            //Check to make sure there is enough product in inventory.
            if(inventory.getQuantity() < quantity) {
                //checkout failed.
                return false;
            }
        }
        return true;
    }
    
    /** Calculate the new inventory amounts */
    private boolean calculateInventory(Cart cart) {
        return this.inventoryService.generateNewInventory(cart);
    }
    
    /** Generate the purchase order for this cart. */
    private boolean generatePurchaseOrder(Cart cart) {
        return this.orderService.createOrderFromCart(cart);
    }
    
    /** Reset the cart for the user that performed the cart checkout. */
    private boolean resetCart(Cart cart) {
        //Get reference to cart
        Client client = cart.getClientid();
        
        //Get reference to object after it has been merged into the persistence context.
        Object cartToDelete = this.merge(cart);
        
        //Remove the project from te database.
        em.remove(cartToDelete);
        
        //Create a new cart
        Cart newCart = new Cart();
        
        //Set it to the new client.
        newCart.setClientid(client);
        
        //Write the new cart to the database
        em.persist(newCart);
        return true;
    }
    
}
