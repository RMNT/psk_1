package persistence;

import entities.Client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ClientsDAO {

    @Inject
    private EntityManager em;

    public List<Client> loadAll() {
        return em.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Client client){
        this.em.persist(client);
    }

    public Client findOne(Integer id) {
        return em.find(Client.class, id);
    }
}
