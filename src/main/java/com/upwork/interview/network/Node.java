package com.upwork.interview.network;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Node implements PropertyChangeListener {

    private int id;
    private final Set<Node> connectedNodes;

    @JsonIgnore
    private PropertyChangeSupport propertyChangeSupport;

    public Node() {
        this.connectedNodes = new HashSet<>();
    }

    public Node(int id) {
        this();
        this.id = id;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void connectNode(Node toNode) {
        connectNode(toNode, false);
    }

    public void connectNode(Node toNode, boolean isCallback) {
        addNode(toNode);
        NodeChangeSet nodeChangeSet = new NodeChangeSet(this, toNode, connectedNodes, isCallback);
        PropertyChangeEvent event = new PropertyChangeEvent(this, "nodeChangeSet", null, nodeChangeSet);
        propertyChangeSupport.firePropertyChange(event);
    }

    private void addNode(Node node) {
        this.connectedNodes.add(node);
        addPropertyChangeListener(node);
    }

    private void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        NodeChangeSet changeSet = (NodeChangeSet) event.getNewValue();

        Set<Node> nodes = changeSet.getSourcesConnections()
                .parallelStream()
                .filter(node -> !node.equals(this))
                .collect(Collectors.toSet());

        if (changeSet.getDestination().equals(this)) {
            if (changeSet.isCallback()) {
                nodes.forEach(this::addNode);
            } else {
                if (!this.connectedNodes.containsAll(nodes)) {
                    nodes.forEach(this::addNode);
                }
                connectNode(changeSet.getSource(), true);
            }
        } else {
            if (changeSet.isCallback()) {
                nodes.forEach(this::addNode);
            } else {
                addNode(changeSet.getDestination());
            }
        }
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
