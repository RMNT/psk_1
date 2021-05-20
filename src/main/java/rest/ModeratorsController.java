package rest;

import entities.Moderator;
import lombok.Getter;
import lombok.Setter;
import persistence.ModeratorsDAO;
import rest.contracts.ModeratorDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/moderators")
public class ModeratorsController {

    @Inject
    @Setter @Getter
    private ModeratorsDAO moderatorsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Moderator moderator = moderatorsDAO.findOne(id);
        if (moderator == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ModeratorDto moderatorDto = new ModeratorDto();
        moderatorDto.setFullname(moderator.getFullname());
        //IDK kaip čia turi būt
        //moderatorDto.setClientTitle(moderator.getEvents().get());

        return Response.ok(moderatorDto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer moderatorId,
            ModeratorDto moderatorData) {
        try {
            Moderator existingModerator = moderatorsDAO.findOne(moderatorId);
            if (existingModerator == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingModerator.setFullname(moderatorData.getFullname());
            moderatorsDAO.update(existingModerator);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
