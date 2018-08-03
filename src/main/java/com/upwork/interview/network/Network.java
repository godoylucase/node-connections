package com.upwork.interview.network;

import java.util.HashMap;
import java.util.stream.IntStream;

public class Network {

    private final HashMap<Integer, Node> nodesMap;

    public Network(int nodesAmount) {
        this.nodesMap = new HashMap<>(nodesAmount);
        IntStream.range(0, nodesAmount).forEach(i -> this.nodesMap.put(i, new Node(i)));
    }

    public void connect(int origin, int destination) {
        Node originNode = nodesMap.get(origin);
        Node destinationNode = nodesMap.get(destination);
        checkPreconditions(originNode, destinationNode);

        originNode.connectNode(destinationNode);
    }

    public boolean query(int origin, int destination) {
        Node originNode = nodesMap.get(origin);
        Node destinationNode = nodesMap.get(destination);
        checkPreconditions(originNode, destinationNode);

        return originNode.getConnectedNodes().contains(destinationNode)
                && destinationNode.getConnectedNodes().contains(originNode);
    }

    private void checkPreconditions(Node origin, Node destination) {
        String message = null;
        if (origin == null && destination == null) {
            message = "origin neither destination nodes exists";
        } else if (destination == null) {
            message = "destination node does not exists";
        } else if (origin == null) {
            message = "origin node does not exists";
        } else if (origin.equals(destination)) {
            message = "The same node cannot be queried/connected to itself";
        }

        if (message != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public HashMap<Integer, Node> getNodesMap() {
        return nodesMap;
    }

}
