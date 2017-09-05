/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entities.Cart;
import entities.Clientproductorder;
import entities.Product;
import entities.Productorderdetails;
import javax.ejb.Local;

/**
 *
 * @author tom13
 */
@Local
public interface OrderServiceLocal {
    public boolean createOrderFromCart(Cart cart);
    public void persist(Clientproductorder order);
    public void persist(Productorderdetails orderDetails);
}
