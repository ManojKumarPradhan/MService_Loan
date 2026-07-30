package com.bigob.loan.mapper;

import com.bigob.loan.dto.LoanPaymentDTO;
import com.bigob.loan.entity.LoanPayment;

public class LoanPaymentMapper {

    private LoanPaymentMapper(){
    	// restrict instantiation
    }

    public static LoanPaymentDTO mapToLoanPaymentDTO(
            LoanPayment payment,
            LoanPaymentDTO dto){

        dto.setPaymentId(payment.getPaymentId());
        dto.setLoanId(payment.getLoan().getLoanId());
        dto.setAmountPaid(payment.getAmountPaid());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMode(payment.getPaymentMode());
        dto.setTransactionReference(payment.getTransactionReference());

        return dto;
    }

    public static LoanPayment mapToLoanPayment(
            LoanPaymentDTO dto,
            LoanPayment payment){

        payment.setPaymentId(dto.getPaymentId());
        payment.setAmountPaid(dto.getAmountPaid());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setTransactionReference(dto.getTransactionReference());

        return payment;
    }

}
