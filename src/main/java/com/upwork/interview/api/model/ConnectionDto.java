package com.upwork.interview.api.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ConnectionDto {

    @NotNull(message = "origin parameter must not be null value")
    @Min(value = 0, message = "origin parameter must be zero or a positive number")
    private Integer origin;
    @NotNull(message = "destination parameter must not be null value")
    @Min(value = 0, message = "destination parameter must be zero or a positive number")
    private Integer destination;

    public ConnectionDto() {
    }

    public ConnectionDto(Integer origin, Integer destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

}
