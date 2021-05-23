package rest;

import entities.Client;
import entities.Event;
import lombok.Getter;
import lombok.Setter;
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

@ApplicationScoped
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsController {

    @Inject
    @Setter @Getter
    private EventsDAO eventsDAO;

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

        EventDto eventDto = new EventDto();
        eventDto.setTitle(event.getTitle());
        eventDto.setClientTitle(event.getClient().getTitle());
        eventDto.setContractNumber(event.getContractNumber());
        eventDto.setModerators(event.getModerators());

        return Response.ok(eventDto).build();
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Event create(EventDto eventDto) {
        Event event = new Event();
        Client client =new Client();
        client.setTitle(eventDto.getClientTitle());
        event.setTitle(eventDto.getTitle());
        event.setClient(client);
        event.setContractNumber(eventDto.getContractNumber());
        event.setModerators(eventDto.getModerators());
        eventsDAO.persist(event);

        return event;
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer eventId,
            EventDto eventData) {
        try {
            Event existingEvent = eventsDAO.findOne(eventId);
            if (existingEvent == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingEvent.setTitle(eventData.getTitle());
            eventsDAO.update(existingEvent);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            System.out.println("OPT_LOC");
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
