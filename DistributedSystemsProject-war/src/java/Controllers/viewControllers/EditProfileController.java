/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.viewControllers;

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
 * @author tom13
 * 
 * Controller for the edit profile view.
 */
@Named(value = "editProfileController")
@SessionScoped
public class EditProfileController implements Serializable {
    @EJB
    ClientServiceLocal clientService;
    
    @Inject
    SessionManager sessionManager;
    
    private String username;
    private Client client;
    private String surname;
    private String firstname;
    private String message;
    private String street1;
    private String street2;
    private String city;
    private String county;
    private String country;

     /**
     * Creates a new instance of EditProfileController
     */
    public EditProfileController() {
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        System.out.println("set");
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
    
    @PostConstruct
    public void getProfileData(){
        //Get current username
        String username = this.sessionManager.getUsername();
        client = clientService.getClient(username);
        
        /*Populate inital data*/
        this.username=client.getUsername();
        this.firstname=client.getFirstname();
        this.surname=client.getSurname();
        this.message=client.getMessage();
        this.street1=client.getStreet1();
        this.street2=client.getStreet2();
        this.city=client.getCity();
        this.county=client.getCounty();
        this.country=client.getCountry();
    }
    
    public String updateProfileData(){
        //Update all fields
        this.client.setFirstname(this.firstname);
        this.client.setSurname(this.surname);
        this.client.setMessage(this.message);
        this.client.setStreet1(this.street1);
        this.client.setStreet2(this.street2);
        this.client.setCity(this.city);
        this.client.setCounty(this.county);
        this.client.setCountry(this.country);
        
        //Update client in persistence context
        this.clientService.update(this.client);
        
        return "home_page";
    }
    
}
