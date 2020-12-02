package si.fri.prpo.skupina19.sledilnik.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.sledilnik.zrna.VrataZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("vrata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VrataVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    VrataZrno vrataZrno;

    @GET
    public Response getVrata(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long prostoriCount = vrataZrno.getVrataCount(query);
        return Response .ok(vrataZrno.getVrata(query)) .header("X-Total-Count", prostoriCount) .build();
    }


    @GET
    @Path("{id}")
    public Response getVrata(@PathParam("id") Integer id) {
        Vrata vrata = vrataZrno.getVrata(id);
        if (vrata != null) {
            return Response.ok(vrata).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createVrata(Vrata vrata) { //ali VrataDTO?
        vrataZrno.createVrata(vrata);
        if (vrata == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST).build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity(vrata)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateVrata(@PathParam("id") Integer id, Vrata vrata) {
        return Response
                .status(Response.Status.CREATED)
                .entity(vrataZrno.updateVrata(id, vrata))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteVrata(@PathParam("id") Integer id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(vrataZrno.deleteVrata(id))
                .build();
    }
}
