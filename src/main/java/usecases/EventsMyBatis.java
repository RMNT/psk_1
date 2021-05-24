package usecases;

import lombok.Getter;
import lombok.Setter;
import mybatis.dao.EventMapper;
import mybatis.model.Event;
import org.mybatis.cdi.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

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
}
