package com.upwork.interview.network;

import com.upwork.interview.network.impl.Node;
import com.upwork.interview.network.impl.search.NetworkSearch;
import org.junit.Before;
import org.junit.Test;

import static com.upwork.interview.TestUtils.assertFirstConnection;
import static com.upwork.interview.TestUtils.assertRemainingEmptyNodes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowableOfType;

public class NetworkSearchTest {

    private NetworkSearch networkSearch;

    @Before
    public void setUp() {
        networkSearch = new NetworkSearch(10);
    }

    @Test
    public void testConnectWithNoExistingConnectionsBefore() {
        networkSearch.connect(0, 1);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertRemainingEmptyNodes(networkSearch, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithNoExistingConnectionsBeforeOtherWayAround() {
        networkSearch.connect(1, 0);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertRemainingEmptyNodes(networkSearch, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBefore() {
        networkSearch.connect(0, 1);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkSearch.getNodesMap().get(2);
        networkSearch.connect(1, 2);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);

        assertRemainingEmptyNodes(networkSearch, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectWithExistingConnectionsBeforeAddingEmptyNode() {
        networkSearch.connect(0, 1);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkSearch.getNodesMap().get(2);
        networkSearch.connect(2, 1);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);

        assertRemainingEmptyNodes(networkSearch, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectCircularReference() {
        networkSearch.connect(0, 1);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkSearch.getNodesMap().get(2);
        networkSearch.connect(1, 2);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);

        networkSearch.connect(2, 0);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeZero);

        assertRemainingEmptyNodes(networkSearch, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testConnectCircularReferenceOtherWayAround() {
        networkSearch.connect(0, 1);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        Node nodeTwo = networkSearch.getNodesMap().get(2);
        networkSearch.connect(1, 2);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);

        networkSearch.connect(0, 2);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeZero);

        assertRemainingEmptyNodes(networkSearch, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void testQueryConnectedNodes() {
        networkSearch.connect(0, 1);

        Node nodeZero = networkSearch.getNodesMap().get(0);
        Node nodeOne = networkSearch.getNodesMap().get(1);

        assertFirstConnection(nodeZero, nodeOne);

        assertThat(networkSearch.query(0, 1)).isTrue();
        assertThat(networkSearch.query(1, 0)).isTrue();
    }


    @Test
    public void testQueryComplexConnections() {
        networkSearch.connect(0, 1);
        networkSearch.connect(4, 2);
        networkSearch.connect(2, 3);
        networkSearch.connect(2, 0);

        assertThat(networkSearch.query(0, 1)).isTrue();
        assertThat(networkSearch.query(2, 4)).isTrue();
        assertThat(networkSearch.query(2, 3)).isTrue();
        assertThat(networkSearch.query(2, 0)).isTrue();

        assertThat(networkSearch.query(1, 0)).isTrue();
        assertThat(networkSearch.query(4, 2)).isTrue();
        assertThat(networkSearch.query(3, 2)).isTrue();
        assertThat(networkSearch.query(0, 2)).isTrue();

        assertThat(networkSearch.query(0, 4)).isTrue();
        assertThat(networkSearch.query(4, 1)).isTrue();
        assertThat(networkSearch.query(3, 1)).isTrue();
        assertThat(networkSearch.query(3, 4)).isTrue();

        assertThat(networkSearch.query(4, 0)).isTrue();
        assertThat(networkSearch.query(1, 4)).isTrue();
        assertThat(networkSearch.query(1, 3)).isTrue();
        assertThat(networkSearch.query(4, 2)).isTrue();

    }

    @Test
    public void testQueryNonConnectedNodes() {
        assertThat(networkSearch.query(1, 0)).isFalse();
    }

    @Test
    public void testQueryNonConnectedNodesOtherWayAround() {
        assertThat(networkSearch.query(0, 1)).isFalse();
    }


    @Test
    public void testConnectNoExistingOriginNode() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.connect(100, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin node does not exists");
    }

    @Test
    public void testConnectNoExistingDestinationNode() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.connect(1, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("destination node does not exists");
    }

    @Test
    public void testConnectNonExistingNodes() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.connect(101, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin neither destination nodes exists");
    }

    @Test
    public void testConnectSameNode() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.connect(1, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("The same node cannot be queried/connected to itself");
    }

    @Test
    public void testQueryNoExistingOriginNode() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.query(100, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin node does not exists");
    }

    @Test
    public void testQueryNoExistingDestinationNode() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.query(1, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("destination node does not exists");
    }

    @Test
    public void testQueryNonExistingNodes() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.query(101, 100), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("origin neither destination nodes exists");
    }

    @Test
    public void testQuerySameNode() {
        Throwable throwable = catchThrowableOfType(() -> networkSearch.query(1, 1), IllegalArgumentException.class);
        assertThat(throwable).hasMessage("The same node cannot be queried/connected to itself");
    }

}
