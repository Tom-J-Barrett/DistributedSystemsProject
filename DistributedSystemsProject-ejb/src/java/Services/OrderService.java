/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entities.Cart;
import entities.Cartitem;
import entities.Client;
import entities.Clientproductorder;
import entities.Product;
import entities.Productorderdetails;
import entities.ProductorderdetailsPK;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tom13
 * 
 * This class manages interactions with Order database entities.
 */
@Stateless
public class OrderService implements OrderServiceLocal {
    
    @EJB
    ClientServiceLocal clientService;

    @PersistenceContext(unitName = "DistributedSystemsProject-ejbPU")
    private EntityManager em;
    
    /** Generates a client purchase order based on a cart */
    @Override
    public boolean createOrderFromCart(Cart cart) {
        //Create new order object.
        Clientproductorder order=new Clientproductorder();
        
        //Get reference to correct user
        Client client = cart.getClientid();
        
        //Set the orders user correctly.
        order.setClientid(client);
        
        //Persist the new client order record.
        em.persist(order);
        
        //Force the entity manager to write new order
        em.flush();
        
        List<Cartitem> products = new ArrayList<>(cart.getCartitemCollection());
        
        //Create and persist the product details entities now.
        for(Cartitem item: products){
            //Create new product order details.
            Productorderdetails orderDetails = new Productorderdetails();
            
            //Create primary key tuple for this order details object.
            ProductorderdetailsPK primaryKey = new ProductorderdetailsPK(order.getOrderid(), item.getProduct().getProductid());
            
            //Set the primary key.
            orderDetails.setProductorderdetailsPK(primaryKey);
            
            //Set the quantity.
            orderDetails.setQuantity(item.getQuantity());
            
            //Persist the orderDetails object.
            this.persist(orderDetails);
        }
        return true;
    }
    
    /** Write a new Clientproductorder object to the persistence context.*/
    @Override
    public void persist(Clientproductorder order){
        em.persist(order);
    }
    
    /** Write a Productorderdetails line item object to the persistence context.*/
    @Override
    public void persist(Productorderdetails orderDetails) {
        em.persist(orderDetails);
    }
    
}
