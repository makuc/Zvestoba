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

    @DELETE
    public Response odstraniStoriteva(RequestStoritev requestStoritev) {

        if(requestStoritev.getStoritevId() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(requestStoritev).build();

        Storitev storitev = storitveZrno.getStoritev(requestStoritev.getStoritevId());

        if(storitev == null)
            return Response.status(Response.Status.NOT_FOUND).entity(requestStoritev).build();

        storitveZrno.deleteStoritev(requestStoritev.getStoritevId());

        return Response.status(Response.Status.OK).build();
    }

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
