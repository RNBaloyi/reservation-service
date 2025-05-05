package com.binary.microservices.reservation_service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    private String customerId;
    private String name;
    private String email;


    public Customer() {}

    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

}
