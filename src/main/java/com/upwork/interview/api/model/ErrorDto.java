package com.upwork.interview.api.model;


public class ErrorDto {
    private Integer httpStatus;
    private String errorMessage;
    private String developerMessage;

    public ErrorDto(Integer httpStatus, String errorMessage, String developerMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.developerMessage = developerMessage;
    }

    public ErrorDto() {
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}

