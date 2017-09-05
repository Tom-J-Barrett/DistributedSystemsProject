package Services;

import entities.Cart;
import entities.Inventory;
import entities.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Robbie
 * 
 * Interface for the InventoryService EJB.
 */
@Local
public interface InventoryServiceLocal {
    //Make JPQL call to get the inventory items for the shop.
    public List<Inventory> getInventory();
    
    public Inventory getMatchingInventory(int productId);
    public List<Inventory> getMatchingInventory(String productName);
    public void persist(Inventory inventory);
    public void merge(Object object);
    public boolean generateNewInventory(Cart cart);
    public void update(Inventory inventory);
    public void delete(Inventory inventory);
}
