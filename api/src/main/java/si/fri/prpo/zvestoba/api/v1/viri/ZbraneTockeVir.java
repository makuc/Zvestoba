package si.fri.prpo.zvestoba.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.headers.Header;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.media.Schema;
import io.swagger.oas.annotations.responses.ApiResponse;
import io.swagger.oas.annotations.security.SecurityRequirement;
import si.fri.prpo.zvestoba.api.v1.Request.RequestZbraneTocke;
import si.fri.prpo.zvestoba.entitete.Storitev;
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
import java.util.List;

@ApplicationScoped
@Path("tocke")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZbraneTockeVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @Operation(
            description = "Vrne seznam vseh zbranih točk za vse uporabnike in vse njihove storitve.",
            summary = "Seznam zbranih točk",
            tags = "ZbraneTocke",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seznam zbranih tock uspešno vrnjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ZbraneTocke.class
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
    public Response vrniZbraneTocke(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<ZbraneTocke> zbraneTocke = tockeZrno.getZbraneTocke(query);

        //ist<ZbraneTocke> zbraneTocke = tockeZrno.getZbraneTocke();

        return Response.status(Response.Status.OK).entity(zbraneTocke).build();
    }

    @Operation(
            description = "Vrne seznam vseh storitev določenega uporabnika in število točk, ki jih je za posamezno storitev zbral.",
            summary = "Seznam zbranih točk storitev določenega uporabnika",
            tags = "ZbraneTocke",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seznam zbranih tock uspešno vrnjen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ZbraneTocke.class
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
    @Path("{username}")
    public Response vrniStoritveUporabnika(@PathParam("username") String uporabnisko_ime){

        Uporabnik user = uporabnikiZrno.getUporabnik(uporabnisko_ime);

        if(user == null)
            return Response.status(Response.Status.NOT_FOUND).entity(uporabnisko_ime).build();

        List<ZbraneTocke> zbraneTocke = tockeZrno.getStoritveUporabnika(user);

        return Response.status(Response.Status.OK).entity(zbraneTocke).build();

    }

    @Operation(
            description = "Vrne seznam vseh uporabnikov določene storitve in število točk, ki so jih pri tej storitvi zbrali.",
            summary = "Seznam zbranih točk uporabnikov določene storitve",
            tags = "ZbraneTocke",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seznam zbranih točk uspešno najden",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ZbraneTocke.class
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
                                            name = "storitevId"
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
    @Path("storitve/{storitev}")
    public Response vrniUporabnikeStoritve(@PathParam("storitev") Integer storitevId){

        Storitev storitev = storitveZrno.getStoritev(storitevId);
        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(storitev).build();

        List<ZbraneTocke> tockeUporabnikovStoritve = tockeZrno.getUporabnikeStoritve(storitev);

        return Response.status(Response.Status.OK).entity(tockeUporabnikovStoritve).build();
    }

    @Operation(
            description = "Vrne določenega uporabnika določene storitve in število točk, ki jih je ta uporabnik pri tej storitvi zbral.",
            summary = "Število točk uporabnika storitve",
            tags = "ZbraneTocke",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Zbrane točke uporabnika storitve uspešno vrnjene",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ZbraneTocke.class
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
                            description = "Vsaj eden izmed kriterijev je napačen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = RequestZbraneTocke.class
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
    @Path("{username}/{storitev}")
    public Response vrniTockeUporabnikaStoritve(@PathParam("username") String uporabnisko_ime, @PathParam("storitev") Integer storitevId){

        Uporabnik user = uporabnikiZrno.getUporabnik(uporabnisko_ime);
        if(user == null)
            return Response.status(Response.Status.NOT_FOUND).entity(uporabnisko_ime).build();

        Storitev storitev = storitveZrno.getStoritev(storitevId);
        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(storitevId).build();

        ZbraneTocke tocke = tockeZrno.getTockeStoritveUporabnika(user, storitev);

        return Response.status(Response.Status.OK).entity(tocke).build();
    }

    @Operation(
            description = "Določenemu uporabniku doda uporabo določene storitve s zbranimi točkami = 0.",
            summary = "Doda uporabniku novo storitev",
            tags = "ZbraneTocke",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Storitev je bila uspešno dodana uporabniku",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ZbraneTocke.class
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
                            responseCode = "503",
                            description = "Nekaj je narobe s strežnikom"
                    )
            }
    )
    @POST
    public Response dodajZbraneTocke(RequestZbraneTocke requestZbraneTocke){

        ZbraneTocke zbraneTocke = tockeZrno.dodajUporabnikuStoritev(requestZbraneTocke.getUporabnisko_ime(), requestZbraneTocke.getStoritevId());
        if(zbraneTocke == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(requestZbraneTocke).build();

        return Response.status(Response.Status.CREATED).entity(zbraneTocke).build();
    }

    @Operation(
            description = "Določenemu uporabniku določene storitve poviša število zbranih točk za kolikor ta storitev povišuje točke.",
            summary = "Uporabniku storitve poviša število zbranih točk",
            tags = "ZbraneTocke",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Zbrane točke uporabnika te storitve so bile uspešno povišane",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ZbraneTocke.class
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
                            description = "Vsaj eden izmed kriterijev je napačen",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = RequestZbraneTocke.class
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
                            responseCode = "503",
                            description = "Nekaj je narobe s strežnikom"
                    )
            }
    )
    @PUT
    public Response povisajUporabnikuTockeStoritve(RequestZbraneTocke requestZbraneTocke){
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(requestZbraneTocke.getUporabnisko_ime());
        if(uporabnik == null)
            return Response.status(Response.Status.NOT_FOUND).entity(requestZbraneTocke.getUporabnisko_ime()).build();

        Storitev storitev = storitveZrno.getStoritev(requestZbraneTocke.getStoritevId());
        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(requestZbraneTocke.getStoritevId()).build();

        tockeZrno.povisajUporabnikuTockeStoritve(uporabnik, storitev);
        ZbraneTocke zbraneTocke = tockeZrno.getTockeStoritveUporabnika(uporabnik, storitev);
        if(zbraneTocke == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();

        return Response.status(Response.Status.OK).entity(zbraneTocke).build();
    }

}
