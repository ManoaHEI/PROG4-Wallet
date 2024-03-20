package com.prog4.wallet.models;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Transfer {

    private Long       transferId;
    private Long       accountId;
    private Long       receiverId;
    private boolean    haveSameBank;
    private String     bankName;
    private BigDecimal transferAmount;
    private String     transferCategory;
    private Timestamp  effectiveDate;
    private Timestamp  registrationDate;
    private String     transferRef;
    private boolean    isCanceled;

}