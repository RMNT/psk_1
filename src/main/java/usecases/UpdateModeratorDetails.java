package usecases;


import lombok.Getter;
import lombok.Setter;
import entities.Moderator;
import interceptors.LoggedInvocation;
import persistence.ModeratorsDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateModeratorDetails implements Serializable {

    private Moderator moderator;

    @Inject
    private ModeratorsDAO moderatorsDAO;

    @PostConstruct
    private void init() {
        System.out.println("UpdateModeratorDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer moderatorId = Integer.parseInt(requestParameters.get("moderatorId"));
        this.moderator = moderatorsDAO.findOne(moderatorId);
    }

    @Transactional
    @LoggedInvocation
    public String updateContractNumber() {
        try{
            moderatorsDAO.update(this.moderator);
        } catch (OptimisticLockException e) {
            return "/moderatorDetails.xhtml?faces-redirect=true&moderatorId=" + this.moderator.getId() + "&error=optimistic-lock-exception";
        }
        return "moderators.xhtml?eventId=" + this.moderator.getEvent().getId() + "&faces-redirect=true";
    }
}
