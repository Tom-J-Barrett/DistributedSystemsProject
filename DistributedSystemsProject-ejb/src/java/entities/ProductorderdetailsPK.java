/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Robbie
 * 
 * This object represents the primary key in a Product Order item entity. 
 * 
 * This PK is a tuple containing the ClientProductOrderID and the ProductID.
 */
@Embeddable
public class ProductorderdetailsPK implements Serializable {

    /*OrderID - the OrderID this order line is assoicated with.*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERID")
    private int orderid;
    
    /*ProductID - the ProductID this order line is assoicated with.*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTID")
    private int productid;

    public ProductorderdetailsPK() {
    }

    public ProductorderdetailsPK(int orderid, int productid) {
        this.orderid = orderid;
        this.productid = productid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderid;
        hash += (int) productid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductorderdetailsPK)) {
            return false;
        }
        ProductorderdetailsPK other = (ProductorderdetailsPK) object;
        if (this.orderid != other.orderid) {
            return false;
        }
        if (this.productid != other.productid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProductorderdetailsPK[ orderid=" + orderid + ", productid=" + productid + " ]";
    }
    
}
