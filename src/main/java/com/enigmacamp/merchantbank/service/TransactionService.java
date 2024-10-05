package com.enigmacamp.merchantbank.service;

import com.enigmacamp.merchantbank.dto.request.TransactionRequest;
import com.enigmacamp.merchantbank.dto.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest TransactionRequest);
    TransactionResponse getTransactionById(String id);
    List<TransactionResponse> getAllTransactions();
}
