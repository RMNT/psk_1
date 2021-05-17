package usecases;

import lombok.Getter;
import lombok.Setter;
import entities.Event;
import entities.Client;
import interceptors.LoggedInvocation;
import persistence.ClientsDAO;
import persistence.EventsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
public class EventsForClient implements Serializable {

    @Inject
    private ClientsDAO clientsDAO;

    @Inject
    private EventsDAO eventsDAO;

    @Getter @Setter
    private Client client;

    @Getter @Setter
    private Event eventToCreate = new Event();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer clientId = Integer.parseInt(requestParameters.get("clientId"));
        this.client = clientsDAO.findOne(clientId);
    }

    @Transactional
    @LoggedInvocation
    public String createEvent() {
        eventToCreate.setClient(this.client);
        eventsDAO.persist(eventToCreate);
        return "events?faces-redirect=true&clientId=" + this.client.getId();
    }
}
