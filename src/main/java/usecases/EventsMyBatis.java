package usecases;

import lombok.Getter;
import lombok.Setter;
import mybatis.dao.EventMapper;
import mybatis.model.Event;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class EventsMyBatis {
    @Inject
    private EventMapper eventMapper;

    @Getter
    private List<Event> allEvents;

    @Getter @Setter
    private Event eventToCreate = new Event();

    @PostConstruct
    public void init() {
        this.loadAllEvents();
    }

    private void loadAllEvents() {
        this.allEvents = eventMapper.selectAll();
    }

    @Transactional
    public String createEvent() {
        eventMapper.insert(eventToCreate);
        return "/myBatis/events?faces-redirect=true";
    }
}
