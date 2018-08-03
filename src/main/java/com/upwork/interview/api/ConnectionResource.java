package com.upwork.interview.api;

import com.upwork.interview.api.model.ConnectionDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/network")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ConnectionResource {

    @POST
    @Path("/connect")
    Response connectNodes(@Valid ConnectionDto connectNodes);

    @GET
    @Path("/query")
    Response queryConnection(@NotNull(message = "origin parameter must not be null value")
                             @Min(value = 0, message = "origin parameter must be zero or a positive number")
                             @QueryParam("origin") Integer origin,
                             @NotNull(message = "destination parameter must not be null value")
                             @Min(value = 0, message = "destination parameter must be zero or a positive number")
                             @QueryParam("destination") Integer destination);

}
