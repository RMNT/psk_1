package persistence;

import entities.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class EventsDAO {

    @Inject
    private EntityManager em;

    public List<Event> loadAll() {
        return em.createNamedQuery("Event.findAll", Event.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Event event){
        this.em.persist(event);
    }

    public Event findOne(Integer id) {
        return em.find(Event.class, id);
    }
}
