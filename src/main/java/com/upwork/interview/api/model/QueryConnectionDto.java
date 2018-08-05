package com.upwork.interview.api.model;

public class QueryConnectionDto extends ConnectionDto {

    private boolean connected;

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

}
