package usecases;

import lombok.Getter;
import lombok.Setter;
import entities.Event;
import persistence.EventsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class Events {

    @Inject
    private EventsDAO eventsDAO;

    @Getter @Setter
    private Event event = new Event();

    @Getter
    private List<Event> allEvents = new ArrayList<>();

    @PostConstruct
    public void createObject() {this.allEvents = eventsDAO.getAllEvents();}

    @Transactional
    public String createEvent(){
        this.eventsDAO.persist(event);
        return "index?faces-redirect=true";
    }

    public String deleteEvent(Event event, Integer clientId) {
        FacesContext fc = FacesContext.getCurrentInstance();
        //Map<String, String> paramMap = fc.getExternalContext().getRequestParameterMap();
        //String clientId = paramMap.get("clientId");
        //return "index?faces-redirect=true";
        System.out.println(event.getTitle());
        this.eventsDAO.remove(event);
        return "clientDetails?clientId="+clientId.toString();
    }
}