/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

/**
 *
 * @author Robbie
 * 
 * Simple wrapper for encapsulating redirect logic. 
 * 
 */
public class RedirectionManager {
    
    public static void redirect(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(fc, null, url);
        fc.renderResponse(); 
        
    }
    
}
