package com.bigob.loan.dto;

import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoanDTO {

    private Long customerId;

    private List<LoanDTO> loans;
}
