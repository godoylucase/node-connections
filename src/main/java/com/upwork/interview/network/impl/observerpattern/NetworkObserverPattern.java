package com.upwork.interview.network.impl.observerpattern;

import com.upwork.interview.network.Network;
import com.upwork.interview.network.impl.Node;

import java.util.HashMap;
import java.util.stream.IntStream;

public class NetworkObserverPattern implements Network {

    private final HashMap<Integer, Node> nodesMap;

    public NetworkObserverPattern(int nodesAmount) {
        this.nodesMap = new HashMap<>(nodesAmount);
        IntStream.range(0, nodesAmount).forEach(i -> this.nodesMap.put(i, new NodeEventListener(i)));
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


    public HashMap<Integer, Node> getNodesMap() {
        return nodesMap;
    }

}
