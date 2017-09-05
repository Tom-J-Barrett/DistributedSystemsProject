/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.componentControllers;

import Controllers.viewControllers.SessionManager;
import Services.ClientServiceLocal;
import entities.Client;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Robbie
 * 
 * Navigation controller used for managing links in the navbar.
 */
@Named(value = "navigationController")
@SessionScoped
public class NavigationController implements Serializable {
    
    @EJB
    ClientServiceLocal clientService;
    
    @Inject
    SessionManager sessionManager;
    
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Creates a new instance of navigationController
     */
    public NavigationController() {
        
    }
    
    @PostConstruct
    public void setUp() {
        this.username = this.sessionManager.getUsername();
    }
    
    private final String homePage = "home_page";
    private final String cartPage = "cart_page";
    private final String profilePage = "profile_page";
    private final String browsePage = "browse_page";
    private final String viewItems = "view_items_page";
    private final String addProductPage = "add_new_product_page";
    private final String productsPage = "products_page";

    public String getAddProductPage() {
        return addProductPage;
    }
    
    public String getProductsPage() {
        return this.productsPage;
    }

    public String getCartPage() {
        return this.cartPage;
    }

    public String getProfilePage() {
        return this.profilePage;
    }
    
    public String getViewItems() {
        return this.viewItems;
    }
    
    public String getBrowsePage() {
        return this.browsePage;
    }
    
    public String getHomePage() {
        return this.homePage;
    }
    
    public boolean isAdmin(){
        boolean admin=false;
        String username = this.sessionManager.getUsername();
        
        Client client = this.clientService.getClient(username);
        if(client.getTypeid().getTypename().equals("ADMIN")){
            admin=true;
        }
        return admin;
    }
    
}
