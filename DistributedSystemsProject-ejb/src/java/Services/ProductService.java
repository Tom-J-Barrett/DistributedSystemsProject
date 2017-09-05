package Services;

import entities.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Robbie
 * 
 * This Service EJB should handle reading anything to do with Product entities from the database.
 */
@Stateless
public class ProductService implements ProductServiceLocal {
    
    
    @PersistenceContext(unitName = "DistributedSystemsProject-ejbPU")
    private EntityManager em;
    
    /** Get a matching Product reference based on product ID.*/
    @Override
    public Product getMatchingProduct(int productId) {
        String sql = "SELECT p FROM Product p WHERE p.productid = ?1";
        TypedQuery<Product> typedQuery = em.createQuery(sql, Product.class);
        typedQuery.setParameter(1, productId);
        return typedQuery.getSingleResult();
    }
    
    /** Get a list of Product references that match based on Product name */
    @Override
    public List<Product> getMatchingProducts(String productName) {
        String sql = "SELECT p FROM Product p WHERE p.productname LIKE ?1";
        TypedQuery<Product> typedQuery = em.createQuery(sql, Product.class);
        typedQuery.setParameter(1, "%" + productName + "%");
        return typedQuery.getResultList();
    }
    
    /** Get a list of all product references */
    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT p FROM Product p ";
        TypedQuery<Product> typedQuery = em.createQuery(sql, Product.class);
        return typedQuery.getResultList();
    }
    
    public void persist(Product product) {
        //Add new product to persistence context
        em.persist(product);
        
        //force write for new product
        em.flush();;
        
    }
}
