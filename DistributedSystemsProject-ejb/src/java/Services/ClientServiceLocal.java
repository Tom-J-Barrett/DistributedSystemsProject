package Services;

import entities.Client;
import entities.Clienttype;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Robbie
 * 
 * Interface for the ClientService EJB.
 */
@Local
public interface ClientServiceLocal {
    //Make JPQL call for a specific client.
    public Client getClient(Integer clientID);
    public Clienttype getClientType(int id);
    public Client getClient(String username);
    public List<Client> getMatchingClients(String username);
    public void persist(Client client);
    
    public void update(Client client);
    
    //Make JPQL call for all clients.
    public List<Client> getAllClients();
    
    
    
    
}
