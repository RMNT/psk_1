package usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import entities.Event;
import entities.Moderator;
import persistence.EventsDAO;
import persistence.ModeratorsDAO;

@Model
public class EventModerators implements Serializable
{
    @Inject
    private EventsDAO eventsDAO;

    @Inject
    private ModeratorsDAO moderatorsDAO;

    @Getter @Setter
    private Event event;

    @Getter @Setter
    private Moderator moderatorToCreate = new Moderator();

    @Getter@Setter
    private Integer moderatorToAssignId = 0;

    @PostConstruct
    public void createObject(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer eventId = Integer.parseInt(requestParameters.get("eventId"));
        this.event = eventsDAO.findOne(eventId);
    }

    @Transactional
    public String addModerators()
    {         List<Moderator> moderatorList = event.getModerators();
        Moderator moderatorToAssign = moderatorsDAO.findOne(moderatorToAssignId);
        if (!moderatorList.contains(moderatorToAssign)) {
            moderatorList.add(moderatorToAssign);
            event.setModerators(moderatorList);
            eventsDAO.update(event);
        }
        return "eventDetails?faces-redirect=true&eventId="+ this.event.getId();
    }
}