/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import utils.RedirectionManager;

/**
 *
 * @author Robbie:
 * 
 * Managed Bean for the browse page form.
 */
@Named(value = "browseController")
@SessionScoped
public class BrowseController implements Serializable{

    /**
     * Creates a new instance of Browse
     */
    public BrowseController() {
    }
    
    private Integer productId;
    private String productName = "";
    
    private Integer userId;
    private String username;
    
    public void setProductId(Integer productId) {
        if(productId == null) {
            //hack done to allow get requests to work.
            this.productId = -1;
        } else {
            this.productId = productId;
        }
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
    
    public void setUserId(Integer userId) {
        if(userId == null) {
            //hack done to allow get requests to work.
            this.userId = -1;
        } else {
            this.userId = userId;
        }
    }
    
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }

    /** This function redirects to the view products page with query string parameters to allow linking the search.*/
    public void browseProducts() {
        RedirectionManager redirectManager = new RedirectionManager();
        String url = String.format("/views/display_items.xhtml?faces-redirect=true&productId=%d&productName=%s",this.productId, this.productName);
        redirectManager.redirect(url);
    }
    
    /** This function redirects to the view users page with query string parameters to allow linking the search.*/
    public void browseUsers() {
        RedirectionManager redirectManager = new RedirectionManager();
        String url = String.format("/views/display_users.xhtml?faces-redirect=true&userId=%d&username=%s",this.userId, this.username);
        redirectManager.redirect(url);
    }
}
