package com.bigob.loan.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigob.loan.dto.CustomerLoanDTO;
import com.bigob.loan.dto.LoanDTO;
import com.bigob.loan.dto.LoanPaymentDTO;
import com.bigob.loan.entity.Loan;
import com.bigob.loan.entity.LoanPayment;
import com.bigob.loan.exception.LoanAlreadyExistsException;
import com.bigob.loan.exception.ResourceNotFoundException;
import com.bigob.loan.mapper.LoanMapper;
import com.bigob.loan.mapper.LoanPaymentMapper;
import com.bigob.loan.repository.LoanPaymentRepository;
import com.bigob.loan.repository.LoanRepository;
import com.bigob.loan.service.LoanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanPaymentRepository paymentRepository;
    
    @Override
    public Long createLoan(LoanDTO loanDTO) {

        loanRepository.findByLoanNumber(loanDTO.getLoanNumber())
                .ifPresent(loan ->
                        {throw new LoanAlreadyExistsException(
                                "Loan already exists with Loan Number : "
                                        + loanDTO.getLoanNumber());});

        Loan loan = LoanMapper.mapToLoan(
                loanDTO,new Loan());

        loan.setOutstandingAmount(
                loan.getPrincipalAmount());

        Loan savedLoan = loanRepository.save(loan);

        return savedLoan.getLoanId();
    }
    
    @Override
    public LoanDTO getLoanByLoanNumber(String loanNumber) {

        Loan loan = loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "Loan Number",
                                loanNumber));

        return LoanMapper.mapToLoanDTO(
                loan,new LoanDTO());
    }
    
    @Override
    public CustomerLoanDTO getLoansByCustomerId(Long customerId) {

        List<Loan> loans =
                loanRepository.findByCustomerId(customerId);

        if(loans.isEmpty()){
            throw new ResourceNotFoundException(
                    "Loan",
                    "Customer Id",
                    customerId.toString());
        }

        List<LoanDTO> loanDTOList =
                loans.stream()
                        .map(loan ->
                                LoanMapper.mapToLoanDTO(
                                        loan,new LoanDTO()))
                        .toList();

        return CustomerLoanDTO.builder()
                .customerId(customerId)
                .loans(loanDTOList)
                .build();
    }
    
    @Override
    public boolean updateLoan(LoanDTO loanDTO) {

        Loan loan = loanRepository.findById(
                        loanDTO.getLoanId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "Loan Id",
                                loanDTO.getLoanId().toString()));

        LoanMapper.mapToLoan(
                loanDTO,loan);

        loanRepository.save(loan);

        return true;
    }
    
    @Override
    @Transactional
    public Long makePayment(
            LoanPaymentDTO paymentDTO) {

        Loan loan = loanRepository.findById(
                        paymentDTO.getLoanId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "Loan Id",
                                paymentDTO.getLoanId().toString()));

        LoanPayment payment =
                LoanPaymentMapper.mapToLoanPayment(
                        paymentDTO,new LoanPayment());

        payment.setLoan(loan);

        LoanPayment savedPayment =
                paymentRepository.save(payment);

        loan.setOutstandingAmount(
                loan.getOutstandingAmount()
                        .subtract(paymentDTO.getAmountPaid()));

        if(loan.getOutstandingAmount().signum()==0){
            loan.setStatus("CLOSED");
        }

        loanRepository.save(loan);

        return savedPayment.getPaymentId();
    }
    
    @Override
    @Transactional
    public boolean deleteLoan(String loanNumber) {

        Loan loan = loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "Loan Number",
                                loanNumber));

        paymentRepository.deleteByLoanLoanId(
                loan.getLoanId());

        loanRepository.delete(loan);

        return true;
    }
    
}
