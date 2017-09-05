/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.componentControllers;

import Controllers.viewControllers.SessionManager;
import Services.ClientServiceLocal;
import entities.Client;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import utils.RedirectionManager;

/**
 *
 * @author Robbie
 * 
 * This managed bean handles user redirection to the login page, if they are not already logged in.
 */
@Named(value = "baseTemplateController")
@Dependent
public class BaseTemplateController {
    @EJB
    ClientServiceLocal clientService;
    
    @Inject
    SessionManager sessionManager;

    /**
     * Creates a new instance of BaseTemplateController
     */
    public BaseTemplateController() {
    }
    
    /** This function processes whether a user is logged in, and if not redirects them to the login page.*/
    public void processUserLoginStatus() {
        String username = sessionManager.getUsername();
        try {
            Client client = this.clientService.getClient(username);
            
        } catch(EJBException ex) {
            //Exception is thrown if the user doesn't exist in the db, so delete session and redirect to 
            //login page. 
            RedirectionManager.redirect("/views/login.xhtml");
        }
        
    }
    
}
