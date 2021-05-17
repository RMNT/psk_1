package rest;

import entities.Event;
import lombok.Getter;
import lombok.Setter;
import persistence.EventsDAO;
import rest.contracts.EventDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/events")
public class EventsController {

    @Inject
    @Setter @Getter
    private EventsDAO eventsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Event event = eventsDAO.findOne(id);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        EventDto eventDto = new EventDto();
        eventDto.setTitle(event.getTitle());
        eventDto.setClientTitle(event.getClient().getTitle());

        return Response.ok(eventDto).build();
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
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
