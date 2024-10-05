package com.enigmacamp.merchantbank.entity;

import com.enigmacamp.merchantbank.base.BaseEntity;
import com.enigmacamp.merchantbank.constant.strings.PathDB;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = PathDB.CUSTOMER)
public class Customer extends BaseEntity {
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    private String phone;

    @Column
    private Double balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
