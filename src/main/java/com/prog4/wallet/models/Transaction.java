package com.prog4.wallet.models;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Transaction {

    private Long       transactionId;
    private String     label;
    private Timestamp  transactionDate;
    private BigDecimal amount;
    private String     transactionType;
    private BigDecimal balance;
    private Long       accountId;

}