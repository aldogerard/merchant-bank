package com.enigmacamp.merchantbank.controller;

import com.enigmacamp.merchantbank.base.BaseResponse;
import com.enigmacamp.merchantbank.constant.strings.Message;
import com.enigmacamp.merchantbank.constant.strings.PathApi;
import com.enigmacamp.merchantbank.dto.request.CustomerRequest;
import com.enigmacamp.merchantbank.dto.response.CustomerResponse;
import com.enigmacamp.merchantbank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enigmacamp.merchantbank.mapper.BaseMapper.mapToBaseResponse;

@RestController
@RequestMapping(PathApi.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResponse> customerResponseList = customerService.getAllCustomer();
        BaseResponse<?> baseResponse = mapToBaseResponse(Message.SUCCESS_GET_ALL, HttpStatus.OK.value(), customerResponseList);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @GetMapping(PathApi.BY_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_CUSTOMER')")
    public ResponseEntity<?> getCustomersById(@PathVariable  String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        BaseResponse<?> baseResponse = mapToBaseResponse(Message.SUCCESS_GET, HttpStatus.OK.value(), customerResponse);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_CUSTOMER')")
    public ResponseEntity<?> updateCustomerById(@RequestBody CustomerRequest customerRequest) {

        CustomerResponse customerResponse = customerService.updateCustomerById(customerRequest);
        BaseResponse<?> baseResponse = mapToBaseResponse(Message.SUCCESS_UPDATE, HttpStatus.OK.value(), customerResponse);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_CUSTOMER')")
    @DeleteMapping(PathApi.BY_ID)
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        CustomerResponse customerResponse =  this.customerService.deleteCustomerById(id);
        BaseResponse<?> response = mapToBaseResponse(Message.SUCCESS_DELETE, HttpStatus.OK.value(), customerResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
