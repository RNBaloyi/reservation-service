package com.binary.microservices.reservation_service.client;

import com.binary.microservices.reservation_service.dto.TableDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "table-service", url = "${table.service.url}")
public interface TableServiceClient {
    @GetMapping("/tables/{tableId}")
    TableDTO getTable(@PathVariable String tableId);

    @PutMapping("/tables/{tableId}")
    TableDTO updateTableAvailability(
            @PathVariable String tableId,
            @RequestBody TableDTO tableDTO);
}