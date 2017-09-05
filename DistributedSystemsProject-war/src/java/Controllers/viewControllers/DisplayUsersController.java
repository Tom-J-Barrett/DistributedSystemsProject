/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import Services.ClientServiceLocal;
import entities.Client;
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
 * Controller for display users page.
 */
@Named(value = "displayUsersController")
@RequestScoped
public class DisplayUsersController {
    
    @EJB
    ClientServiceLocal clientService;

    /**
     * Creates a new instance of DisplayUsersController
     */
    public DisplayUsersController() {
    }
    private Integer userId;
    private String username;
    private final List<Client> users = new ArrayList<>();
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public List<Client> getUsers() {
        return this.users;
    }
    
    public void populateUsersData() {
        
        try {
            //Hacky way of making GET request work.
            if(userId != null && userId != -1) {
                //User ID was entered, search based on this.
                users.add(clientService.getClient(this.userId));
            } else if(!(username == null || username.equals("") )){
                //user name was entered, search based on this.
                users.addAll(clientService.getMatchingClients(this.username));
            } 
        } catch(EJBException ex) {
            //no matching products.
        }
        
    }
}
