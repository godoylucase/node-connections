package com.upwork.interview.service;

import com.upwork.interview.api.model.ConnectionDto;
import com.upwork.interview.api.model.QueryConnectionDto;
import com.upwork.interview.network.Network;

public interface NetworkService {

    void connectNodes(ConnectionDto connectNodes);

    QueryConnectionDto queryConnection(ConnectionDto connectNodes);

    Network getNetwork();

}
