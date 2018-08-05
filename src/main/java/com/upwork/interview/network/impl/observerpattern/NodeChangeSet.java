package com.upwork.interview.network.impl.observerpattern;

import com.upwork.interview.network.impl.Node;

import java.util.HashSet;
import java.util.Set;

public class NodeChangeSet {

    private final Set<Node> sourcesConnections;
    private Node source;
    private Node destination;
    private boolean callback;

    public NodeChangeSet(Node source, Node destination, Set<Node> sourcesConnections, boolean callback) {
        this.source = source;
        this.destination = destination;
        this.sourcesConnections = new HashSet<>(sourcesConnections);
        this.callback = callback;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public Set<Node> getSourcesConnections() {
        return sourcesConnections;
    }

    public boolean isCallback() {
        return callback;
    }

    public void setCallback(boolean callback) {
        this.callback = callback;
    }
}
