package si.fri.prpo.skupina19.sledilnik.api.v1.mappers;

import si.fri.prpo.skupina19.sledilnik.izjeme.NeveljavnaEntitetaIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavnaEntitetaIzjemaExceptionMapper implements ExceptionMapper<NeveljavnaEntitetaIzjema> {
    @Override
    public Response toResponse(NeveljavnaEntitetaIzjema exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity("{\"napaka\":\"" + exception.getMessage() + "\"}").build();
    }
}
