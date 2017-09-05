/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Robbie
 * 
 * Entity class representing clients (users).
 */
@Entity
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByClientid", query = "SELECT c FROM Client c WHERE c.clientid = :clientid")
    , @NamedQuery(name = "Client.findByUsername", query = "SELECT c FROM Client c WHERE c.username = :username")
    , @NamedQuery(name = "Client.findByPassword", query = "SELECT c FROM Client c WHERE c.password = :password")
    , @NamedQuery(name = "Client.findByFirstname", query = "SELECT c FROM Client c WHERE c.firstname = :firstname")
    , @NamedQuery(name = "Client.findBySurname", query = "SELECT c FROM Client c WHERE c.surname = :surname")
    , @NamedQuery(name = "Client.findByMessage", query = "SELECT c FROM Client c WHERE c.message = :message")
    , @NamedQuery(name = "Client.findByStreet1", query = "SELECT c FROM Client c WHERE c.street1 = :street1")
    , @NamedQuery(name = "Client.findByStreet2", query = "SELECT c FROM Client c WHERE c.street2 = :street2")
    , @NamedQuery(name = "Client.findByCity", query = "SELECT c FROM Client c WHERE c.city = :city")
    , @NamedQuery(name = "Client.findByCounty", query = "SELECT c FROM Client c WHERE c.county = :county")
    , @NamedQuery(name = "Client.findByCountry", query = "SELECT c FROM Client c WHERE c.country = :country")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*ClientID - auto incremented primary key for client objects.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIENTID")
    private Integer clientid;
    
    /*Username - unique field for each client.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USERNAME")
    private String username;
    
    /*Password - the user password.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "PASSWORD")
    private String password;
    
    /*Firstname - varchar limited to 15.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "FIRSTNAME")
    private String firstname;
    
    /*Surname - varchar limited to 15.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "SURNAME")
    private String surname;
    
    /*Message - message to be displayed on user profile. Varchar limited to 500.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "MESSAGE")
    private String message;
    
    /*Street1 - first line of street address in a users address. Varchar limited to 30.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "STREET1")
    private String street1;
    
    /*Street2 - second line of street address in a users address. Varchar limited to 30.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "STREET2")
    private String street2;
    
    /*City - city/town in a users address. Varchar limited to 30.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CITY")
    private String city;
    
    /*County - county/state in a users address. Varchar limited to 30.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "COUNTY")
    private String county;
    
    /*Country - country in a users address. Varchar limited to 30.*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "COUNTRY")
    private String country;
    
    /*Orders assoicated with a client.*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientid")
    private Collection<Clientproductorder> clientproductorderCollection;
    
    /*Type of client - could be normal user or admin.*/
    @JoinColumn(name = "TYPEID", referencedColumnName = "TYPEID")
    @ManyToOne(optional = false)
    private Clienttype typeid;
    
    /*The carts assoicated with a user - should be limited to one cart in reality.*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientid")
    private Collection<Cart> cartCollection;

    /*Default constructor required for entity classes.*/
    public Client() {
    }
    
    /*Constructor for instantiation based on ClientID*/
    public Client(Integer clientid) {
        this.clientid = clientid;
    }
    
    /*Constructor for instantiation based on all client attributes.*/
    public Client(Integer clientid, String username, String password, String firstname, String surname, String message, String street1, String street2, String city, String county, String country) {
        this.clientid = clientid;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.message = message;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.county = county;
        this.country = country;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    @XmlTransient
    public Collection<Clientproductorder> getClientproductorderCollection() {
        return clientproductorderCollection;
    }

    public void setClientproductorderCollection(Collection<Clientproductorder> clientproductorderCollection) {
        this.clientproductorderCollection = clientproductorderCollection;
    }

    public Clienttype getTypeid() {
        return typeid;
    }

    public void setTypeid(Clienttype typeid) {
        this.typeid = typeid;
    }

    @XmlTransient
    public Collection<Cart> getCartCollection() {
        return cartCollection;
    }

    public void setCartCollection(Collection<Cart> cartCollection) {
        this.cartCollection = cartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientid != null ? clientid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientid == null && other.clientid != null) || (this.clientid != null && !this.clientid.equals(other.clientid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Client[ clientid=" + clientid + " ]";
    }
    
}
