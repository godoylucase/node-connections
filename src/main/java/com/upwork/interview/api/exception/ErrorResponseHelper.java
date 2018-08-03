package com.upwork.interview.api.exception;

import com.upwork.interview.api.model.ErrorDto;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ErrorResponseHelper {

    public static Response getResponse(Response.Status errorStatus, String messages, Throwable exception) {
        return getResponse(errorStatus, messages, exception, null);
    }

    public static Response getResponse(Response.Status errorStatus, String messages, Throwable exception, HttpHeaders headers) {
        ErrorDto errorResponse = new ErrorDto();
        errorResponse.setHttpStatus(errorStatus.getStatusCode());
        errorResponse.setErrorMessage(messages);
        errorResponse.setDeveloperMessage(exception.getMessage() != null ? exception.getMessage() : messages);

        Response.ResponseBuilder responseBuilder = Response.status(errorStatus).entity(errorResponse);

        if (headers != null) {
            responseBuilder.type(headers.getMediaType());
        } else {
            responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);
        }

        return responseBuilder.build();
    }

}
