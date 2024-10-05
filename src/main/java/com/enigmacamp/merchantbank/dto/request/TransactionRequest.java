package com.enigmacamp.merchantbank.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransactionRequest {
    private String customerId;

    private String destinationAccountId;

    @Positive
    private Double nominal;

}
