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
 * Entity class representing items that are in a users cart.
 */
@Entity
@Table(name = "CARTITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cartitem.findAll", query = "SELECT c FROM Cartitem c")
    , @NamedQuery(name = "Cartitem.findByCartid", query = "SELECT c FROM Cartitem c WHERE c.cartitemPK.cartid = :cartid")
    , @NamedQuery(name = "Cartitem.findByProductid", query = "SELECT c FROM Cartitem c WHERE c.cartitemPK.productid = :productid")
    , @NamedQuery(name = "Cartitem.findByQuantity", query = "SELECT c FROM Cartitem c WHERE c.quantity = :quantity")})
public class Cartitem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*Primary key for CartItem entities - consists of CartID and ProductID. */
    @EmbeddedId
    protected CartitemPK cartitemPK;
    
    /*CardID - The cart that this item is a part of. */
    @JoinColumn(name = "CARTID", referencedColumnName = "CARTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cart cart;
    
    /*ProductID - The product assoicated with this cart item.*/
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    
    /*Quantity - int used to represent quantity of item in cart. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    
    /*Default constructor required for entity classes.*/
    public Cartitem() {
    }
    
    /*Contructor for instantiation based on primary key tuple object.*/
    public Cartitem(CartitemPK cartitemPK) {
        this.cartitemPK = cartitemPK;
    }
    
    /*Contructor for instantiation based on primary key tuple object and quantity.*/
    public Cartitem(CartitemPK cartitemPK, int quantity) {
        this.cartitemPK = cartitemPK;
        this.quantity = quantity;
    }
    
    /*Contructor for instantiation based on primary key tuple.*/
    public Cartitem(int cartid, int productid) {
        this.cartitemPK = new CartitemPK(cartid, productid);
    }

    public CartitemPK getCartitemPK() {
        return cartitemPK;
    }

    public void setCartitemPK(CartitemPK cartitemPK) {
        this.cartitemPK = cartitemPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
        hash += (cartitemPK != null ? cartitemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cartitem)) {
            return false;
        }
        Cartitem other = (Cartitem) object;
        if ((this.cartitemPK == null && other.cartitemPK != null) || (this.cartitemPK != null && !this.cartitemPK.equals(other.cartitemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cartitem[ cartitemPK=" + cartitemPK + " ]";
    }
    
}
