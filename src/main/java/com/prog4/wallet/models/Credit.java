package com.prog4.wallet.models;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Credit {

    private Long       creditId;
    private String     description;
    private BigDecimal amount;
    private Timestamp  dateBegin;
    private Timestamp  dateEnd;
    private BigDecimal loanInterest;
    private Long       accountId;

}