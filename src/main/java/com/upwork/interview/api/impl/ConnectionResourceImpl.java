package com.upwork.interview.api.impl;

import com.upwork.interview.api.ConnectionResource;
import com.upwork.interview.api.model.ConnectionDto;
import com.upwork.interview.service.NetworkService;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

@Component
public class ConnectionResourceImpl implements ConnectionResource {

    private final NetworkService networkService;

    public ConnectionResourceImpl(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public Response connectNodes(@Valid ConnectionDto connectNodes) {
        networkService.connectNodes(connectNodes);
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    public Response queryConnection(Integer origin, Integer destination) {
        return Response.status(Response.Status.OK).entity(networkService.queryConnection(new ConnectionDto(origin, destination))).build();
    }

}
