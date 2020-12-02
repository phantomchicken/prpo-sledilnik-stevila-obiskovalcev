package si.fri.prpo.skupina19.sledilnik.api.v1.viri;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.zrna.ProstorZrno;
import si.fri.prpo.skupina19.sledilnik.zrna.UpravljanjePoslovnihMetod;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import com.kumuluz.ee.rest.beans.QueryParameters;

@ApplicationScoped
@Path("prostori")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProstorVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    ProstorZrno prostorZrno;

    @Inject
    UpravljanjePoslovnihMetod upravljanjePoslovnihMetod;

    @GET
    public Response getProstori(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long prostoriCount = prostorZrno.getProstoriCount(query);
        return Response .ok(prostorZrno.getProstori(query)) .header("X-Total-Count", prostoriCount) .build();
    }

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

    // Poslovna metoda 1: pridobi omejitev oseb v podanem prostoru
    @GET
    @Path("{id}/omejitev")
    public Response getOmejitev(@PathParam("id") Integer id){
        ProstorDTO prostorDTO = upravljanjePoslovnihMetod.getProstorDTOFromId(id);
        if (prostorDTO != null) {
            Integer omejitev = upravljanjePoslovnihMetod.getOmejitev(prostorDTO);
            return Response.ok(omejitev).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Poslovna metoda 2: doloci ce je v podanem prostoru ze presezena omejitev ljudi
    @GET
    @Path("{id}/presezeno")
    public Response getPresezenaMeja(@PathParam("id") Integer id){
        ProstorDTO prostorDTO = upravljanjePoslovnihMetod.getProstorDTOFromId(id);
        String odgovor;
        if (prostorDTO != null) {
            if(upravljanjePoslovnihMetod.presezenaMeja(prostorDTO))  odgovor = "Meja presezena!";
            else odgovor = "Meja ni presezena.";
            return Response.ok(odgovor).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
