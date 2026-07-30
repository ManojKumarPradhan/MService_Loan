package com.bigob.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bigob.loan.constant.LoanConstants;
import com.bigob.loan.dto.CustomerLoanDTO;
import com.bigob.loan.dto.LoanDTO;
import com.bigob.loan.dto.LoanPaymentDTO;
import com.bigob.loan.dto.ResponseDTO;
import com.bigob.loan.service.LoanService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Tag(
		name = "CRUD REST APIs for Loans in Bigob",
		description = "CRUD REST APIs in Bigob to CREATE, UPDATE, FETCH AND DELETE loan details"
)

@RestController
@RequestMapping(value="/api/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/create")
    @RateLimiter(name = "postPutLimiter")
    public ResponseEntity<ResponseDTO<Long>> createLoan(
            @Valid @RequestBody LoanDTO loanDTO){

        Long loanId = loanService.createLoan(loanDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.<Long>builder()
                        .statusCode(LoanConstants.STATUS_201)
                        .statusMessage(LoanConstants.MESSAGE_201)
                        .body(loanId)
                        .build());
    }
    
    @GetMapping("/get")
    @Bulkhead(name = "getDeleteLimiter")
    public ResponseEntity<LoanDTO> getLoan(
            @RequestParam String loanNumber){

        return ResponseEntity.ok(
                loanService.getLoanByLoanNumber(loanNumber)
        );
    }
    
    @GetMapping("/customer")
    @Bulkhead(name = "getDeleteLimiter")
    public ResponseEntity<CustomerLoanDTO> getLoansByCustomerId(
            @RequestParam Long customerId){

        return ResponseEntity.ok(
                loanService.getLoansByCustomerId(customerId)
        );
    }
    
    @PutMapping("/update")
    @RateLimiter(name = "postPutLimiter")
    public ResponseEntity<ResponseDTO<String>> updateLoan(
            @Valid @RequestBody LoanDTO loanDTO){

        boolean updated = loanService.updateLoan(loanDTO);

        if(updated){
            return ResponseEntity.ok(
                    ResponseDTO.<String>builder()
                            .statusCode(LoanConstants.STATUS_200)
                            .statusMessage(LoanConstants.MESSAGE_200)
                            .body("Loan Updated Successfully")
                            .build());
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(ResponseDTO.<String>builder()
                        .statusCode(LoanConstants.STATUS_417)
                        .statusMessage(LoanConstants.MESSAGE_417_UPDATE)
                        .body("Update Failed")
                        .build());
    }
    
    @PostMapping("/payment")
    @RateLimiter(name = "postPutLimiter")
    public ResponseEntity<ResponseDTO<Long>> makePayment(
            @Valid @RequestBody LoanPaymentDTO paymentDTO){

        Long paymentId = loanService.makePayment(paymentDTO);

        return ResponseEntity.ok(
                ResponseDTO.<Long>builder()
                        .statusCode(LoanConstants.STATUS_200)
                        .statusMessage("Payment Recorded")
                        .body(paymentId)
                        .build());
    }
    
    @DeleteMapping("/delete")
    @Bulkhead(name = "getDeleteLimiter")
    public ResponseEntity<ResponseDTO<String>> deleteLoan(
            @RequestParam String loanNumber){

        boolean deleted = loanService.deleteLoan(loanNumber);

        if(deleted){
            return ResponseEntity.ok(
                    ResponseDTO.<String>builder()
                            .statusCode(LoanConstants.STATUS_200)
                            .statusMessage(LoanConstants.MESSAGE_200)
                            .body("Loan Deleted Successfully")
                            .build());
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(ResponseDTO.<String>builder()
                        .statusCode(LoanConstants.STATUS_417)
                        .statusMessage(LoanConstants.MESSAGE_417_DELETE)
                        .body("Delete Failed")
                        .build());
    }
    
}