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

    public List<Event> getAllEvents(){
        return em.createNamedQuery("Event.findAll", Event.class).getResultList();
    }

    public void persist(Event event){
        this.em.persist(event);
    }

    public Event findOne(Integer id){
        return em.find(Event.class, id);
    }

    public Event update(Event event) {
        return em.merge(event);
    }

    public void remove (Event event) {this.em.remove(event);}
}
