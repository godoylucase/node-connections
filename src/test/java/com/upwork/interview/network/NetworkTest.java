package com.upwork.interview.network;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.ThrowableAssert.catchThrowableOfType;

public class NetworkTest {

    private Network network;

    @Before
    public void setUp() {
        network = new Network(10);
    }

    @Test
    public void testConnectWithNoExistingConnectionsBefore() {
        network.connect(0, 1);

        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertRemainingEmptyNodes(2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithNoExistingConnectionsBeforeOtherWayAround() {
        network.connect(1, 0);

        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertRemainingEmptyNodes(2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBefore() {
        network.connect(0, 1);

        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = network.getNodesMap().get(2);
        network.connect(1, 2);

        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo);

        assertRemainingEmptyNodes(3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBeforeAddingEmptyNode() {
        network.connect(0, 1);

        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = network.getNodesMap().get(2);
        network.connect(2, 1);

        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo);

        assertRemainingEmptyNodes(3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBeforeAddingNonEmptyNode() {
        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);
        Node nodeTwo = network.getNodesMap().get(2);
        Node nodeThree = network.getNodesMap().get(3);
        Node nodeFour = network.getNodesMap().get(4);

        network.connect(0, 1);

        assertFirstConnection(nodeZero, nodeOne);

        network.connect(4, 2);
        network.connect(2, 3);

        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeThree, nodeFour);
        assertThat(nodeThree.getConnectedNodes()).containsExactlyInAnyOrder(nodeTwo, nodeFour);
        assertThat(nodeFour.getConnectedNodes()).containsExactlyInAnyOrder(nodeTwo, nodeThree);

        network.connect(2, 0);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo, nodeThree, nodeFour);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo, nodeThree, nodeFour);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne, nodeThree, nodeFour);
        assertThat(nodeThree.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne, nodeTwo, nodeFour);
        assertThat(nodeFour.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne, nodeTwo, nodeThree);

        assertRemainingEmptyNodes(5, 6, 7, 8, 9);
    }

    @Test
    public void testQueryConnectedNodes() {
        network.connect(0, 1);

        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertThat(network.query(0,1)).isTrue();
    }

    @Test
    public void testQueryConnectedNodesOtherWayAround() {
        network.connect(0, 1);

        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertThat(network.query(1,0)).isTrue();
    }

    @Test
    public void testQueryNonConnectedNodes() {
        assertThat(network.query(1,0)).isFalse();
    }

    @Test
    public void testQueryNonConnectedNodesOtherWayAround() {
        assertThat(network.query(0,1)).isFalse();
    }

    private void assertFirstConnection(Node nodeZero, Node nodeOne) {
        assertThat(nodeZero).isNotNull();
        assertThat(nodeOne).isNotNull();

        assertThat(nodeZero.getConnectedNodes()).containsOnly(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsOnly(nodeZero);
    }

    private void assertRemainingEmptyNodes(Integer... ids) {
        Arrays.asList(ids).forEach(id -> {
            Node node = network.getNodesMap().get(id);
            if (node != null) {
                assertThat(node.getConnectedNodes()).isEmpty();
            } else {
                fail(String.format("This id %d does not exist", id));
            }
        });
    }

    @Test
    public void testConnectNoExistingOriginNode() {
        Throwable throwable = catchThrowableOfType(() -> network.connect(100, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin node does not exists");
    }

    @Test
    public void testConnectNoExistingDestinationNode() {
        Throwable throwable = catchThrowableOfType(() -> network.connect(1, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("destination node does not exists");
    }

    @Test
    public void testConnectNonExistingNodes() {
        Throwable throwable = catchThrowableOfType(() -> network.connect(101, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin neither destination nodes exists");
    }

    @Test
    public void testConnectSameNode() {
        Throwable throwable = catchThrowableOfType(() -> network.connect(1, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("The same node cannot be queried/connected to itself");
    }

    @Test
    public void testQueryNoExistingOriginNode() {
        Throwable throwable = catchThrowableOfType(() -> network.query(100, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin node does not exists");
    }

    @Test
    public void testQueryNoExistingDestinationNode() {
        Throwable throwable = catchThrowableOfType(() -> network.query(1, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("destination node does not exists");
    }

    @Test
    public void testQueryNonExistingNodes() {
        Throwable throwable = catchThrowableOfType(() -> network.query(101, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin neither destination nodes exists");
    }

    @Test
    public void testQuerySameNode() {
        Throwable throwable = catchThrowableOfType(() -> network.query(1, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("The same node cannot be queried/connected to itself");
    }

}
