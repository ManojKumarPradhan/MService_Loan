package com.bigob.loan.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    private Long loanId;

    private Long customerId;

    private Long accountNumber;

    private String loanNumber;

    private String loanType;

    private BigDecimal principalAmount;

    private BigDecimal interestRate;

    private Integer tenureMonths;

    private BigDecimal emiAmount;

    private BigDecimal outstandingAmount;

    private String status;
}
