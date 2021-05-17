package usecases;

import lombok.Getter;
import lombok.Setter;
import entities.Moderator;
import entities.Event;
import interceptors.LoggedInvocation;
import persistence.ModeratorsDAO;
import persistence.EventsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
public class ModeratorsForEvent implements Serializable {

    @Inject
    private EventsDAO eventsDAO;

    @Inject
    private ModeratorsDAO moderatorsDAO;

    @Getter @Setter
    private Event event;

    @Getter @Setter
    private Moderator moderatorToCreate = new Moderator();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer eventId = Integer.parseInt(requestParameters.get("eventId"));
        this.event = eventsDAO.findOne(eventId);
    }

    @Transactional
    @LoggedInvocation
    public String createModerator() {
        moderatorToCreate.setEvent(this.event);
        moderatorsDAO.persist(moderatorToCreate);
        return "moderators?faces-redirect=true&eventId=" + this.event.getId();
    }
}
