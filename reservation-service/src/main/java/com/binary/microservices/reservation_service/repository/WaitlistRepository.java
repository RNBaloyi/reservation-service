package com.binary.microservices.reservation_service.repository;

import com.binary.microservices.reservation_service.model.WaitlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitlistRepository extends JpaRepository<WaitlistEntry, Long> {
    List<WaitlistEntry> findByTableIdOrderByPositionAsc(String tableId);

}