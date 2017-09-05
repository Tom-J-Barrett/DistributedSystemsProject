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
 * This object represents the primary key in a Cart Item entity. 
 * 
 * This PK is a tuple containing the CartID and the ProductID.
 */
@Embeddable
public class CartitemPK implements Serializable {
    
    /*CartID - The cart this item is associated with.*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "CARTID")
    private int cartid;
    
    /*ProductID - The Product this item is associated with.*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTID")
    private int productid;

    public CartitemPK() {
    }

    public CartitemPK(int cartid, int productid) {
        this.cartid = cartid;
        this.productid = productid;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
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
        hash += (int) cartid;
        hash += (int) productid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartitemPK)) {
            return false;
        }
        CartitemPK other = (CartitemPK) object;
        if (this.cartid != other.cartid) {
            return false;
        }
        if (this.productid != other.productid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CartitemPK[ cartid=" + cartid + ", productid=" + productid + " ]";
    }
    
}
