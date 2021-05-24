package usecases;

import lombok.Getter;
import lombok.Setter;
import mybatis.dao.ClientMapper;
import mybatis.dao.EventMapper;
import mybatis.model.Event;
import org.mybatis.cdi.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

public class ClientEventsMyBatis {
    @Inject
    ClientMapper clientMapper;

    @Inject
    EventMapper eventMapper;

    @Getter
    List<Event> events;

    @Getter @Setter
    Event eventToCreate = new Event();

    @PostConstruct
    public void init(Integer clientId) {this.loadAllEvents(clientId);}

    @Transactional
    private void loadAllEvents(Integer clientId) {
        this.events = eventMapper.selectAllByClient(clientId);
    }
}
