/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robbie 
 * 
 * Entity class representing what is in effect line items for orders.
 */
@Entity
@Table(name = "PRODUCTORDERDETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productorderdetails.findAll", query = "SELECT p FROM Productorderdetails p")
    , @NamedQuery(name = "Productorderdetails.findByOrderid", query = "SELECT p FROM Productorderdetails p WHERE p.productorderdetailsPK.orderid = :orderid")
    , @NamedQuery(name = "Productorderdetails.findByProductid", query = "SELECT p FROM Productorderdetails p WHERE p.productorderdetailsPK.productid = :productid")
    , @NamedQuery(name = "Productorderdetails.findByQuantity", query = "SELECT p FROM Productorderdetails p WHERE p.quantity = :quantity")})
public class Productorderdetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*Line Item primary key - effectively OrderID, ProductID tuple.*/
    @EmbeddedId
    protected ProductorderdetailsPK productorderdetailsPK;
    
    /*Quantity - quantity that is assoicated with an order.*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    
    /*ClientProductOrder entity associated with this line item.*/
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientproductorder clientproductorder;
    
    /*Product entity assoicated with this line item.*/
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    
    /*Default constructor required for entity classes.*/
    public Productorderdetails() {
    }
    
    /*Contructor for instantiation using primary key tuple object.*/
    public Productorderdetails(ProductorderdetailsPK productorderdetailsPK) {
        this.productorderdetailsPK = productorderdetailsPK;
    }
    
    /*Constructor for instantiation using PK tuple object and quantity.*/
    public Productorderdetails(ProductorderdetailsPK productorderdetailsPK, int quantity) {
        this.productorderdetailsPK = productorderdetailsPK;
        this.quantity = quantity;
    }
    
    /*Constructor for instantiation using PK tuple.*/
    public Productorderdetails(int orderid, int productid) {
        this.productorderdetailsPK = new ProductorderdetailsPK(orderid, productid);
    }

    public ProductorderdetailsPK getProductorderdetailsPK() {
        return productorderdetailsPK;
    }

    public void setProductorderdetailsPK(ProductorderdetailsPK productorderdetailsPK) {
        this.productorderdetailsPK = productorderdetailsPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Clientproductorder getClientproductorder() {
        return clientproductorder;
    }

    public void setClientproductorder(Clientproductorder clientproductorder) {
        this.clientproductorder = clientproductorder;
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
        hash += (productorderdetailsPK != null ? productorderdetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productorderdetails)) {
            return false;
        }
        Productorderdetails other = (Productorderdetails) object;
        if ((this.productorderdetailsPK == null && other.productorderdetailsPK != null) || (this.productorderdetailsPK != null && !this.productorderdetailsPK.equals(other.productorderdetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Productorderdetails[ productorderdetailsPK=" + productorderdetailsPK + " ]";
    }
    
}
