package com.binary.microservices.reservation_service.controller;

import com.binary.microservices.reservation_service.dto.ReservationRequest;
import com.binary.microservices.reservation_service.dto.ReservationResponse;
import com.binary.microservices.reservation_service.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping
    public ResponseEntity<?> handleReservation(@RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.processReservation(request);
        return ResponseEntity.ok(response);
    }



}