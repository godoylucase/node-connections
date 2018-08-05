package com.upwork.interview;

import com.upwork.interview.network.Network;
import com.upwork.interview.network.impl.Node;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TestUtils {

    public static void assertFirstConnection(Node nodeZero, Node nodeOne) {
        assertThat(nodeZero).isNotNull();
        assertThat(nodeOne).isNotNull();

        assertThat(nodeZero.getConnectedNodes()).containsOnly(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsOnly(nodeZero);
    }

    public static void assertMultipleConnections(Node nodeZero, Node nodeOne, Node nodeTwo) {
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero, nodeTwo);
        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo);
    }

    public static void assertRemainingEmptyNodes(Network networkObserverPattern, Integer... ids) {
        Arrays.asList(ids).forEach(id -> {
            Node node = networkObserverPattern.getNodesMap().get(id);
            if (node != null) {
                assertThat(node.getConnectedNodes()).isEmpty();
            } else {
                fail(String.format("This id %d does not exist", id));
            }
        });
    }

}
