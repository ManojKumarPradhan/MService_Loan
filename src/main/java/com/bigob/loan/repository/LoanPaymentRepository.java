package com.bigob.loan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bigob.loan.entity.LoanPayment;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {

    List<LoanPayment> findByLoanLoanId(Long loanId);

    void deleteByLoanLoanId(Long loanId);

}