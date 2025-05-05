package com.binary.microservices.reservation_service.exception;

public class InvalidReservationException extends RuntimeException {
    public InvalidReservationException(String message) {
        super(message);
    }
}