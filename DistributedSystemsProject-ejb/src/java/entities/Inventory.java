package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robbie
 * 
 * Entity class representing the stock for the shop. 
 * 
 * Really it represents a single piece of inventory - one product and its corresponding amount.
 * We will need to have a database controller that reads out a list of these items.
 */
@Entity
@Table(name = "INVENTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i")
    , @NamedQuery(name = "Inventory.findByProductid", query = "SELECT i FROM Inventory i WHERE i.productid = :productid")
    , @NamedQuery(name = "Inventory.findByQuantity", query = "SELECT i FROM Inventory i WHERE i.quantity = :quantity")})
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    /*ProductID - Primary key for this table.*/
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTID")
    private Integer productid;
    
    /*Quantity - the amount of the product in inventory.*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    
    /*Product - product entity for this inventory item.*/
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Product product;
    
    /*Default constructor required for entity classes.*/
    public Inventory() {
    }
    
    /*Constructor for instantiation based on ProductID.*/
    public Inventory(Integer productid) {
        this.productid = productid;
    }
    
    /*Constructor for instantiation based of all attributes.*/
    public Inventory(Integer productid, int quantity) {
        this.productid = productid;
        this.quantity = quantity;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Inventory[ productid=" + productid + " ]";
    }
    
}
