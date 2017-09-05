/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Robbie 
 * 
 * This is the controller for the home page, shouldn't really need to much actually.
 */
@Named(value = "homePageController")
@RequestScoped
public class HomePageController {

    @Inject
    SessionManager sessionManager;
    /**
     * Creates a new instance of HomePage
     */
    public HomePageController() {
    }
    
    @PostConstruct
    public void populate() {
        this.username = sessionManager.getUsername();
    }
    
    String username;
    
    public void setUsername(String usernname) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
}
