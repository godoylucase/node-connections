package com.upwork.interview.service.impl;

import com.upwork.interview.api.exception.ErrorResponseHelper;
import com.upwork.interview.api.model.ConnectionDto;
import com.upwork.interview.api.model.QueryConnectionDto;
import com.upwork.interview.configuration.InterviewConfiguration;
import com.upwork.interview.network.Network;
import com.upwork.interview.network.NetworkFactory;
import com.upwork.interview.network.impl.Node;
import com.upwork.interview.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

@Service
public class NetworkServiceImpl implements NetworkService {

    private final Network network;

    @Autowired
    public NetworkServiceImpl(InterviewConfiguration configuration) {
        this.network = NetworkFactory.getNetwork(configuration.getNodesAmount(), configuration.getNetworkSolutionType());
    }

    @Override
    public void connectNodes(ConnectionDto connectNodes) {
        try {
            network.connect(connectNodes.getOrigin(), connectNodes.getDestination());
        } catch (IllegalArgumentException e) {
            throw new ClientErrorException(ErrorResponseHelper.getResponse(Response.Status.BAD_REQUEST,
                    "Parameters are wrong, please, double check them", e));
        }
    }

    @Override
    public QueryConnectionDto queryConnection(ConnectionDto connectNodes) {
        try {
            boolean connected = network.query(connectNodes.getOrigin(), connectNodes.getDestination());
            QueryConnectionDto dto = new QueryConnectionDto(connectNodes.getOrigin(), connectNodes.getDestination(), connected);
            Node originNode = network.getNodesMap().get(connectNodes.getOrigin());

            return dto;
        } catch (IllegalArgumentException e) {
            throw new ClientErrorException(ErrorResponseHelper.getResponse(Response.Status.BAD_REQUEST,
                    "Parameters are wrong, please, double check them", e));
        }
    }

    @Override
    public Network getNetwork() {
        return this.network;
    }

}
