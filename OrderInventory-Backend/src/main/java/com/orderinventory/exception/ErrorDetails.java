package com.orderinventory.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private HttpStatus status;
    private String message;
    private String error;
    private Date timestamp;

    public ErrorDetails(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = new Date();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
