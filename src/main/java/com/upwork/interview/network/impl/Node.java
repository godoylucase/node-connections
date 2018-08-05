package com.upwork.interview.network.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {

    protected int id;
    protected final Set<Node> connectedNodes;

    public Node() {
        this.connectedNodes = new HashSet<>();
    }

    public Node(int id) {
        this();
        this.id = id;
    }

    public void connectNode(Node toNode) {
        this.connectedNodes.add(toNode);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<Node> getConnectedNodes() {
        return connectedNodes;
    }

}
