package com.binary.microservices.reservation_service.repository;

import com.binary.microservices.reservation_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {}