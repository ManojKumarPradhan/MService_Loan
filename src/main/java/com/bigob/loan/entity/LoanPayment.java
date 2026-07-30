package com.bigob.loan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "loan_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanPayment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    private BigDecimal amountPaid;

    private LocalDate paymentDate;

    private String paymentMode;

    private String transactionReference;
}
