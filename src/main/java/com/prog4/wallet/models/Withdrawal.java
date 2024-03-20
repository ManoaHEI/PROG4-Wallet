package com.prog4.wallet.models;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Withdrawal {

    private long       withdrawalId;
    private long       accountId;
    private BigDecimal withdrawalAmount;
    private Timestamp  withdrawalDate;

}
