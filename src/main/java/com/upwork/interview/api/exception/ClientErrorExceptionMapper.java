package com.upwork.interview.api.exception;

import org.springframework.stereotype.Component;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {

    @Override
    public Response toResponse(ClientErrorException exception) {
        if (Response.Status.NOT_FOUND.getStatusCode() == exception.getResponse().getStatus()) {
            return ErrorResponseHelper.getResponse(Response.Status.NOT_FOUND, "The current URI could not be resolved", exception);
        }
        return exception.getResponse();
    }

}
