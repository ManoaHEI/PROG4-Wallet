package com.prog4.wallet.models;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Transfer {

    private Long       transferId;
    private BigDecimal transferAmount;
    private Date       transferDate;
    private String     transferReason;
    private String     transferType;
    private String     transferStatus;
    private Long       accountId;

}