package com.binary.microservices.reservation_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationResponse {

    private String reservationId;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tableId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String waitlistMessage;

    private ReservationResponse() {}

    public static ReservationResponse createSuccess(String reservationId, String tableId) {
        ReservationResponse response = new ReservationResponse();
        response.reservationId = reservationId;
        response.status = "success";
        response.tableId = tableId;
        return response;
    }


    public static ReservationResponse createWaitlisted(String waitlistMessage) {
        ReservationResponse response = new ReservationResponse();
        response.status = "waitlisted";
        response.waitlistMessage = waitlistMessage;
        return response;
    }

    public static ReservationResponse createCancelled(String tableId, String message) {
        ReservationResponse response = new ReservationResponse();
        response.status = "cancelled";
        response.tableId = tableId;
        response.waitlistMessage = message;
        return response;
    }

}