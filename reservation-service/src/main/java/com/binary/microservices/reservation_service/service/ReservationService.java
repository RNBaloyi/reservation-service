package com.binary.microservices.reservation_service.service;

import com.binary.microservices.reservation_service.client.TableServiceClient;
import com.binary.microservices.reservation_service.dto.*;
import com.binary.microservices.reservation_service.exception.*;
import com.binary.microservices.reservation_service.model.*;
import com.binary.microservices.reservation_service.repository.*;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
public class ReservationService implements IReservationService{

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    private final TableServiceClient tableServiceClient;
    private final WaitlistRepository waitlistRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(TableServiceClient tableServiceClient,
                              WaitlistRepository waitlistRepository,
                              CustomerRepository customerRepository,
                              ReservationRepository reservationRepository) {
        this.tableServiceClient = tableServiceClient;
        this.waitlistRepository = waitlistRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse processReservation(ReservationRequest request) {
        TableDTO table;

        try {
            table = tableServiceClient.getTable(request.getTableId());

        } catch (FeignException.NotFound ex) {

            throw new TableNotFoundException("Table not found");
        } catch (FeignException ex) {
            throw new RuntimeException("Error retrieving table from table service: " + ex.getMessage(), ex);
        }


        if (!"reserve".equalsIgnoreCase(request.getReservationType()) &&
                !"cancel".equalsIgnoreCase(request.getReservationType())) {
            throw new InvalidReservationException("Invalid reservation type");
        }

        customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));


        try {
            if ("reserve".equalsIgnoreCase(request.getReservationType())) {
                return processReservationRequest(request);
            } else {
                return processCancellationRequest(request);
            }
        } catch (TableNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Reservation processing failed", e);
        }
    }

    private ReservationResponse processReservationRequest(ReservationRequest request) {
        TableDTO table = tableServiceClient.getTable(request.getTableId());

        if (table.isAvailable()) {

            table.setAvailable(false);
            tableServiceClient.updateTableAvailability(request.getTableId(), table);

            Reservation reservation = new Reservation();
            reservation.setTableId(request.getTableId());
            reservation.setCustomerId(request.getCustomerId());
            reservation = reservationRepository.save(reservation);

            return ReservationResponse.createSuccess(reservation.getReservationId(), request.getTableId());
        } else {
            int position = addToWaitlist(request);
            return ReservationResponse.createWaitlisted("You are #" + position + " in the queue.");
        }
    }


    private ReservationResponse processCancellationRequest(ReservationRequest request) {


        TableDTO table = tableServiceClient.getTable(request.getTableId());



        if (table == null) {
            throw new TableNotFoundException("Table not found");
        }

        Optional<Reservation> existingReservation = reservationRepository
                .findByTableIdAndCustomerId(request.getTableId(), request.getCustomerId());

        List<WaitlistEntry> waitlist = waitlistRepository.findByTableIdOrderByPositionAsc(request.getTableId());

        if (!waitlist.isEmpty()) {
            WaitlistEntry nextCustomer = findNextCustomerByPreferences(waitlist, request.getTableId());
            if (nextCustomer != null) {
                Reservation newReservation = new Reservation();
                newReservation.setTableId(request.getTableId());
                newReservation.setCustomerId(nextCustomer.getCustomerId());
                newReservation = reservationRepository.save(newReservation);

                tableServiceClient.updateTableAvailability(request.getTableId(),
                        new TableDTO(request.getTableId(), 0, "", false));

                existingReservation.ifPresent(res -> {
                    res.setStatus(Reservation.ReservationStatus.CANCELLED);
                    reservationRepository.save(res);
                });

                waitlistRepository.delete(nextCustomer);
                updateWaitlistPositions(request.getTableId());

                return ReservationResponse.createSuccess(newReservation.getReservationId(), request.getTableId());
            }
        }

        tableServiceClient.updateTableAvailability(request.getTableId(),
                new TableDTO(request.getTableId(), 0, "", true));

        existingReservation.ifPresent(res -> {
            res.setStatus(Reservation.ReservationStatus.CANCELLED);
            reservationRepository.save(res);
        });

        return ReservationResponse.createCancelled(request.getTableId(), "Table is now available");
    }

    private int addToWaitlist(ReservationRequest request) {
        List<WaitlistEntry> existingEntries = waitlistRepository.findByTableIdOrderByPositionAsc(request.getTableId());
        int position = existingEntries.size() + 1;

        WaitlistEntry entry = new WaitlistEntry();
        entry.setTableId(request.getTableId());
        entry.setCustomerId(request.getCustomerId());
        entry.setPreferences(request.getPreferences());
        entry.setPosition(position);

        waitlistRepository.save(entry);
        return position;
    }

    private void updateWaitlistPositions(String tableId) {
        List<WaitlistEntry> entries = waitlistRepository.findByTableIdOrderByPositionAsc(tableId);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setPosition(i + 1);
        }
        waitlistRepository.saveAll(entries);
    }

    private void validateCustomer(String customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    private WaitlistEntry findNextCustomerByPreferences(List<WaitlistEntry> waitlist, String tableId) {
        TableDTO table = tableServiceClient.getTable(tableId);
        if (table != null) {
            for (WaitlistEntry entry : waitlist) {
                if (entry.getPreferences() != null &&
                        entry.getPreferences().contains(table.getLocation())) {
                    return entry;
                }
            }
        }
        return waitlist.isEmpty() ? null : waitlist.get(0);
    }
}