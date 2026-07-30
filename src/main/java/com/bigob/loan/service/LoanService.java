package com.bigob.loan.service;

import com.bigob.loan.dto.CustomerLoanDTO;
import com.bigob.loan.dto.LoanDTO;
import com.bigob.loan.dto.LoanPaymentDTO;

public interface LoanService {

    Long createLoan(LoanDTO loanDTO);

    LoanDTO getLoanByLoanNumber(String loanNumber);

    CustomerLoanDTO getLoansByCustomerId(Long customerId);

    boolean updateLoan(LoanDTO loanDTO);

    Long makePayment(LoanPaymentDTO paymentDTO);

    boolean deleteLoan(String loanNumber);

}
