package com.binary.microservices.reservation_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class WaitlistEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tableId;
    private String customerId;

    @ElementCollection
    private List<String> preferences;
    private int position;

}