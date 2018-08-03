package com.upwork.interview.api.model;

import java.util.ArrayList;
import java.util.List;

public class QueryConnectionDto extends ConnectionDto {

    private boolean connected;
    private List<Integer> otherOriginConnections = new ArrayList<>();

    public QueryConnectionDto() {
        super();
    }

    public QueryConnectionDto(Integer origin, Integer destination, boolean connected) {
        super(origin, destination);
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public List<Integer> getOtherOriginConnections() {
        return otherOriginConnections;
    }

    public void setOtherOriginConnections(List<Integer> otherOriginConnections) {
        this.otherOriginConnections = otherOriginConnections;
    }

}
