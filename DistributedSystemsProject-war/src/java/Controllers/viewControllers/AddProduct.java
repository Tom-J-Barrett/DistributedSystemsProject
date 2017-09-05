/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import Services.ClientServiceLocal;
import Services.InventoryServiceLocal;
import Services.ProductServiceLocal;
import entities.Client;
import entities.Inventory;
import entities.Product;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import utils.RedirectionManager;
import utils.TextMessageProducer;

/**
 *
 * @author tom13
 * 
 * Admin page allowing admins to add new products to the database.
 */
@Named(value = "addProduct")
@RequestScoped
public class AddProduct {
    @EJB
    ProductServiceLocal productbean;
    
    @EJB
    InventoryServiceLocal inventoryService;
    
    @EJB
    ClientServiceLocal clientService;
    
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
    
    private String productName;
    private Double cost;
    private String description;
    private int quantity;
    
    /**
     * Creates a new instance of AddProduct
     */
    public AddProduct() {
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
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return this.quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
    public void update(){
        //Create new product object.
        Product product = new Product();
        
        //Set relevent fields.
        product.setProductname(this.productName);
        product.setCost(this.cost);
        product.setDescription(this.description);
        
        //Add new product reference to persistence context.
        productbean.persist(product);
        
        //Create new inventory item based on new product item.
        Inventory inventory = new Inventory();
        inventory.setProductid(product.getProductid());
        inventory.setProduct(product);
        inventory.setQuantity(this.quantity);
        
        //write new inventory object to persistence context.
        this.inventoryService.persist(inventory);
        
        String message = String.format("New inventory item %s added to the system. Initial stock was set to %d.", inventory.getProduct().getProductname(), inventory.getQuantity());
        
        //Log new product being added to database
        TextMessageProducer.writeMessage(session, messageProducer, message);
    }
    
    /** This function handles ensuring that a user is an admin.*/
    public void processUserType() {
        String username = this.sessionManager.getUsername();
        Client client = null;
        try {
            client = this.clientService.getClient(username);
            
        } catch(EJBException ex) {
        } finally {
            if(client != null) {
                //Check if user is a customer, if so - redirect.
                if(client.getTypeid().getTypename().equals("CUSTOMER")) {
                    //User is customer, so normal homepage:
                    RedirectionManager.redirect("/views/home_page.xhtml");
                }
            }
        }
    }
}
