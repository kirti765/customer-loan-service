package com.loan.web;

import com.loan.dto.request.LoanRequest;
import com.loan.model.CustomerEntity;
import com.loan.dto.response.LoanResponse;
import com.loan.service.CustomerLoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@Tag(
        name = "Customer Loan detail API",
        description = "Get information about Customer Loan."
)
public class CustomerLoanController {

    @Autowired
    private CustomerLoanService customerLoanService;

    @PostMapping(value = "/createLoan", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save loan request")
    public ResponseEntity<String> saveLoanRequest(@RequestBody @Valid LoanRequest loanRequest) {
        CustomerEntity saveCustomerLoan = customerLoanService.saveCustomerLoan(loanRequest);
        return new ResponseEntity<>("Loan created successfully for Customer ID " + saveCustomerLoan.getCustomerId(), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get Customer loan amount")
    public ResponseEntity<LoanResponse> getLoanByCustomerId(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<>(customerLoanService.getCustLoanInformation(customerId),HttpStatus.OK);
    }
}
