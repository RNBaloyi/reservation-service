package com.binary.microservices.reservation_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDTO {
    private String tableId;
    private int capacity;
    private String location;
    private boolean available;


    public TableDTO() {}

    public TableDTO(String tableId, int capacity, String location, boolean available) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.location = location;
        this.available = available;
    }



}