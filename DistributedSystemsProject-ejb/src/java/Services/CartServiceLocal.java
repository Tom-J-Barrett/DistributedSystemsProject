package Services;

import entities.Cart;
import entities.Cartitem;
import entities.Client;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Robbie
 * 
 * Interface for CartService EJB.
 */
@Local
public interface CartServiceLocal {
    public void persist(Object object);
    public Object merge(Object object);
    public void delete(Object object);
    public void clearCart(Cart cart);
    public Cart getClientCart(Client client);
    public void checkoutCart(Cart cart);
}
