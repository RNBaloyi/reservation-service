package com.binary.microservices.reservation_service.repository;

import com.binary.microservices.reservation_service.model.Reservation;
import com.binary.microservices.reservation_service.model.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    Optional<Reservation> findByTableIdAndCustomerIdAndStatus(String tableId, String customerId, ReservationStatus status);

}