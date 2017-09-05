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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Robbie
 * 
 * Entity class representing client carts. This entity class should be populated for the duration of each users session.
 */
@Entity
@Table(name = "CART")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
    , @NamedQuery(name = "Cart.findByCartid", query = "SELECT c FROM Cart c WHERE c.cartid = :cartid")})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*CartID - auto incremented primary key for cart entities.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CARTID")
    private Integer cartid;
    
    /*ClientID - client entity assoicated with this cart.*/
    @JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID")
    @ManyToOne(optional = false)
    private Client clientid;
    
    /*The CartItem collection assoicated with this cart.*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Collection<Cartitem> cartitemCollection;
    
    /*Default contructor - required for entity classes.*/
    public Cart() {
    }
    
    /*Constructor for instantiation based on Cart ID.*/
    public Cart(Integer cartid) {
        this.cartid = cartid;
    }

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    @XmlTransient
    public Collection<Cartitem> getCartitemCollection() {
        return cartitemCollection;
    }

    public void setCartitemCollection(Collection<Cartitem> cartitemCollection) {
        this.cartitemCollection = cartitemCollection;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartid != null ? cartid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartid == null && other.cartid != null) || (this.cartid != null && !this.cartid.equals(other.cartid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cart[ cartid=" + cartid + " ]";
    }
    
}
