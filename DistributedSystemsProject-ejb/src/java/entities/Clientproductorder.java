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
 * Entity class representing product orders that clients have made.
 */
@Entity
@Table(name = "CLIENTPRODUCTORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientproductorder.findAll", query = "SELECT c FROM Clientproductorder c")
    , @NamedQuery(name = "Clientproductorder.findByOrderid", query = "SELECT c FROM Clientproductorder c WHERE c.orderid = :orderid")})
public class Clientproductorder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*OrderID - auto incremented primary key for Customer Orders.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID")
    private Integer orderid;
    
    /*ClientID - client that made the order.*/
    @JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID")
    @ManyToOne(optional = false)
    private Client clientid;
    
    /*Collection of Order Line Items essentially.*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientproductorder")
    private Collection<Productorderdetails> productorderdetailsCollection;
    
    /*Default constructor required for entity classes.*/
    public Clientproductorder() {
    }
    
    /*Constructor for instantiation based on OrderID.*/
    public Clientproductorder(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @XmlTransient
    public Collection<Productorderdetails> getProductorderdetailsCollection() {
        return productorderdetailsCollection;
    }

    public void setProductorderdetailsCollection(Collection<Productorderdetails> productorderdetailsCollection) {
        this.productorderdetailsCollection = productorderdetailsCollection;
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
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientproductorder)) {
            return false;
        }
        Clientproductorder other = (Clientproductorder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Clientproductorder[ orderid=" + orderid + " ]";
    }
    
}
