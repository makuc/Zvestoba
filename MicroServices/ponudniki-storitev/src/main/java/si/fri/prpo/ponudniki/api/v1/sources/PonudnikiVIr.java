package si.fri.prpo.ponudniki.api.v1.sources;

import si.fri.prpo.ponudniki.beans.PonudnikiZrno;
import si.fri.prpo.ponudniki.entities.Ponudniki;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("ponudniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PonudnikiVIr {

    @Inject
    PonudnikiZrno ponudnikiZrno;

    @GET
    public Response getPonudnike() {
        List<Ponudniki> p = ponudnikiZrno.getPonudniki();
        if(p.size() <= 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(p).build();
    }

    @GET
    @Path("{id}")
    public Response getPonudnik(@PathParam("id") Integer ponudnikId) {
        Ponudniki p = ponudnikiZrno.getPonudnik(ponudnikId);
        if(p == null)
            return Response.status(Response.Status.NOT_FOUND).entity(ponudnikId).build();
        return Response.status(Response.Status.OK).entity(p).build();
    }
}
