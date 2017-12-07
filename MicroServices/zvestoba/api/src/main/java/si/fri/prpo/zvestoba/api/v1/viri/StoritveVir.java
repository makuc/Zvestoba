package si.fri.prpo.zvestoba.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.headers.Header;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.media.Schema;
import io.swagger.oas.annotations.responses.ApiResponse;
import si.fri.prpo.zvestoba.api.v1.Request.RequestStoritev;
import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
import si.fri.prpo.zvestoba.zrna.StoritevZrno;
import si.fri.prpo.zvestoba.zrna.StoritevZrno;
import si.fri.prpo.zvestoba.zrna.UporabnikZrno;
import si.fri.prpo.zvestoba.zrna.ZbraneTockeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("storitve")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class StoritveVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @Operation(
            description = "Vrne seznam vseh storitev.",
            summary = "Seznam storitev",
            tags = "Storitve",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seznam storitev uspešno vrnjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Storitev.class
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    )
            }
    )
    @GET
    public Response vrniStoritve(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Storitev> storitve = storitveZrno.getStoritve(query);

        return Response.status(Response.Status.OK).entity(storitve).build();
    }

    @Operation(
            description = "Vrne določeno storitev.",
            summary = "Določena storitev",
            tags = "Storitve",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Storitev uspešno vrnjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Storitev.class
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Zahtevana storitev ne obstaja",
                            content = @Content(
                                    schema = @Schema(
                                            type = "int",
                                            name = "id"
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    )
            }
    )
    @GET
    @Path("{id}")
    public Response vrniStoritev(@PathParam("id") int id){
        Storitev storitev = storitveZrno.getStoritev(id);
        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();
        return Response.status(Response.Status.OK).entity(storitev).build();
    }

    @Operation(
            description = "Briše določeno storitev.",
            summary = "Brisanje storitve",
            tags = "Storitve",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Storitev uspešno odstranjena",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Storitev.class
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Zahtevana storitev ne obstaja",
                            content = @Content(
                                    schema = @Schema(
                                            type = "int",
                                            name = "id"
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Manjkajoči podatek",
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    )
            }
    )
    @DELETE
    public Response odstraniStoritev(RequestStoritev requestStoritev) {

        if(requestStoritev.getStoritevId() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(requestStoritev).build();

        Storitev storitev = storitveZrno.getStoritev(requestStoritev.getStoritevId());

        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(requestStoritev).build();

        storitveZrno.deleteStoritev(requestStoritev.getStoritevId());

        return Response.status(Response.Status.OK).build();
    }

    @Operation(
            description = "Dodaja nove storitve.",
            summary = "Dodajanje storitve",
            tags = "Storitve",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Storitev uspešno ustvarjena",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Storitev.class
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "206",
                            description = "Manjkajoči podatki",
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Nekaj je narobe s strežnikom"
                    )
            }
    )
    @POST
    public Response dodajStoriteva(RequestStoritev requestStoritev){
        if(requestStoritev.getNaziv() == null || requestStoritev.getOpis() == null || requestStoritev.getStPridobljenihTock() == null)
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(requestStoritev).build();

        Storitev storitev = new Storitev(requestStoritev.getNaziv(),
                requestStoritev.getOpis(),
                requestStoritev.getStPridobljenihTock()
        );
        storitev = storitveZrno.storeStoritev(storitev);

        if(storitev == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        return Response.status(Response.Status.CREATED).entity(storitev).build();
    }

    @Operation(
            description = "Posodobi podatke o storitvi.",
            summary = "Posodabljanje storitve",
            tags = "Storitve",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Storitev uspešno posodobljena",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Storitev.class
                                    )
                            ),
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "206",
                            description = "Manjkajoči podatki",
                            headers = {
                                    @Header(
                                            name = "content-length",
                                            schema = @Schema(
                                                    type = "int"
                                            )
                                    ),
                                    @Header(
                                            name = "content-type",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "date",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "server",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    ),
                                    @Header(
                                            name = "x-powered-by",
                                            schema = @Schema(
                                                    type = "string"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Nekaj je narobe s strežnikom"
                    )
            }
    )
    @PUT
    public Response posodobiStoriteva(RequestStoritev requestStoritev){

        if(requestStoritev.getStoritevId() == null)
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(requestStoritev).build();

        Storitev storitev = new Storitev(
                requestStoritev.getNaziv(),
                requestStoritev.getOpis(),
                requestStoritev.getStPridobljenihTock()
        );
        storitev = storitveZrno.updateStoritev(requestStoritev.getStoritevId(), storitev);

        if(storitev == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();

        return Response.status(Response.Status.OK).entity(storitev).build();
    }
}
