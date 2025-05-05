package com.binary.microservices.reservation_service.config;

import com.binary.microservices.reservation_service.model.Customer;
import com.binary.microservices.reservation_service.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner initCustomerData(CustomerRepository customerRepo) {
        return args -> {
            List<Customer> sampleCustomers = Arrays.asList(
                    new Customer("C101", "John Doe", "john@example.com"),
                    new Customer("C102", "Jane Smith", "jane@example.com"),
                    new Customer("C103", "Sam Wilson", "sam@example.com"),
                    new Customer("C104", "Emily Davis", "emily@example.com")
            );

            sampleCustomers.forEach(customer -> {
                if (!customerRepo.existsById(customer.getCustomerId())) {
                    customerRepo.save(customer);
                    System.out.println("Created customer: " + customer.getName());
                }
            });
        };
    }

}
