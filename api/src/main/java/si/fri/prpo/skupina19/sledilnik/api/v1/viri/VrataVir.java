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
import si.fri.prpo.skupina19.sledilnik.zrna.UpravljanjeProstorovZrno;
import si.fri.prpo.skupina19.sledilnik.zrna.UpravljanjeVrataZrno;
import si.fri.prpo.skupina19.sledilnik.zrna.UpravljanjePoslovnihMetod;
import si.fri.prpo.skupina19.sledilnik.zrna.ZaposleniZrno;
import si.fri.prpo.skupina19.sledilnik.zrna.VrataZrno;
import si.fri.prpo.skupina19.sledilnik.zrna.ProstorZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import com.kumuluz.ee.cors.annotations.CrossOrigin;

@ApplicationScoped
@Path("vrata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class VrataVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    VrataZrno vrataZrno;

    @Inject
    ProstorZrno prostorZrno;

    @Inject
    UpravljanjeProstorovZrno upravljanjeProstorovZrno;

    @Inject
    UpravljanjeVrataZrno upravljanjeVrataZrno;

    @GET
    @Operation(summary = "Pridobi podrobnosti vrat", description = "Vrne podrobnosti vrat.")
    @Tag(name="GET")
    @APIResponses({
            @APIResponse(description = "Podrobnosti vrat",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Vrata.class)),
                    headers = {@Header(name = "X-Total-Count",
                            description = "Stevilo vrnjenih vrat")}),
            @APIResponse(description = "Vrata niso najdena!", responseCode = "404" )
    })
    public Response getVrata(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long prostoriCount = vrataZrno.getVrataCount(query);
        return Response .ok(vrataZrno.getVrata(query)) .header("X-Total-Count", prostoriCount) .build();
    }


    @GET
    @Path("{id}")
    @Operation(summary = "Pridobi podrobnosti vrat", description = "Vrne podrobnosti vrat.")
    @Tag(name="GET")
    @APIResponses({
            @APIResponse(description = "Podrobnosti vrat", responseCode = "200", content = @Content(schema = @Schema(implementation = Vrata.class))),
            @APIResponse(description = "Vrata niso najdena!", responseCode = "404")
    })
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
    @Path("dodaj/{idProstora}/{vstopov}/{izstopov}")
    @Operation(summary = "Kreiraj nova vrata", description = "Ustvari nova vrata.")
    @Tag(name="POST")
    @APIResponses({
            @APIResponse(description = "Ustvarjena nova vrata", responseCode = "201", content = @Content(schema = @Schema(implementation = Vrata.class))),
            @APIResponse(description = "vrata ze obstajajo!", responseCode = "409", content = @Content(schema = @Schema(implementation = Vrata.class)))
    })
    public Response createVrata(@PathParam("idProstora") Integer id, @PathParam("vstopov") Integer vstopov, @PathParam("izstopov") Integer izstopov) { //ali VrataDTO?
        VrataDTO vrataDTO = new VrataDTO();
        vrataDTO.setStVstopov(vstopov);
        vrataDTO.setStIzstopov(izstopov);
        Prostor p = prostorZrno.getProstor(id);
        vrataDTO.setProstor(p);
        Vrata vrata = upravljanjeVrataZrno.createVrata(vrataDTO);
        p.setStVrat(p.getStVrat()+1);
        List<Vrata> vr = p.getSeznamVrat();
        vr.add(vrata);
        p.setSeznamVrat(vr);
        prostorZrno.updateProstor(id, p);
        //vrataZrno.createVrata(vrata);
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
    @Operation(summary = "Posodabljanje vrat", description = "Posodobi vrata.")
    @Tag(name="PUT")
    @APIResponses({
            @APIResponse(description = "Posodobljena vrata", responseCode = "200", content = @Content(schema = @Schema(implementation = Vrata.class))),
            @APIResponse(description = "Vrata niso najdena!", responseCode = "404")
    })
    public Response updateVrata(@PathParam("id") Integer id, Vrata vrata) {
        return Response
                .status(Response.Status.CREATED)
                .entity(vrataZrno.updateVrata(id, vrata))
                .build();
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Brisanje vrat", description = "Pobriše vrata.")
    @Tag(name="DELETE")
    @APIResponses({
            @APIResponse(description = "Izbrisan vrata", responseCode = "204", content = @Content(schema = @Schema(implementation = Vrata.class))),
            @APIResponse(description = "Vrata niso najdena!", responseCode = "404")
    })
    public Response deleteVrata(@PathParam("id") Integer id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(vrataZrno.deleteVrata(id))
                .build();
    }
}
