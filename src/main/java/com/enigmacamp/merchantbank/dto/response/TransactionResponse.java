package com.enigmacamp.merchantbank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransactionResponse {
    private String id;
    private String customerId;
    private String destinationCustomerId;
    private Double nominal;
    private String transactionDate;
}
