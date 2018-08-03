package com.upwork.interview.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "interview.network")
public class InterviewConfiguration {

    @NotNull(message = "nodesAmount cannot be null")
    @Min(value = 1L, message = "nodesAmount should be a positive number")
    private Integer nodesAmount;

    public Integer getNodesAmount() {
        return nodesAmount;
    }

    public void setNodesAmount(Integer nodesAmount) {
        this.nodesAmount = nodesAmount;
    }

}
