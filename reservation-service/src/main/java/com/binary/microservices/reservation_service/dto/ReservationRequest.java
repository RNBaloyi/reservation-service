package com.binary.microservices.reservation_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationRequest {

    private String tableId;
    private String customerId;
    private String reservationType;
    private List<String> preferences;

    public ReservationRequest(String tableId, String customerId,
                              String reservationType, List<String> preferences) {
        this.tableId = tableId;
        this.customerId = customerId;
        this.reservationType = reservationType;
        this.preferences = preferences;
    }

}