package com.upwork.interview.network;

import com.upwork.interview.network.impl.observerpattern.NetworkObserverPattern;
import com.upwork.interview.network.impl.Node;
import org.junit.Before;
import org.junit.Test;

import static com.upwork.interview.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowableOfType;

public class NetworkObserverPatternTest {

    private NetworkObserverPattern networkObserverPattern;

    @Before
    public void setUp() {
        networkObserverPattern = new NetworkObserverPattern(10);
    }

    @Test
    public void testConnectWithNoExistingConnectionsBefore() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertRemainingEmptyNodes(networkObserverPattern, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithNoExistingConnectionsBeforeOtherWayAround() {
        networkObserverPattern.connect(1, 0);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertRemainingEmptyNodes(networkObserverPattern, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBefore() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkObserverPattern.getNodesMap().get(2);
        networkObserverPattern.connect(1, 2);

        assertMultipleConnections(nodeZero, nodeOne, nodeTwo);

        assertRemainingEmptyNodes(networkObserverPattern, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBeforeAddingEmptyNode() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkObserverPattern.getNodesMap().get(2);
        networkObserverPattern.connect(2, 1);

        assertMultipleConnections(nodeZero, nodeOne, nodeTwo);

        assertRemainingEmptyNodes(networkObserverPattern, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBeforeAddingNonEmptyNode() {
        connectingNonEmptyNodes();
    }

    @Test
    public void testConnectCircularReference() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkObserverPattern.getNodesMap().get(2);
        networkObserverPattern.connect(1, 2);

        assertMultipleConnections(nodeZero, nodeOne, nodeTwo);

        networkObserverPattern.connect(2, 0);

        assertMultipleConnections(nodeZero, nodeOne, nodeTwo);
        assertRemainingEmptyNodes(networkObserverPattern, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectCircularReferenceOtherWayAround() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkObserverPattern.getNodesMap().get(2);
        networkObserverPattern.connect(1, 2);

        assertMultipleConnections(nodeZero, nodeOne, nodeTwo);

        networkObserverPattern.connect(0, 2);

        assertMultipleConnections(nodeZero, nodeOne, nodeTwo);
        assertRemainingEmptyNodes(networkObserverPattern, 3, 4, 5, 6, 7, 8, 9);
    }

    private void connectingNonEmptyNodes() {
        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);
        Node nodeTwo = networkObserverPattern.getNodesMap().get(2);
        Node nodeThree = networkObserverPattern.getNodesMap().get(3);
        Node nodeFour = networkObserverPattern.getNodesMap().get(4);

        networkObserverPattern.connect(0, 1);

        assertFirstConnection(nodeZero, nodeOne);

        networkObserverPattern.connect(4, 2);
        networkObserverPattern.connect(2, 3);

        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeThree, nodeFour);
        assertThat(nodeThree.getConnectedNodes()).containsExactlyInAnyOrder(nodeTwo, nodeFour);
        assertThat(nodeFour.getConnectedNodes()).containsExactlyInAnyOrder(nodeTwo, nodeThree);

        networkObserverPattern.connect(2, 0);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo, nodeThree, nodeFour);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo, nodeThree, nodeFour);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne, nodeThree, nodeFour);
        assertThat(nodeThree.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne, nodeTwo, nodeFour);
        assertThat(nodeFour.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne, nodeTwo, nodeThree);

        assertRemainingEmptyNodes(networkObserverPattern, 5, 6, 7, 8, 9);
    }

    @Test
    public void testQueryConnectedNodes() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertThat(networkObserverPattern.query(0, 1)).isTrue();
    }

    @Test
    public void testQueryConnectedNodesOtherWayAround() {
        networkObserverPattern.connect(0, 1);

        Node nodeZero = networkObserverPattern.getNodesMap().get(0);
        Node nodeOne = networkObserverPattern.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertThat(networkObserverPattern.query(1, 0)).isTrue();
    }

    @Test
    public void testQueryComplexConnections() {
        connectingNonEmptyNodes();
        assertThat(networkObserverPattern.query(0, 1)).isTrue();
        assertThat(networkObserverPattern.query(2, 4)).isTrue();
        assertThat(networkObserverPattern.query(2, 3)).isTrue();
        assertThat(networkObserverPattern.query(2, 0)).isTrue();

        assertThat(networkObserverPattern.query(0, 4)).isTrue();
        assertThat(networkObserverPattern.query(4, 1)).isTrue();
        assertThat(networkObserverPattern.query(3, 1)).isTrue();
        assertThat(networkObserverPattern.query(3, 4)).isTrue();
    }

    @Test
    public void testQueryNonConnectedNodes() {
        assertThat(networkObserverPattern.query(1, 0)).isFalse();
    }

    @Test
    public void testQueryNonConnectedNodesOtherWayAround() {
        assertThat(networkObserverPattern.query(0, 1)).isFalse();
    }


    @Test
    public void testConnectNoExistingOriginNode() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.connect(100, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin node does not exists");
    }

    @Test
    public void testConnectNoExistingDestinationNode() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.connect(1, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("destination node does not exists");
    }

    @Test
    public void testConnectNonExistingNodes() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.connect(101, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin neither destination nodes exists");
    }

    @Test
    public void testConnectSameNode() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.connect(1, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("The same node cannot be queried/connected to itself");
    }

    @Test
    public void testQueryNoExistingOriginNode() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.query(100, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin node does not exists");
    }

    @Test
    public void testQueryNoExistingDestinationNode() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.query(1, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("destination node does not exists");
    }

    @Test
    public void testQueryNonExistingNodes() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.query(101, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin neither destination nodes exists");
    }

    @Test
    public void testQuerySameNode() {
        Throwable throwable = catchThrowableOfType(() -> networkObserverPattern.query(1, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("The same node cannot be queried/connected to itself");
    }

}
