package persistence;

import entities.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class EventsDAO {

    @Inject
    private EntityManager em;

    public void persist(Event event){
        this.em.persist(event);
    }

    public Event findOne(Integer id){
        return em.find(Event.class, id);
    }

    public Event update(Event event){
        return em.merge(event);
    }
}
