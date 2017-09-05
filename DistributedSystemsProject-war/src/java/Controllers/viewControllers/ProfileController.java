package Controllers.viewControllers;

import Services.ClientServiceLocal;
import entities.Client;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Robbie
 * 
 * Controller for the profile page. Should be able to read data straight from a user 
 * entity that has been received through the use of the user EJB service.
 */
@Named(value = "profileController")
@RequestScoped
public class ProfileController {
    @EJB
    ClientServiceLocal clientService;
    
    @Inject
    SessionManager sessionManager;
    
    private Client client;
    
    public Client getClient() {
        return this.client;
    }
    
    private String username;
    
    private String surname;
    private String firstname;
    private String message;
    private String street1;
    private String street2;
    private String city;
    private String county;
    private String country;

    
    
    
    /**
     * Creates a new instance of Profile
     */
    public ProfileController() {
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getSurname() {
        return this.client.getSurname();
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return this.client.getFirstname();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMessage() {
        return this.client.getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStreet1() {
        return this.client.getStreet1();
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return this.client.getStreet2();
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return this.client.getCity();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return this.client.getCounty();
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return this.client.getCountry();
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public void getProfileData(){
        //Get current username
        this.client = clientService.getClient(this.username);
    }
    
    /** This function determines whether or not to load the edit profile button when viewing a profile - will only display when viewing own profile.*/
    public boolean editButton(){
        String storedUsername = this.sessionManager.getUsername();
        if(username != null && username.equals(storedUsername))
            return true;
        
        return false;
    }
}
