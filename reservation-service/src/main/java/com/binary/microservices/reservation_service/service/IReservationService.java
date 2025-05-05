package com.binary.microservices.reservation_service.service;

import com.binary.microservices.reservation_service.dto.ReservationRequest;
import com.binary.microservices.reservation_service.dto.ReservationResponse;

public interface IReservationService {
    ReservationResponse processReservation(ReservationRequest request);
}
