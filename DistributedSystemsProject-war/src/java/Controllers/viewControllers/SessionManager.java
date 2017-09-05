package Controllers.viewControllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Robbie
 * 
 * Session manager, stores username.
 */
@Named(value = "sessionManager")
@SessionScoped
public class SessionManager implements Serializable {
    
    private String username;
    /**
     * Creates a new instance of SessionManager
     */
    public SessionManager() {
    }
    
    public void setUsername(String username) {
        this.username  = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
}
