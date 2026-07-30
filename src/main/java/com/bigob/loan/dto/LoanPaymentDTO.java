package com.bigob.loan.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanPaymentDTO {

    private Long paymentId;

    private Long loanId;

    private BigDecimal amountPaid;

    private LocalDate paymentDate;

    private String paymentMode;

    private String transactionReference;
}