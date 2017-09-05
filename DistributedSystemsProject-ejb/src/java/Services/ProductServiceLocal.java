/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entities.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Robbie
 * 
 * Interface for the ProductService EJB.
 */
@Local
public interface ProductServiceLocal {
    
    //Make JPQL call to get all products.
    public List<Product> getProducts();
    
    public Product getMatchingProduct(int productId);
    public List<Product> getMatchingProducts(String productName);
    public List<Product> getAllProducts();
    
    public void persist(Product product);
}
