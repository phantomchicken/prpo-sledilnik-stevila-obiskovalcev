package si.fri.prpo.skupina19.sledilnik.api.v1.viri;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.sledilnik.zrna.ProstorZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("prostori")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProstorVir {
    @Inject
    ProstorZrno prostorZrno;

    @GET
    @Path("{id}")
    public Response getProstor(@PathParam("id") Integer id) {
        Prostor prostor = prostorZrno.getProstor(id);
        if (prostor != null) {
            return Response.ok(prostor).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createProstor(Prostor prostor) { //ali ProstorDTO?
        prostorZrno.createProstor(prostor);
        if (prostor == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST).build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity(prostor)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateProstor(@PathParam("id") Integer id, Prostor prostor) {
        return Response
                .status(Response.Status.CREATED)
                .entity(prostorZrno.updateProstor(id, prostor))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProstor(@PathParam("id") Integer id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(prostorZrno.deleteProstor(id))
                .build();
    }
}
