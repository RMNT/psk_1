package usecases;


import entities.Event;
import lombok.Getter;
import lombok.Setter;
import interceptors.LoggedInvocation;
import persistence.EventsDAO;

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
public class UpdateEventDetails implements Serializable {

    private Event event;

    @Inject
    private EventsDAO eventsDAO;

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
}
