package com.enigmacamp.merchantbank.service.impl;

import com.enigmacamp.merchantbank.constant.strings.Message;
import com.enigmacamp.merchantbank.dto.request.CustomerRequest;
import com.enigmacamp.merchantbank.dto.request.TransactionRequest;
import com.enigmacamp.merchantbank.dto.response.TransactionResponse;
import com.enigmacamp.merchantbank.entity.*;
import com.enigmacamp.merchantbank.repository.TransactionRepository;
import com.enigmacamp.merchantbank.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository TransactionRepository;
    private final CustomerService customerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        try {
            Customer customer = customerService.getById(transactionRequest.getCustomerId());

            if (customer.getBalance() <= transactionRequest.getNominal()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance is not enough");
            }

            Customer destinationCustomer = customerService.getById(transactionRequest.getDestinationAccountId());

            CustomerRequest customerRequest = CustomerRequest.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .phone(customer.getPhone())
                    .dateOfBirth(customer.getDateOfBirth() != null ? customer.getDateOfBirth().toString() : null)
                    .nominal(customer.getBalance() - transactionRequest.getNominal())
                    .build();

            CustomerRequest destinationCustomerRequest = CustomerRequest.builder()
                    .id(destinationCustomer.getId())
                    .firstName(destinationCustomer.getFirstName())
                    .lastName(destinationCustomer.getLastName())
                    .phone(destinationCustomer.getPhone())
                    .dateOfBirth(customer.getDateOfBirth() != null ? customer.getDateOfBirth().toString() : null)
                    .nominal(destinationCustomer.getBalance() + transactionRequest.getNominal())
                    .build();

            customerService.updateCustomerById(customerRequest);
            customerService.updateCustomerById(destinationCustomerRequest);

            Transaction transaction = Transaction.builder()
                    .customer(customer)
                    .nominal(transactionRequest.getNominal())
                    .destinationCustomerId(destinationCustomer.getId())
                    .build();

            TransactionRepository.saveAndFlush(transaction);

            return TransactionResponse.builder()
                    .id(transaction.getId())
                    .customerId(transaction.getCustomer().getId())
                    .nominal(transaction.getNominal())
                    .destinationCustomerId(destinationCustomer.getId())
                    .transactionDate(transaction.getCreatedAt().toString())
                    .build();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    private TransactionResponse transactionToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .nominal(transaction.getNominal())
                .transactionDate(transaction.getCreatedAt().toString())
                .build();
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = getByIdOrThrow(id);

        return TransactionResponse.builder()
               .id(transaction.getId())
               .customerId(transaction.getCustomer().getId())
               .nominal(transaction.getNominal())
                .destinationCustomerId(transaction.getDestinationCustomerId())
               .transactionDate(transaction.getCreatedAt().toString())
               .build();
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = TransactionRepository.findAll();
        if (transactions.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.NOT_FOUND);

        return transactions.stream().map(transaction -> TransactionResponse
                        .builder()
                        .id(transaction.getId())
                        .customerId(transaction.getCustomer().getId())
                        .destinationCustomerId(transaction.getDestinationCustomerId())
                        .nominal(transaction.getNominal())
                        .transactionDate(transaction.getCreatedAt().toString())
                        .build())
                        .collect(Collectors.toList());

    }

    private Transaction getByIdOrThrow(String id) {
        Optional<Transaction> transaction = TransactionRepository.findById(id);
        return transaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not found"));
    }

}
