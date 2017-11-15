package si.fri.prpo.zvestoba.api.v1.viri;

import si.fri.prpo.zvestoba.api.v1.Request.RequestUporabnik;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
import si.fri.prpo.zvestoba.zrna.StoritevZrno;
import si.fri.prpo.zvestoba.zrna.UporabnikZrno;
import si.fri.prpo.zvestoba.zrna.ZbraneTockeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UporabnikiVir {

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @GET
    public Response vrniUporabnike(){

        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();
        // pridobi uporabnike

        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @Path("{username}")
    @GET
    public Response vrniUporabnika(@PathParam("username") String username){
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(username);

        if(uporabnik == null)
            return Response.status(Response.Status.NOT_FOUND).entity(username).build();
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }
    @Path("{username}")
    @DELETE
    public Response odstraniUporabnika(@PathParam("username") String username) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(username);
        if(uporabnik == null)
            return Response.status(Response.Status.NOT_FOUND).entity(username).build();

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
}
