package Controllers.viewControllers;

import Services.ClientServiceLocal;
import entities.Client;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import utils.RedirectionManager;

/**
 *
 * @author Robbie
 * 
 * Controller for the login page. Can link the views username and password to a field in this 
 * managed bean and then validate it against a user entity.
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable{
    @EJB
    private ClientServiceLocal clientService;
    
    @Inject
    SessionManager sessionManager;
    
    
    /**
     * Creates a new instance of Login Controller.
     */
    public LoginController() {
    }
    
    private String username;
    private String password;
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username != null) {
            this.username = username.toLowerCase();
        } else {
            this.username = null;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
    public String validateCredentials() {
        Client client = null;
        try {
            client = this.clientService.getClient(this.username);
        } catch(EJBException ex) {
            
        }
        
        //Check if this user exists.
        if(client == null) {
            //Force user to re enter
            return null;
        }
        
        //User exists, check if password is correct.
        if(client.getPassword().equals(this.password)) {
            //User has successfully logged in - store user details.
            sessionManager.setUsername(this.username);
            
            return "SUCCESS";
        }
        this.setUsername(null);
        this.setPassword(null);
        //Failed to authenticate user, force them to try again.
        return null;
    }
    
    public void logoutUser() { 
        //User is logged out - no longer store username or password.
        this.setUsername(null);
        this.setPassword(null);
   
        
        //Delete the session now that the user has logged out.
        this.sessionManager.setUsername(null);
            
        //Redirect to the login page.
        RedirectionManager.redirect("/views/login.xhtml");
    }
    
    /** This function checks to see if the user is logged in, if so redirects to correct home page.*/
    public void processUserLoginStatus() {
        
        if(this.username == null || this.username == "") {
            //user not logged in.
        }
        
        //Get username from session.
        String username = this.sessionManager.getUsername();
        Client client = null;
        try {
            client = this.clientService.getClient(username);
            
        } catch(EJBException ex) {
        } finally {
            if(client != null) {
                //No exception thrown and client not null - user is logged in and therefore shouldn't be in the login page.    
                if(client.getTypeid().getTypename().equals("CUSTOMER")) {
                    //User is customer, so normal homepage:
                    RedirectionManager.redirect("/views/home_page.xhtml");
                }
                if(client.getTypeid().getTypename().equals("ADMIN")) {
                    //User is customer, so admin homepage:
                    RedirectionManager.redirect("/views/admin_home_page.xhtml");
                }
            }
        }
        
    }
    
    
    
}
