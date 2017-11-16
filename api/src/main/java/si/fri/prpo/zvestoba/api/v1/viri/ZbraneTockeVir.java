package si.fri.prpo.zvestoba.api.v1.viri;


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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("tocke")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZbraneTockeVir {

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @GET
    public Response vrniZbraneTocke(){

        List<ZbraneTocke> zbraneTocke = tockeZrno.getZbraneTocke();
        // pridobi uporabnike

        return Response.status(Response.Status.OK).entity(zbraneTocke).build();
    }

    @GET
    @Path("{username}")
    public Response vrniStoritveUporabnika(@PathParam("username") String uporabnisko_ime){

        Uporabnik user = uporabnikiZrno.getUporabnik(uporabnisko_ime);
        if(user == null)
            return Response.status(Response.Status.NOT_FOUND).entity(uporabnisko_ime).build();

        List<ZbraneTocke> zbraneTocke = tockeZrno.getStoritveUporabnika(user);

        return Response.status(Response.Status.OK).entity(zbraneTocke).build();

    }

    @GET
    @Path("storitve/{storitev}")
    public Response vrniUporabnikeStoritve(@PathParam("storitev") Integer storitevId){

        Storitev storitev = storitveZrno.getStoritev(storitevId);
        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(storitev).build();

        List<ZbraneTocke> tockeUporabnikovStoritve = tockeZrno.getUporabnikeStoritve(storitev);

        return Response.status(Response.Status.OK).entity(tockeUporabnikovStoritve).build();
    }

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

    @POST
    public Response dodajZbraneTocke(RequestZbraneTocke requestZbraneTocke){

        ZbraneTocke zbraneTocke = tockeZrno.dodajUporabnikuStoritev(requestZbraneTocke.getUporabnisko_ime(), requestZbraneTocke.getStoritevId());
        if(zbraneTocke == null)
            return Response.status(Response.Status.CONFLICT).entity(requestZbraneTocke).build();

        return Response.status(Response.Status.CREATED).entity(zbraneTocke).build();
    }

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
