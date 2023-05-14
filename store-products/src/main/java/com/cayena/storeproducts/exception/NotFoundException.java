package com.cayena.storeproducts.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(String.format(message));
    }
}
