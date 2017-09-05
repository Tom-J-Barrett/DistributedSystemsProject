/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import Services.InventoryServiceLocal;
import entities.Inventory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Robbie
 * 
 * This controller is for the page that displays the result of a search. It should handle populating 
 * a list of items based on the search input. Request scoped as it should just need to populate once 
 * (should be able to paginate still - not a requirement for this project.).
 */
@Named(value = "displayItemsController")
@RequestScoped
public class DisplayItemsController {
    
    @EJB
    InventoryServiceLocal inventoryService;

    /**
     * Creates a new instance of DisplayItems
     */
    public DisplayItemsController() {
        
    }
    
    private Integer productId;
    private String productName;
    private List<Inventory> inventory = new ArrayList<>();
    
    public void setProductId(Integer productId) {
        this.productId = productId;
        
    }
    
    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductName() {
        return this.productName;
    }
    
    public List<Inventory> getInventory() {
        return this.inventory;
    }
    
    public void populateProductsData() {
        
        try {
            //Hacky way of making GET request work.
            if(productId != null && productId != -1) {
                //Product ID was entered, search based on this.
                inventory.add(inventoryService.getMatchingInventory(this.productId));
            } else if(!(productName == null || productName.equals("") )){
                //Product name was entered, search based on this.
                inventory.addAll(inventoryService.getMatchingInventory(this.productName));
            } else {
                //no product id or name - display all items.
                inventory.addAll(inventoryService.getInventory());
                
            }
        } catch(EJBException ex) {
            //no matching products.
        }
        
    }
    
}
