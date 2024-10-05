package com.enigmacamp.merchantbank.service;

import com.enigmacamp.merchantbank.dto.request.CustomerRequest;
import com.enigmacamp.merchantbank.dto.response.CustomerResponse;
import com.enigmacamp.merchantbank.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    void createCustomer(Customer customer);
    List<CustomerResponse> getAllCustomer();
    CustomerResponse getCustomerById(String id);
    Customer getById(String id);
    CustomerResponse updateCustomerById(CustomerRequest customerRequest);
    CustomerResponse deleteCustomerById(String id);
}
