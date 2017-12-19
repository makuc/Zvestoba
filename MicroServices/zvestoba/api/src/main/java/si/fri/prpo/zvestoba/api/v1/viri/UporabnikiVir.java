package si.fri.prpo.zvestoba.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.headers.Header;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.media.Schema;
import io.swagger.oas.annotations.responses.ApiResponse;
import si.fri.prpo.zvestoba.api.v1.Request.RequestUporabnik;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
import si.fri.prpo.zvestoba.entitete.ZbraneTocke;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, OPTIONS, DELETE")
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @Operation(
            description = "Vrne seznam vseh uporabnikov.",
            summary = "Seznam uporabnikov",
            tags = "Uporabniki",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seznam uporabnikov uspešno vrnjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Uporabnik.class
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
    public Response vrniUporabnike(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki(query);
        // pridobi uporabnike

        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @Operation(
            description = "Vrne določenega uporabnika.",
            summary = "Določen uporabnik",
            tags = "Uporabniki",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Uporabnik uspešno vrnjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Uporabnik.class
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
                            description = "Zahtevan uporabnik ne obstaja",
                            content = @Content(
                                    schema = @Schema(
                                            type = "string",
                                            name = "uporabnisko_ime"
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
    @Path("{uporabnisko_ime}")
    public Response vrniUporabnika(@PathParam("uporabnisko_ime") String uporabnisko_ime){
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnisko_ime);
        if(uporabnik == null)
            return Response.status(Response.Status.NOT_FOUND).entity(uporabnisko_ime).build();
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @Operation(
            description = "Briše določenega uporabnika.",
            summary = "Brisanje uporabnika",
            tags = "Uporabniki",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Uporabnik uspešno odstranjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Uporabnik.class
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
                            description = "Zahtevan uporabnik ne obstaja",
                            content = @Content(
                                    schema = @Schema(
                                            type = "string",
                                            name = "uporabnisko_ime"
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
    public Response odstraniUporabnika(RequestUporabnik requestUporabnik) {

        if(requestUporabnik.getUporabnisko_ime() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(requestUporabnik.getUporabnisko_ime());
        if(uporabnik == null)
            return Response.status(Response.Status.NOT_FOUND).entity(requestUporabnik.getUporabnisko_ime()).build();

        uporabnikiZrno.deleteUporabnik(uporabnik);
        return Response.status(Response.Status.OK).build();

    }
    @DELETE
    @Path("{uporabnisko_ime}")
    public Response odstraniUporabnika(@PathParam("uporabnisko_ime") String uporabnisko_ime) {

        if(uporabnisko_ime == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        uporabnikiZrno.deleteUporabnik(uporabnisko_ime);
        return Response.status(Response.Status.OK).build();

    }


    @Operation(
            description = "Dodaja novega uporabnika.",
            summary = "Dodajanje uporabnika",
            tags = "Uporabniki",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Uporabnik uspešno ustvarjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Uporabnik.class
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
    public Response dodajUporabnika(RequestUporabnik requestUporabnik){
        if(requestUporabnik.getUporabnisko_ime().length() == 0 || requestUporabnik.getIme().length() == 0 ||
                requestUporabnik.getPriimek().length() == 0 || requestUporabnik.getEmail().length() == 0)
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(requestUporabnik).build();

        uporabnikiZrno.createUporabnik(
                requestUporabnik.getUporabnisko_ime(),
                requestUporabnik.getIme(),
                requestUporabnik.getPriimek(),
                requestUporabnik.getEmail()
        );
        Uporabnik created = uporabnikiZrno.getUporabnik(requestUporabnik.getUporabnisko_ime());
        if(created == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @Operation(
            description = "Posodobi podatke o uporabniku.",
            summary = "Posodabljanje uporabnika",
            tags = "Uporabniki",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Uporabnik uspešno posodobljen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Uporabnik.class
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
    public Response posodobiUporabnika(RequestUporabnik requestUporabnik){

        if(requestUporabnik.getUporabnisko_ime() == null)
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(requestUporabnik).build();

        Uporabnik uporabnik = new Uporabnik(
                requestUporabnik.getUporabnisko_ime(),
                requestUporabnik.getIme(),
                requestUporabnik.getPriimek(),
                requestUporabnik.getEmail()
        );
        uporabnikiZrno.updateUporabnik(uporabnik);
        uporabnik = uporabnikiZrno.getUporabnik(requestUporabnik.getUporabnisko_ime());
        if(uporabnik == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }
}
