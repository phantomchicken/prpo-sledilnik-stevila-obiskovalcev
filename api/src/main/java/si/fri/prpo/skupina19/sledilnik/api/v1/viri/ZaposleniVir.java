package si.fri.prpo.skupina19.sledilnik.api.v1.viri;

import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;
import si.fri.prpo.skupina19.sledilnik.zrna.ZaposleniZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("zaposleni")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZaposleniVir {
    @Inject
    ZaposleniZrno zaposleniZrno;

    @GET
    @Path("{id}")
    public Response getZaposleni(@PathParam("id") Integer id) {
        Zaposleni zaposleni = zaposleniZrno.getZaposleni(id);
        if (zaposleni != null) {
            return Response.ok(zaposleni).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createZaposleni(Zaposleni zaposleni) { //ali ZaposleniDTO?
        zaposleniZrno.createZaposleni(zaposleni);
        if (zaposleni == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST).build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity(zaposleni)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateZaposleni(@PathParam("id") Integer id, Zaposleni zaposleni) {
        return Response
                .status(Response.Status.CREATED)
                .entity(zaposleniZrno.updateZaposleni(id, zaposleni))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteZaposleni(@PathParam("id") Integer id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(zaposleniZrno.deleteZaposleni(id))
                .build();
    }
}
