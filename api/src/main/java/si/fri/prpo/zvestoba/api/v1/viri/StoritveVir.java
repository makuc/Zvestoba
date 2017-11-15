package si.fri.prpo.zvestoba.api.v1.viri;

import si.fri.prpo.zvestoba.api.v1.Request.RequestStoritev;
import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.zrna.StoritevZrno;
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
@Path("storitve")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class StoritveVir {
    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @GET
    public Response vrniStoritve(){

        List<Storitev> storitve = storitveZrno.getStoritve();
        // pridobi Storiteve

        return Response.status(Response.Status.OK).entity(storitve).build();
    }

    @Path("{id}")
    @GET
    public Response vrniStoriteva(@PathParam("id") int id){
        Storitev storitev = storitveZrno.getStoritev(id);

        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();
        return Response.status(Response.Status.OK).entity(storitev).build();
    }

    @Path("{id}")
    @DELETE
    public Response odstraniStoriteva(@PathParam("id") int id) {

        Storitev storitev = storitveZrno.getStoritev(id);
        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();

        storitveZrno.deleteStoritev(id);
        return Response.status(Response.Status.OK).build();

    }

    @POST
    public Response dodajStoriteva(RequestStoritev requestStoritev){
        if(requestStoritev.getNaziv().length() == 0 ||
                requestStoritev.getOpis().length() == 0 || requestStoritev.getStPridobljenihTock() == null)
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(requestStoritev).build();

        Storitev storitev = new Storitev(requestStoritev.getNaziv(),
                requestStoritev.getOpis(),
                requestStoritev.getStPridobljenihTock());
        storitveZrno.storeStoritev(storitev);
        Storitev created = storitveZrno.getStoritev(storitev.getStoritevId());
        if(created == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @Path("{id}")
    @PUT
    public Response posodobiStoriteva(@PathParam("id") int id, RequestStoritev requestStoritev){
        if(requestStoritev.getStoritevId() == null || requestStoritev.getNaziv().length() == 0 ||
                requestStoritev.getOpis().length() == 0 || requestStoritev.getStPridobljenihTock() == null)
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(requestStoritev).build();
        Storitev storitev = new Storitev(requestStoritev.getNaziv(),
                requestStoritev.getOpis(),
                requestStoritev.getStPridobljenihTock());
        storitveZrno.updateStoritev(id, storitev);
        storitev = storitveZrno.getStoritev(id);
        if(storitev == null)
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        return Response.status(Response.Status.OK).entity(storitev).build();
    }
}
