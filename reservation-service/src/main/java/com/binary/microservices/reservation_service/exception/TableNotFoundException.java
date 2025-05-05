package com.binary.microservices.reservation_service.exception;


public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super(message);
    }
}