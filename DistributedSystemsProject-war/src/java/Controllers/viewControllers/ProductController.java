/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import Services.CartServiceLocal;
import Services.ClientServiceLocal;
import Services.InventoryServiceLocal;
import entities.Cart;
import entities.Cartitem;
import entities.CartitemPK;
import entities.Client;
import entities.Inventory;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
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
 * Controller for the display product information page.
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable{
    
    @EJB
    InventoryServiceLocal inventoryService;
    
    @EJB
    ClientServiceLocal clientService;
    
    @EJB 
    CartServiceLocal cartService;
    
    @Inject
    SessionManager sessionManager;
    
    @Resource(mappedName = "newLoggingFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:app/LoggingQueue")
    private Queue queue;
    
    //Message driven bean resources needed for accessing logging bean.
    Connection connection = null;               
    Session session = null;
    MessageProducer messageProducer = null;

    private Inventory inventoryProduct;
    
    /**
     * Creates a new instance of productController
     */
    public ProductController() {
    }
    
    @PostConstruct
    public void setUp() {
        //Set up message driven bean connection
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);

        } catch (JMSException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Integer productId;
    private Integer amountToOrder = 0;
    private UIComponent addToCartButton;
    
    private int updateInventoryAmount;

    public Integer getAmountToOrder() {
        return amountToOrder;
    }
    
    public void setUpdateInventoryAmount(int updateInventoryAmount) {
        this.updateInventoryAmount = updateInventoryAmount;
    }
    
    public int getUpdateInventoryAmount() {
        return this.updateInventoryAmount;
    }

    public void setAmountToOrder(Integer amountToOrder) {
        this.amountToOrder = amountToOrder;
    }
    
    public void setAddToCartButton(UIComponent addToCartButton) {
        this.addToCartButton = addToCartButton;
    }
    
    public UIComponent getAddToCartButton() {
        return this.addToCartButton;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public Integer getProductId() {
        return this.productId;
    }
    
  
    
    public String getProductName() {
        if (inventoryProduct != null) {
            return this.inventoryProduct.getProduct().getProductname();
        }
        return "";
    }
    
    public String getProductDescription() {
        if(inventoryProduct != null ){
            return this.inventoryProduct.getProduct().getDescription();
        }
        return "";
    }
    
    public Double getProductCost() {
        if(inventoryProduct != null ){
            return this.inventoryProduct.getProduct().getCost();
        }
        
        return new Double(0);
    }
    
    public Integer getProductAmount() {
        if(inventoryProduct != null ){
            return this.inventoryProduct.getQuantity();
        }
        return 0;
    }
    
    public void populateProductInformation() {
        this.inventoryProduct = inventoryService.getMatchingInventory(this.productId);
    }
    
    /** Add a cartitem entity for this product to the users cart*/
    public String addProductToCart() {
        
        //Get reference to client object.
        String username = this.sessionManager.getUsername();
        Client client = clientService.getClient(username);
        
        //Get reference to cart object.
        Cart clientCart = cartService.getClientCart(client);
        
        //Create new CartItem object.
        Cartitem cartItem = new Cartitem();
        cartItem.setCartitemPK(new CartitemPK(clientCart.getCartid(), this.productId));
        cartItem.setQuantity(this.amountToOrder);
        //Update object through entity manager.
        cartService.persist(cartItem);
        
        return "home_page";
        
    }
    
    /** Update the inventory quantity - only available to admins.*/
    public void updateStock() {
        //Set the new product quantity.
        this.inventoryProduct.setQuantity(this.updateInventoryAmount);
        
        //update the product in the database
        this.inventoryService.update(inventoryProduct);
        
        
        String message = String.format("Inventory item %s quantity updated to %d", inventoryProduct.getProduct().getProductname(), inventoryProduct.getQuantity());
        
        //log that stock was updated
        TextMessageProducer.writeMessage(session, messageProducer, message);

    }
    
    /** Delete the product, only available to admins.*/
    public String removeProduct() {
        //remove the inventory item from the database.
        this.inventoryService.delete(this.inventoryProduct);
        
        String message = String.format("Inventory item %s removed", inventoryProduct.getProduct().getProductname());
        
        //log that stock was removed
        TextMessageProducer.writeMessage(session, messageProducer, message);
        
        //redirect to home
        return "home_page";
    }
}
