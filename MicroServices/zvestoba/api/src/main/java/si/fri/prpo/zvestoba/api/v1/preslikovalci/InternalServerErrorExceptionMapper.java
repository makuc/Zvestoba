package si.fri.prpo.zvestoba.api.v1.preslikovalci;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

    @Override
    public Response toResponse(InternalServerErrorException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(e)
                .build();
    }

}
