package com.upwork.interview.network.impl.search;

import com.upwork.interview.network.AbstractNetwork;
import com.upwork.interview.network.impl.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class NetworkSearch extends AbstractNetwork {

    public NetworkSearch(int networkSize) {
        super(networkSize);
        IntStream.range(0, networkSize).forEach(i -> this.nodesMap.put(i, new Node(i)));
    }

    @Override
    public void connect(int origin, int destination) {
        Node originNode = nodesMap.get(origin);
        Node destinationNode = nodesMap.get(destination);
        checkPreconditions(originNode, destinationNode);

        originNode.connectNode(destinationNode);
        destinationNode.connectNode(originNode);
    }

    @Override
    public boolean query(int origin, int destination) {
        Node originNode = nodesMap.get(origin);
        Node destinationNode = nodesMap.get(destination);
        checkPreconditions(originNode, destinationNode);

        return query(originNode, destinationNode, new HashSet<>());
    }

    private boolean query(Node originNode, Node destinationNode, Set<Node> checkedNodes) {
        if (checkedNodes.contains(destinationNode)) {
            return false;
        }
        if (originNode.getConnectedNodes().contains(destinationNode)) {
            return true;
        } else {
            checkedNodes.add(destinationNode);
            return destinationNode.getConnectedNodes()
                    .parallelStream()
                    .anyMatch(node -> query(originNode, node, checkedNodes));
        }
    }

}
