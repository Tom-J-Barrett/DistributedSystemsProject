package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Robbie
 * 
 * Entity class representing the types of Client available (Customer and Admin).
 */
@Entity
@Table(name = "CLIENTTYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clienttype.findAll", query = "SELECT c FROM Clienttype c")
    , @NamedQuery(name = "Clienttype.findByTypeid", query = "SELECT c FROM Clienttype c WHERE c.typeid = :typeid")
    , @NamedQuery(name = "Clienttype.findByTypename", query = "SELECT c FROM Clienttype c WHERE c.typename = :typename")})
public class Clienttype implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*TypeID - autoincremented primary key for Client Types.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TYPEID")
    private Integer typeid;
    
    /*TypeName - String representation of what this type of client actually is (i.e. Customer, Admin, etc).*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "TYPENAME")
    private String typename;
    
    /*A collection of all of the clients of this type - not really needed.*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private Collection<Client> clientCollection;
    
    /*Default constructor required for entity classes.*/
    public Clienttype() {
    }
    
    /*Constructor for instantiation based on TypeID. */
    public Clienttype(Integer typeid) {
        this.typeid = typeid;
    }
    
    /*Constructor for instantiation based on all atributes.*/
    public Clienttype(Integer typeid, String typename) {
        this.typeid = typeid;
        this.typename = typename;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @XmlTransient
    public Collection<Client> getClientCollection() {
        return clientCollection;
    }

    public void setClientCollection(Collection<Client> clientCollection) {
        this.clientCollection = clientCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeid != null ? typeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clienttype)) {
            return false;
        }
        Clienttype other = (Clienttype) object;
        if ((this.typeid == null && other.typeid != null) || (this.typeid != null && !this.typeid.equals(other.typeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Clienttype[ typeid=" + typeid + " ]";
    }
    
}
