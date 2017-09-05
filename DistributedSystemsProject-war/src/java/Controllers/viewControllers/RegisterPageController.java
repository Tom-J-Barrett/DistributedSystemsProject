/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

import Services.CartServiceLocal;
import Services.ClientServiceLocal;
import entities.Cart;
import entities.Client;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Robbie
 * 
 * Controller for signing up new users.
 */
@Named(value = "registerPageController")
@RequestScoped
public class RegisterPageController {
    
    @EJB
    ClientServiceLocal clientService;
    
    @EJB
    CartServiceLocal cartService;
    
    
    @Inject
    SessionManager sessionManager;

    /**
     * Creates a new instance of RegisterPageController
     */
    public RegisterPageController() {
    }
    
    private UIComponent mybutton;
    
    String username;
    String password;
    String firstName;
    String surname;
    String message;
    String street1;
    String street2;
    String city;
    String county;
    String country;

    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }
    
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    /** Validate user inputted information and write client to db. */
    public String validateInformation() {
        try {
            Client checkUsername = clientService.getClient(this.username);
            FacesMessage message = new FacesMessage("This username already exists! ");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), message);
            return null;
        } catch(EJBException ex) {
            //Exception thrown if user does not exist.
        } 
        
        //Create new client
        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);
        client.setFirstname(firstName);
        client.setSurname(surname);
        client.setMessage(message);
        client.setTypeid(clientService.getClientType(0));
        client.setStreet1(street1);
        client.setStreet2(street2);
        client.setCity(city);
        client.setCounty(county);
        client.setCountry(country);
            
        //Add client to persistence context.
        clientService.persist(client);
        
        //Create empty cart for new client.
        Cart cart = new Cart();
        cart.setClientid(client);
        cartService.persist(cart);
        
        //Set the username for the client in the session manager.
        this.sessionManager.setUsername(this.username);
        return "LOGGED_IN";
    }
    
}
