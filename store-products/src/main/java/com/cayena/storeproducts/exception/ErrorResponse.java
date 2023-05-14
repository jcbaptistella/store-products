package com.cayena.storeproducts.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse {
    private HttpStatus httpStatus;
    private List<String> details;

    public ErrorResponse(HttpStatus httpStatus, List<String> details) {
        this.httpStatus = httpStatus;
        this.details = details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
