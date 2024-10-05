package com.enigmacamp.merchantbank.entity;

import com.enigmacamp.merchantbank.base.BaseEntity;
import com.enigmacamp.merchantbank.constant.strings.PathDB;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = PathDB.TRANSACTION)
public class Transaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "destination_customer_id")
    private String destinationCustomerId;

    @Column
    private Double nominal;

    @Column(name = "transaction_date", updatable = false)
    @CreatedDate
    private LocalDateTime transactionDate;

}
