package com.bigob.loan.mapper;

import com.bigob.loan.dto.LoanDTO;
import com.bigob.loan.entity.Loan;

public class LoanMapper {

    private LoanMapper() {
    	// restrict instantiation
    }

    public static LoanDTO mapToLoanDTO(Loan loan, LoanDTO dto) {

        dto.setLoanId(loan.getLoanId());
        dto.setCustomerId(loan.getCustomerId());
        dto.setAccountNumber(loan.getAccountNumber());
        dto.setLoanNumber(loan.getLoanNumber());
        dto.setLoanType(loan.getLoanType());
        dto.setPrincipalAmount(loan.getPrincipalAmount());
        dto.setInterestRate(loan.getInterestRate());
        dto.setTenureMonths(loan.getTenureMonths());
        dto.setEmiAmount(loan.getEmiAmount());
        dto.setOutstandingAmount(loan.getOutstandingAmount());
        dto.setStatus(loan.getStatus());

        return dto;
    }

    public static Loan mapToLoan(LoanDTO dto, Loan loan){

        loan.setLoanId(dto.getLoanId());
        loan.setCustomerId(dto.getCustomerId());
        loan.setAccountNumber(dto.getAccountNumber());
        loan.setLoanNumber(dto.getLoanNumber());
        loan.setLoanType(dto.getLoanType());
        loan.setPrincipalAmount(dto.getPrincipalAmount());
        loan.setInterestRate(dto.getInterestRate());
        loan.setTenureMonths(dto.getTenureMonths());
        loan.setEmiAmount(dto.getEmiAmount());
        loan.setOutstandingAmount(dto.getOutstandingAmount());
        loan.setStatus(dto.getStatus());

        return loan;
    }

}
