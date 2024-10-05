package com.enigmacamp.merchantbank.mapper;

import com.enigmacamp.merchantbank.dto.request.CustomerRequest;
import com.enigmacamp.merchantbank.dto.response.CustomerResponse;
import com.enigmacamp.merchantbank.entity.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerMapper {
    public static Customer customerRequestToCustomer(CustomerRequest customerRequest)  {
        LocalDate date = null;
        try {
             date = LocalDate.parse(customerRequest.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ignored) {

        }
        return Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .dateOfBirth(date)
                .phone(customerRequest.getPhone())
                .build();
    }

    public static CustomerResponse customerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .phone(customer.getPhone())
                .createdBy(customer.getCreatedBy())
                .updatedAt(customer.getUpdatedAt())
                .updatedBy(customer.getUpdatedBy())
                .build();
    }
}
