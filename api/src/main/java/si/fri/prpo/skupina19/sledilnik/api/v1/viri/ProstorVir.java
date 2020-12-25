package si.fri.prpo.skupina19.sledilnik.api.v1.viri;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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

import com.kumuluz.ee.cors.annotations.CrossOrigin;

@ApplicationScoped
@Path("prostori")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supporetedMethods = "GET, POST, HEAD, DELETE, OPTIONS");
public class ProstorVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    ProstorZrno prostorZrno;

    @Inject
    UpravljanjePoslovnihMetod upravljanjePoslovnihMetod;

    @GET
    @Operation(summary = "Pridobi podrobnosti prostorov", description = "Vrne podrobnosti prostorov.")
    @Tag(name="GET")
    @APIResponses({
            @APIResponse(description = "Podrobnosti prostorov", responseCode = "200", content = @Content(schema = @Schema(implementation = Prostor.class)), headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjenih prostorov")}),
            @APIResponse(description = "Prostori niso najdeni!", responseCode = "404" )
    })
    public Response getProstori(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        Long prostoriCount = prostorZrno.getProstoriCount(query);
        return Response .ok(prostorZrno.getProstori(query)) .header("X-Total-Count", prostoriCount) .build();
    }

    @GET
    @Operation(summary = "Pridobi podrobnosti prostorov", description = "Vrne podrobnosti prostorov.")
    @Tag(name="GET")
    @APIResponses({
            @APIResponse(description = "Podrobnosti prostora", responseCode = "200", content = @Content(schema = @Schema(implementation = Prostor.class))),
            @APIResponse(description = "Prostor ni najden!", responseCode = "404")
    })
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
    @Operation(summary = "Kreiraj novi prostor", description = "Ustvari nov prostor.")
    @Tag(name="POST")
    @APIResponses({
            @APIResponse(description = "Ustvarjen novi prostor", responseCode = "201", content = @Content(schema = @Schema(implementation = Prostor.class))),
            @APIResponse(description = "Prostor ze obstaja!", responseCode = "409", content = @Content(schema = @Schema(implementation = Prostor.class)))
    })
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
    @Operation(summary = "Posodabljanje prostora", description = "Posodobi prostor.")
    @Tag(name="PUT")
    @APIResponses({
            @APIResponse(description = "Posodobljen prostor", responseCode = "200", content = @Content(schema = @Schema(implementation = Prostor.class))),
            @APIResponse(description = "Prostor ni najden!", responseCode = "404")
    })
    public Response updateProstor(@PathParam("id") Integer id, Prostor prostor) {
        return Response
                .status(Response.Status.CREATED)
                .entity(prostorZrno.updateProstor(id, prostor))
                .build();
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Brisanje prostora", description = "Pobriše prostor.")
    @Tag(name="DELETE")
    @APIResponses({
            @APIResponse(description = "Izbrisan prostor", responseCode = "204", content = @Content(schema = @Schema(implementation = Prostor.class))),
            @APIResponse(description = "Prostor ni najden!", responseCode = "404")
    })
    public Response deleteProstor(@PathParam("id") Integer id) {
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(prostorZrno.deleteProstor(id))
                .build();
    }

    // Poslovna metoda 1: pridobi omejitev oseb v podanem prostoru
    @GET
    @Path("{id}/omejitev")
    @Operation(summary = "Pridobitev omejitve prostora", description = "Iz podanih vrednosti prostora, kvadrature in omejitve stevila oseb na kvadrat, izracuna omejitev za celi prostor.")
    @Tag(name="BusinessLogic")
    @APIResponses({
            @APIResponse(description = "Omejitev izracunana", responseCode = "200", content = @Content(schema = @Schema(implementation = Integer.class))),
            @APIResponse(description = "Prostor ni podan!", responseCode = "400")
    })
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
    @Operation(summary = "Dolocanje presezene omejitve prostora", description = "Primerja trenutno stevilo oseb v prostoru z omejitvijo prostora.")
    @Tag(name="BusinessLogic")
    @APIResponses({
            @APIResponse(description = "Stanje prostora", responseCode = "200", content = @Content(schema = @Schema(implementation = boolean.class))),
            @APIResponse(description = "Prostor ni podan!", responseCode = "400")
    })

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
