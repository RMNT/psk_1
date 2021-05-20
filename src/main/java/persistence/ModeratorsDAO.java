package persistence;

import entities.Client;
import entities.Event;
import entities.Moderator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ModeratorsDAO {

    @Inject
    private EntityManager em;

    public void persist(Moderator moderator){
        this.em.persist(moderator);
    }

    public List<Moderator> getAllModerators(){
        return em.createNamedQuery("Moderator.findAll", Moderator.class).getResultList();
    }

    public Moderator findOne(Integer id) {
        return em.find(Moderator.class, id);
    }

    public Moderator update(Moderator moderator){
        return em.merge(moderator);
    }
}