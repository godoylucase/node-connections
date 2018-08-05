package com.upwork.interview.api.impl;

import com.upwork.interview.InterviewIntegrationTests;
import com.upwork.interview.TestUtils;
import com.upwork.interview.api.model.ConnectionDto;
import com.upwork.interview.api.model.QueryConnectionDto;
import com.upwork.interview.network.Network;
import com.upwork.interview.network.impl.Node;
import com.upwork.interview.service.NetworkService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.core.Response;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "interview.network.networkSolutionType=SEARCH")
public class ConnectionResourceImplSearchIT extends InterviewIntegrationTests {

    private final static String API_PATH = "/api/network";
    private final static String CONNECT_PATH = API_PATH + "/connect";
    private final static String QUERY_PATH = API_PATH + "/query";

    @Autowired
    private NetworkService networkService;
    private Network network;

    @Before
    public void setUp() {
        network = networkService.getNetwork();
    }

    @Test
    public void testConnectNodesSearchType() {
        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);

        connectNodes(nodeOne, nodeZero);

        QueryConnectionDto response = queryConnection(nodeZero, nodeOne);
        assertMultipleConnections(response);
    }

    @Test
    public void testConnectNonExistingNodesSearch() {
        given()
                .body(new ConnectionDto(100, 101))
                .post(CONNECT_PATH)
                .then()
                .statusCode(equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testQueryNonExistingNodesSearch() {
        given()
                .queryParam("origin", 100)
                .queryParam("destination", 101)
                .get(QUERY_PATH)
                .then()
                .statusCode(equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testConnectMultipleNodesAndQuerySearch() {
        Node nodeZero = network.getNodesMap().get(0);
        Node nodeOne = network.getNodesMap().get(1);
        Node nodeTwo = network.getNodesMap().get(2);

        connectNodes(nodeOne, nodeZero);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero);
        assertThat(nodeTwo.getConnectedNodes()).isEmpty();

        connectNodes(nodeZero, nodeTwo);

        assertThat(nodeZero.getConnectedNodes()).containsExactlyInAnyOrder(nodeOne, nodeTwo);
        assertThat(nodeOne.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero);
        assertThat(nodeTwo.getConnectedNodes()).containsExactlyInAnyOrder(nodeZero);

        QueryConnectionDto response = queryConnection(nodeZero, nodeOne);
        assertMultipleConnections(response);

        response = queryConnection(nodeZero, nodeTwo);
        assertMultipleConnections(response);

        response = queryConnection(nodeOne, nodeTwo);
        assertMultipleConnections(response);

        TestUtils.assertRemainingEmptyNodes(network, 3, 4, 5, 6, 7, 8, 9);
    }

    private void connectNodes(Node origin, Node source) {
        given()
                .body(new ConnectionDto(source.getId(), origin.getId()))
                .post(CONNECT_PATH)
                .then()
                .statusCode(equalTo(Response.Status.CREATED.getStatusCode()));
    }

    private QueryConnectionDto queryConnection(Node origin, Node destination) {
        return given()
                .queryParam("origin", origin.getId())
                .queryParam("destination", destination.getId())
                .get(QUERY_PATH)
                .then()
                .statusCode(equalTo(Response.Status.OK.getStatusCode()))
                .extract().body().as(QueryConnectionDto.class);
    }

    private void assertMultipleConnections(QueryConnectionDto response) {
        assertThat(response).isNotNull();
        assertThat(response.isConnected()).isTrue();
    }

}
