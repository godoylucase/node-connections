package com.upwork.interview.network.impl.observerpattern;

import com.upwork.interview.network.AbstractNetwork;
import com.upwork.interview.network.impl.Node;

import java.util.stream.IntStream;

public class NetworkObserverPattern extends AbstractNetwork {

    public NetworkObserverPattern(int networkSize) {
        super(networkSize);
        IntStream.range(0, networkSize).forEach(i -> this.nodesMap.put(i, new NodeEventListener(i)));
    }

    @Override
    public void connect(int origin, int destination) {
        Node originNode = nodesMap.get(origin);
        Node destinationNode = nodesMap.get(destination);
        checkPreconditions(originNode, destinationNode);

        originNode.connectNode(destinationNode);
    }

    @Override
    public boolean query(int origin, int destination) {
        Node originNode = nodesMap.get(origin);
        Node destinationNode = nodesMap.get(destination);
        checkPreconditions(originNode, destinationNode);

        return originNode.getConnectedNodes().contains(destinationNode)
                && destinationNode.getConnectedNodes().contains(originNode);
    }

}
