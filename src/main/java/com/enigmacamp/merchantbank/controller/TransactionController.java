package com.enigmacamp.merchantbank.controller;

import com.enigmacamp.merchantbank.base.BaseResponse;
import com.enigmacamp.merchantbank.constant.strings.Message;
import com.enigmacamp.merchantbank.constant.strings.PathApi;
import com.enigmacamp.merchantbank.dto.request.TransactionRequest;
import com.enigmacamp.merchantbank.dto.response.TransactionResponse;
import com.enigmacamp.merchantbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enigmacamp.merchantbank.mapper.BaseMapper.mapToBaseResponse;

@RestController
@RequestMapping(PathApi.TRANSACTIONS)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> create(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);
        BaseResponse<?> baseResponse = mapToBaseResponse(Message.SUCCESS_CREATE, HttpStatus.CREATED.value(), transactionResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @GetMapping(PathApi.BY_ID)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(id);
        BaseResponse<?> baseResponse = mapToBaseResponse(Message.SUCCESS_GET, HttpStatus.OK.value(), transactionResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllTransactions() {
        List<TransactionResponse> transactionResponseList = transactionService.getAllTransactions();
        BaseResponse<?> baseResponse = mapToBaseResponse(Message.SUCCESS_GET, HttpStatus.OK.value(), transactionResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
