package com.loan.service;

import com.loan.exception.UsernameNotFoundException;
import com.loan.model.CustomerEntity;
import com.loan.model.LoanEntity;
import com.loan.dto.request.LoanRequest;
import com.loan.dto.response.LoanResponse;
import com.loan.repository.CustomerLoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerLoanService {

    @Autowired
    private CustomerLoanRepository customerLoanRepository;

    public CustomerEntity saveCustomerLoan(LoanRequest loanRequest) {
        CustomerEntity customer = prepareLoanRequest(loanRequest);
        log.info("Save loan details for {} " , customer.getCustomerId());
        return customerLoanRepository.save(customer);
    }

    public LoanResponse getCustLoanInformation(Long customerId) {
        log.info("get loan details for {} " , customerId);
        Optional<CustomerEntity> customerLoanInfo = customerLoanRepository.findCustomerByCustomerId(customerId);
        if(customerLoanInfo.isEmpty()){
            throw new UsernameNotFoundException("Customer Loan Details not found by Customer Id :"+customerId);
        }
        BigDecimal loanAmount= customerLoanInfo.get().getLoanRequestDtos().stream().map(i -> i.getLoanAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new LoanResponse(customerLoanInfo.get().getCustomerId(),customerLoanInfo.get().getCustomerFullName(),loanAmount);
    }

    private CustomerEntity prepareLoanRequest(LoanRequest loanRequest) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(loanRequest.getCustomerId());
        customerEntity.setCustomerFullName(loanRequest.getCustomerFullName());
        List<LoanEntity> loanEntities =new ArrayList<>();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setLoanAmount(loanRequest.getLoanAmount());
        loanEntities.add(loanEntity);
        customerEntity.setLoanRequestDtos(loanEntities);
        return customerEntity;
    }
}
