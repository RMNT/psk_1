package persistence;

import entities.Moderator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ModeratorsDAO {

    @Inject
    private EntityManager em;

    public void persist(Moderator moderator){
        this.em.persist(moderator);
    }

    public Moderator findOne(Integer id){
        return em.find(Moderator.class, id);
    }

    public Moderator update(Moderator moderator){
        return em.merge(moderator);
    }
}
