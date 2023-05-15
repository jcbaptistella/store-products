package com.cayena.storeproducts.exception;

public class GenericException extends RuntimeException {

    public GenericException(String message) {
        super(String.format(message));
    }
}
