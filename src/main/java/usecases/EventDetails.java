package usecases;


import entities.Event;
import entities.Moderator;
import lombok.Getter;
import lombok.Setter;
import interceptors.LoggedInvocation;
import persistence.EventsDAO;
import persistence.ModeratorsDAO;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ViewScoped
@Named
@Getter @Setter
public class EventDetails implements Serializable {
    private Event event;

    @Inject
    private EventsDAO eventsDAO;

    @Inject
    private ModeratorsDAO moderatorsDAO;

    private Integer moderatorId;

    @Getter@Setter
    private Integer moderatorToAssignId;

    @PostConstruct
    private void init() {
        System.out.println("UpdateEventDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer eventId = Integer.parseInt(requestParameters.get("eventId"));
        this.event = eventsDAO.findOne(eventId);
    }

    @Transactional
    @LoggedInvocation
    public String updateContractNumber() {
        try{
            eventsDAO.update(this.event);
        } catch (OptimisticLockException e) {
            return "/eventDetails.xhtml?faces-redirect=true&eventId=" + this.event.getId() + "&error=optimistic-lock-exception";
        }
        return "events.xhtml?clientId=" + this.event.getClient().getId() + "&faces-redirect=true";
    }

    public List<Moderator> getFreeModerators(){
        List<Moderator> allModerators = moderatorsDAO.getAllModerators();
        List<Moderator> eventModerators = event.getModerators();

        return allModerators.stream().filter(s -> !eventModerators.contains(s)).collect(Collectors.toList());
    }

    @Transactional
    public String assignToNewModerator() {
        //Event - Employee
        //moderator - shift
        List<Moderator> moderatorList = event.getModerators();
        System.out.println(moderatorList);
        Moderator moderatorToAssign = moderatorsDAO.findOne(moderatorToAssignId);
        moderatorList.add(moderatorToAssign);
        event.setModerators(moderatorList);
        eventsDAO.update(event);

        return "eventDetails?faces-redirect=true&eventId="+ event.getId();
    }

}
