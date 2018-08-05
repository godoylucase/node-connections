package com.upwork.interview.network;

import com.upwork.interview.network.impl.Node;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNetwork implements Network {

    protected final Map<Integer, Node> nodesMap;

    protected AbstractNetwork(int networkSize) {
        this.nodesMap = new HashMap<>(networkSize);
    }

    @Override
    public Map<Integer, Node> getNodesMap() {
        return nodesMap;
    }

}
