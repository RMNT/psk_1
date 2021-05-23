package usecases;

import entities.Event;
import interceptors.LoggedInvocation;
import lombok.Getter;
import lombok.Setter;
import entities.Moderator;
import persistence.EventsDAO;
import persistence.ModeratorsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ViewScoped
@Model
@Getter@Setter
public class ModeratorDetails implements Serializable {

    private Moderator moderator;

    @Inject
    private ModeratorsDAO moderatorsDAO;

    @Inject
    private EventsDAO eventsDAO;

    @Getter
    @Setter
    private Integer eventToAssignId;

    @Getter
    @Setter
    private Moderator moderatorToCreate = new Moderator();

    @PostConstruct
    public void CreateObject() {
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
        return "moderatorDetails.xhtml?moderatorId=" + this.moderator.getId() + "&faces-redirect=true";
    }

    @Transactional
    public String AssignToNewEvent() {
        List<Event> eventsList = moderator.getEvents();
        Event eventToAssign = eventsDAO.findOne(eventToAssignId);
        eventsList.add(eventToAssign);
        moderator.setEvents(eventsList);
        moderatorsDAO.update(moderator);

        return "moderatorDetails?faces-redirect=true&moderatorId=" + moderator.getId();
    }

    public List<Event> getFreeEvents() {
        List<Event> allEvents = eventsDAO.getAllEvents();
        List<Event> moderatorEvents = moderator.getEvents();

        return allEvents.stream().filter(s -> !moderatorEvents.contains(s)).collect(Collectors.toList());
    }
}