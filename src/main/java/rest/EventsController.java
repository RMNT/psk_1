package rest;

import com.google.gson.Gson;
import entities.Client;
import entities.Event;
import entities.Moderator;
import lombok.Getter;
import lombok.Setter;
import persistence.ClientsDAO;
import persistence.EventsDAO;
import rest.contracts.EventDto;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.SystemEvent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsController {

    @Inject
    @Setter @Getter
    private EventsDAO eventsDAO;

    @Inject
    @Getter @Setter
    private ClientsDAO clientsDAO;

    @Inject
    private EntityManager em;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final int id) {
        Event event = eventsDAO.findOne(id);
        if (event == null) {
            System.out.println("Pasiekia");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Gson gson = new Gson();
        EventDto eventDto = new EventDto();
        eventDto.setTitle(event.getTitle());
        eventDto.setClientTitle(event.getClient().getTitle());
        eventDto.setContractNumber(event.getContractNumber());

        return Response.ok(eventDto).build();
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Event create(EventDto eventDto) {
        Event event = new Event();
        Client client = new Client();
        client.setTitle(eventDto.getClientTitle());
        event.setClient(client);
        event.setTitle(eventDto.getTitle());
        event.setContractNumber(eventDto.getContractNumber());
        //event.setModerators(eventDto.getModerators());
        eventsDAO.persist(event);
        clientsDAO.persist(client);

        return event;
    }

    @Path("/{eventId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("eventId") final Integer eventId, EventDto eventData) {
        Event existingEvent = eventsDAO.findOne(eventId);
        try {
            if (existingEvent == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Gson gson = new Gson();
            //existingEvent.setId(eventId);
            existingEvent.setTitle(eventData.getTitle());
            Client client = new Client();
            client.setTitle(eventData.getClientTitle());
            existingEvent.setClient(client);
            existingEvent.setContractNumber(eventData.getContractNumber());
            clientsDAO.persist(client);
            eventsDAO.update(existingEvent);
            return Response.ok(eventData).build();
        } catch (OptimisticLockException ole) {
            System.out.println("OPT_LOC");
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
