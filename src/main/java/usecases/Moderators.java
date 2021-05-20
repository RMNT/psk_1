package usecases;

import entities.Moderator;
import lombok.Getter;
import lombok.Setter;
import persistence.ModeratorsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Moderators {

    @Inject
    private ModeratorsDAO moderatorsDAO;

    @Getter @Setter
    private Moderator moderatorToCreate = new Moderator();

    @Getter
    private List<Moderator> allModerators;

    @PostConstruct
    public void init(){
        loadAllModerators();
    }

    @Transactional
    public String createModerator(){
        this.moderatorsDAO.persist(moderatorToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllModerators(){
        this.allModerators = moderatorsDAO.getAllModerators();
    }
}
