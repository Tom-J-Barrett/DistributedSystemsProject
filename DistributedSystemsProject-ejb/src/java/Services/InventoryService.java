package Services;

import entities.Cart;
import entities.Cartitem;
import entities.Inventory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Robbie
 * 
 * This service should handle reading inventory items (product + amount) from the database.
 */
@Stateless
public class InventoryService implements InventoryServiceLocal {

    @PersistenceContext(unitName = "DistributedSystemsProject-ejbPU")
    private EntityManager em;

    /** Get all inventory items. */
    @Override
    public List<Inventory> getInventory() {
        em.getEntityManagerFactory().getCache().evictAll();

        String sql = "SELECT i FROM Inventory i WHERE i.quantity >= 1";
        TypedQuery<Inventory> typedQuery = em.createQuery(sql, Inventory.class);;
        return typedQuery.getResultList();
    }
    
    /** Get an inventory item reference based on a product id.*/
    @Override
    public Inventory getMatchingInventory(int productId) {
        String sql = "SELECT i FROM Inventory i WHERE i.productid = ?1 AND i.quantity >= 1";
        TypedQuery<Inventory> typedQuery = em.createQuery(sql, Inventory.class);
        typedQuery.setParameter(1, productId);
        return typedQuery.getSingleResult();
    }

    /** Get a list of inventory items that match the product name entered. */
    @Override
    public List<Inventory> getMatchingInventory(String productName) {
        String sql = "SELECT i FROM Inventory i WHERE i.quantity >= 1 AND i.product.productname LIKE ?1";
        TypedQuery<Inventory> typedQuery = em.createQuery(sql, Inventory.class);
        typedQuery.setParameter(1, "%" + productName + "%");
        return typedQuery.getResultList();
    }
    
    /** Update stock levels based on a users cart. */
    @Override
    public boolean generateNewInventory(Cart cart) {
        Inventory inventory;
        List<Cartitem> products = new ArrayList<>(cart.getCartitemCollection());
        
        for(Cartitem item: products){
            //Get a reference to the correct inventory item for each user cart item.
            inventory = this.getMatchingInventory(item.getProduct().getProductid());
            
            //Add the entity object to the entity manager to ensure that it is updated.
            this.merge(inventory);
            
            //Update inventory quantity - total quantity - the amount in the users cart.
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                       
        }    
        return true;
    }
    
    /** Write a new inventory item into the persistence context.*/
    @Override
    public void persist(Inventory inventory) {
        //Writ new inventory item to persistence context.
        em.persist(inventory);
        
        //force write of new item
        em.flush();
    }
    
    /** Merge object into persistence context*/
    @Override
    public void merge(Object object) {
        em.merge(object);
    }
    
    /** Update an inventory item that exists in the persistence context. */
    @Override
    public void update(Inventory inventory) {
        //merge client into persistence context
        em.merge(inventory);
        
        //force update
        em.flush();
        
        //empty cache
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    /** Remove an item from the persistence context.*/
    @Override
    public void delete(Inventory inventory) {
        //merge client into persistence context.
        Object toBeDeleted = em.merge(inventory);
        
        //Remove item from persistence context.
        em.remove(toBeDeleted);
        
        //force deletion
        em.flush();
        
        //empty cache to ensure that the inventory item is immediately deleted.
        em.getEntityManagerFactory().getCache().evictAll();
        
        
    }
}
