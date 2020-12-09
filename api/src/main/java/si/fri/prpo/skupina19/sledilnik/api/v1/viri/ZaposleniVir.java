package si.fri.prpo.skupina19.sledilnik.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.sledilnik.anotacije.BeleziKlice;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.VrataDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;
import si.fri.prpo.skupina19.sledilnik.zrna.UpravljanjePoslovnihMetod;
import si.fri.prpo.skupina19.sledilnik.zrna.ZaposleniZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@ApplicationScoped
@Path("zaposleni")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZaposleniVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    ZaposleniZrno zaposleniZrno;

    @Inject
    UpravljanjePoslovnihMetod upravljanjePoslovnihMetod;

    @GET
    public Response getZaposleni(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long prostoriCount = zaposleniZrno.getZaposleniCount(query);
        return Response .ok(zaposleniZrno.getZaposleni(query)) .header("X-Total-Count", prostoriCount) .build();
    }

    @Operation(description = "Vrne seznam zaposlenih.", summary = "Seznam zaposlenih",
        @APIResponses({
                @APIResponse(
                    responseCode = "200",
                    description = "Seznam zaposlenih",
                    content = @Content(
                            schema = @Schema(implementation = Zaposleni.class)
                    )
                    //schema = @Schema(implementation = Zaposleni.class)
                /*content = @Content(
                        array = @ArraySchema(
                                schema = @Schema(implementation = Zaposleni.class)
                        )),
                headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjenih zaposlenih")}*/
                )
        })
    )
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

    // Poslovna metoda 3: spremeni stanje stevila oseb v prostoru, kjer je podani zaposleni
    @PUT
    @Path("{id}/{vstopov}/{izstopov}")
    public Response updateStOseb(@PathParam("id") Integer id, @PathParam("vstopov") Integer vstopov, @PathParam("izstopov") Integer izstopov) {
        ZaposleniDTO zaposleniDTO = upravljanjePoslovnihMetod.getZaposleniDTOFromId(id);
        Zaposleni z = zaposleniZrno.getZaposleni(id);
        upravljanjePoslovnihMetod.spremeniSteviloOsebPoZaposlenim(zaposleniDTO,vstopov,izstopov);
        Vrata v = zaposleniDTO.getVrata();
        VrataDTO vrataDTO = upravljanjePoslovnihMetod.getVrataDTOFromId(v.getId());
        Prostor p = v.getProstor();
        ProstorDTO prostorDTO = upravljanjePoslovnihMetod.getProstorDTOFromId(p.getId());
        if (v!=null && p!=null)
        return Response
                .status(Response.Status.CREATED)
                .entity(prostorDTO.toString() +"\n" + vrataDTO.toString())
                .build();
        else return Response
                .status(Response.Status.NOT_FOUND).build();
    }
}
