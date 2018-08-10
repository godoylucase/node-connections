package com.upwork.interview.network.impl.observerpattern;

import com.upwork.interview.network.impl.Node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;
import java.util.stream.Collectors;

public class NodeEventListener extends Node implements PropertyChangeListener {

    private PropertyChangeSupport propertyChangeSupport;

    public NodeEventListener(int id) {
        super(id);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
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
        addPropertyChangeListener((NodeEventListener) node);
    }

    private void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        NodeChangeSet changeSet = (NodeChangeSet) event.getNewValue();

        Set<Node> sourceConnectedNodes = changeSet.getSourcesConnections()
                .parallelStream()
                .filter(node -> !node.equals(this))
                .collect(Collectors.toSet());

        if (changeSet.isCallback()) {
            // adding all the nodes that comes from the node that executes the callback
            sourceConnectedNodes.forEach(this::addNode);
            return;
        }

        if (changeSet.getDestination().equals(this)) {
            // this is new node receiving the source's list
            if (!this.connectedNodes.containsAll(sourceConnectedNodes)) {
                // adding all the source's connected nodes
                sourceConnectedNodes.forEach(this::addNode);
            }
            // connect this back (callback -> true) with source to add it
            // as a connected node and as a listener
            connectNode(changeSet.getSource(), true);

        } else {
            // these are other nodes (not source neither destination)
            // adding just the new node
            addNode(changeSet.getDestination());
        }
    }

}
