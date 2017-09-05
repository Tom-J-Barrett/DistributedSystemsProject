package Services;

import entities.Client;
import entities.Clienttype;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Robbie
 * 
 * EJB used to create Client/User entities from the database.
 */
@Stateless
public class ClientService implements ClientServiceLocal {
    
    @PersistenceContext(unitName = "DistributedSystemsProject-ejbPU")
    private EntityManager em;
    
    /**Return client type based on ID. */
    @Override
    public Clienttype getClientType(int id) {
        String query = "SELECT c from Clienttype c WHERE c.typeid = ?1";
        Query q = em.createQuery(query);
        q.setParameter(1, id);
        
        return (Clienttype) q.getSingleResult();
    }
    
    /** Return client reference based on an ID*/
    @Override
    public Client getClient(Integer clientID) {
        String query = "SELECT c from Client c WHERE c.clientid = ?1";
        Query q = em.createQuery(query);
        q.setParameter(1, clientID);
        
        return (Client) q.getSingleResult();
    }
    
    /** Return client reference based on a username.*/
    @Override
    public Client getClient(String username) {
        String query = "SELECT c from Client c WHERE c.username = ?1";
        Query q = em.createQuery(query);
        q.setParameter(1, username);
        
        return (Client) q.getSingleResult();
    }
    
    /** Return all client objects registered in the database. */
    @Override
    public List<Client> getAllClients() {
        return null;
    }
    
    /** Get clients that match a username.*/
    @Override
    public List<Client> getMatchingClients(String username) {
        String sql = "SELECT p FROM Client p WHERE p.username LIKE ?1";
        TypedQuery<Client> typedQuery = em.createQuery(sql, Client.class);
        typedQuery.setParameter(1, "%" + username + "%");
        return typedQuery.getResultList();
    }
    
    /** Write a new client to the database.*/
    @Override
    public void persist(Client client) {
        em.persist(client);
        em.flush();
    }
    
    /** Update a client that exists already in the database.*/
    @Override
    public void update(Client client) {
        //merge client into persistence context
        em.merge(client);
        
        //force update
        em.flush();
        
        //empty cache
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    
}
