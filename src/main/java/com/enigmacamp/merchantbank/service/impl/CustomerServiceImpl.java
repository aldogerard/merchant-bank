package com.enigmacamp.merchantbank.service.impl;

import com.enigmacamp.merchantbank.constant.strings.Message;
import com.enigmacamp.merchantbank.dto.request.CustomerRequest;
import com.enigmacamp.merchantbank.dto.response.CustomerResponse;
import com.enigmacamp.merchantbank.entity.Customer;
import com.enigmacamp.merchantbank.mapper.CustomerMapper;
import com.enigmacamp.merchantbank.repository.CustomerRepository;
import com.enigmacamp.merchantbank.service.CustomerService;
import com.enigmacamp.merchantbank.service.UserService;
import com.enigmacamp.merchantbank.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCustomer(Customer customer) {
        try {
            customerRepository.saveAndFlush(customer);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,Message.IS_EXIST);
        }
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.NOT_FOUND);
        return customerList.stream().map(CustomerMapper::customerToCustomerResponse).toList();
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = getByIdOrThrow(id);
        return CustomerMapper.customerToCustomerResponse(customer);
    }

    @Override
    public Customer getById(String id) {
        return getByIdOrThrow(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse updateCustomerById(CustomerRequest customerRequest) {
        try {
            validationUtil.validate(customerRequest);

            Customer findCustomer = getByIdOrThrow(customerRequest.getId());
            findCustomer.setFirstName(customerRequest.getFirstName());
            findCustomer.setLastName(customerRequest.getLastName());
            findCustomer.setPhone(customerRequest.getPhone());

            if (customerRequest.getNominal() != null) {
                findCustomer.setBalance(customerRequest.getNominal());
            }

            if (customerRequest.getDateOfBirth() != null) {
                LocalDate date = LocalDate.parse(customerRequest.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                findCustomer.setDateOfBirth(date);
            }
            customerRepository.saveAndFlush(findCustomer);

            return CustomerMapper.customerToCustomerResponse(findCustomer);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse deleteCustomerById(String id) {
        try {
            Customer customer = getByIdOrThrow(id);
            customerRepository.deleteById(id);

            userService.deleteUserById(customer.getUser().getId());
            return CustomerMapper.customerToCustomerResponse(customer);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private Customer getByIdOrThrow(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Message.NOT_FOUND));
    }

}
