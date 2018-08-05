package com.upwork.interview.network;

import com.upwork.interview.network.impl.Node;

import java.util.HashMap;

/**
 * Keeps registry of the network's nodes and manages
 * nodes connections
 */
public interface Network {

    /**
     * Connects two valid nodes
     *
     * @param origin      node
     * @param destination node
     */
    void connect(int origin, int destination);

    /**
     * Verifies if two valid nodes are connected
     *
     * @param origin      node
     * @param destination node
     * @return
     */
    boolean query(int origin, int destination);

    HashMap<Integer, Node> getNodesMap();

    default void checkPreconditions(Node origin, Node destination) {
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

}
