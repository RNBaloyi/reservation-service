package com.binary.microservices.reservation_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation {

    @Id
    private String reservationId;

    private String tableId;
    private String customerId;
    private LocalDateTime reservationTime;
    private LocalDateTime expirationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Transient  // This field won't be persisted
    private static int sequenceNumber = 1;

    public Reservation() {
        this.reservationId = generateReservationId();
        this.reservationTime = LocalDateTime.now();
        this.status = ReservationStatus.CONFIRMED;
    }

    private synchronized String generateReservationId() {
        String formattedNumber = String.format("R%03d", sequenceNumber++);
        return formattedNumber;
    }

    public enum ReservationStatus {
        CONFIRMED, CANCELLED, FULFILLED
    }


}