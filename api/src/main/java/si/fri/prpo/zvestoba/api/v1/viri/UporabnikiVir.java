package si.fri.prpo.zvestoba.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.zvestoba.api.v1.Request.RequestUporabnik;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
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
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @GET
    public Response vrniUporabnike(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki(query);
        // pridobi uporabnike

        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @Path("{uporabnisko_ime}")
    @GET
    public Response vrniUporabnika(@PathParam("uporabnisko_ime") String uporabnisko_ime){
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnisko_ime);

        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

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
