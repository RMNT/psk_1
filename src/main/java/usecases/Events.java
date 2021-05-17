package usecases;

import entities.Event;
import lombok.Getter;
import lombok.Setter;
import persistence.EventsDAO;
import entities.Event;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Events {

    @Inject
    private EventsDAO eventsDAO;

    @Getter @Setter
    private Event eventToCreate = new Event();

    @Getter
    private List<Event> allEvents;

    @PostConstruct
    public void init(){
        loadAllEvents();
    }

    @Transactional
    public String createEvent(){
        this.eventsDAO.persist(eventToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllEvents(){
        this.allEvents = eventsDAO.loadAll();
    }
}
