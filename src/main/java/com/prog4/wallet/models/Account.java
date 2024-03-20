package com.prog4.wallet.models;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Account {

    private Long       accountId;
    private String     name;
    private String     firstname;
    private Date       birthdate;
    private BigDecimal salary;
    private BigDecimal balance;
    private BigDecimal credit;
    private double     creditInterest;
    private boolean    canTakeCredit = false;

}