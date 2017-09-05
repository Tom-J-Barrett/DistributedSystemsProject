package Controllers.viewControllers;

import Services.CartServiceLocal;
import Services.ClientServiceLocal;
import Services.InventoryServiceLocal;
import Services.OrderServiceLocal;
import Services.ProductServiceLocal;
import entities.Cart;
import entities.Cartitem;
import entities.Client;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import utils.TextMessageProducer;

/**
 *
 * @author Robbie
 * 
 * Managed Bean/Controller for the cart. Session based - should be populated at the start of every session based
 * on the database entry for a users current cart.
 */
@Named(value = "CartController")
@RequestScoped
public class CartController{
    @EJB
    CartServiceLocal cartService;
    
    @EJB
    ClientServiceLocal clientService;
    
     @EJB
    ProductServiceLocal productService;
     
    @EJB
    InventoryServiceLocal inventoryService;
    
    @EJB
    OrderServiceLocal orderService;
    
    @Resource(mappedName = "newLoggingFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:app/LoggingQueue")
    private Queue queue;
    
    @Inject
    SessionManager sessionManager;
    
    //Message driven bean resources needed for accessing logging bean.
    Connection connection = null;               
    Session session = null;
    MessageProducer messageProducer = null;
    
    private List<Cartitem> products;
    private double totalCost;
    Cart cart;
     
    /**
     * Creates a new instance of Cart
     */
    public CartController() {
    
    }

    public String getTotalCost() {
        return Double.toString(totalCost);
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public Cart getCart() {
        return this.cart;
    }
 
    @PostConstruct
    public void populateCart(){
        String username = this.sessionManager.getUsername();
        
        Client client = clientService.getClient(username);
        
        //Get reference to cart object.
        this.cart = cartService.getClientCart(client);
        
        if(cart != null)
            this.totalCost = this.getTotalCost(this.cart);
        
        //Set products list
        setProducts(new ArrayList<>(cart.getCartitemCollection()));
        
        //Set up message driven bean connection
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);

        } catch (JMSException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /** This function calculates total cost based on the products in a users cart.*/
    private double getTotalCost(Cart cart) {
        double totalCost = 0;
        for(Cartitem ci: cart.getCartitemCollection()){
            totalCost+= ci.getProduct().getCost() * ci.getQuantity();
        }
        return totalCost;
    }
    
    /** Clear users cart, both in DB and locally in controller.*/
    public void clearCart(){
        //Empty the cart in the database.
        cartService.clearCart(this.cart);
        
        //Clear all references to products in the cart.
        products.clear();
        
        //Reset the total cost for the cart to zero.
        this.setTotalCost(0);
        
        //Log that the cart was cleared
        String username = this.cart.getClientid().getUsername();
        int cartId = this.cart.getCartid();
        String message = String.format("%s cleared their Cart (Cart ID  = %d)", username, cartId);
        TextMessageProducer.writeMessage(session, messageProducer, message);
        
        
    }

    public List getProducts() {
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }
    
    /** Update cart item object in persistence context */
    public void updateQuantity(Cartitem entry){
        cartService.merge(entry);
        this.totalCost = this.getTotalCost(this.cart);
    }
    
    /** Delete cartitem locally in controller and from persistence context.*/
    public void deleteEntry(Cartitem entry){
        removeFromSession(entry);
        cartService.delete(entry);
    }
    
    /** Remove item locally.*/
    private void removeFromSession(Cartitem entry){
        products.remove(entry);
    }
    
    /** Handles cart checkout.*/
    public void checkout() {
        this.cartService.checkoutCart(this.cart);
        
        String username = this.cart.getClientid().getUsername();
        int cartId = this.cart.getCartid();
        String message = String.format("%s checked out their Cart (Cart ID  = %d", username, cartId);

        //Log that the cart was checked out
        TextMessageProducer.writeMessage(session, messageProducer, message);
        
        //Empty controllers view of cart
        this.products.clear();
        
        //Reset total cost
        this.setTotalCost(0);
        
      
    }
}
