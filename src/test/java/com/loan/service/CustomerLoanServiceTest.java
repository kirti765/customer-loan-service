package com.loan.service;


import com.loan.exception.UsernameNotFoundException;
import com.loan.model.CustomerEntity;
import com.loan.model.LoanEntity;
import com.loan.dto.request.LoanRequest;
import com.loan.dto.response.LoanResponse;
import com.loan.repository.CustomerLoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerLoanServiceTest {

    @InjectMocks
    private CustomerLoanService customerLoanService;

    @Mock
    private CustomerLoanRepository loanRepositoryMock;

    @Test
    void getCustomerLoanInformationSuccess() {
        CustomerEntity customerRequest = getloanrequest();

        when(this.loanRepositoryMock.findCustomerByCustomerId(Mockito.any())).thenReturn(Optional.ofNullable(customerRequest));

        LoanResponse CustLoanRequestResponse = customerLoanService.getCustLoanInformation(Mockito.any());

        assertNotNull(CustLoanRequestResponse);
        assertEquals(customerRequest.getCustomerId(), CustLoanRequestResponse.getCustomerId());
    }

    @Test
    void getCustomerLoanInformationCustomerNotFound() {
        long customerId = 1L;
        CustomerEntity customer = null;
        when(this.loanRepositoryMock.findCustomerByCustomerId(customerId)).thenReturn(Optional.ofNullable(customer));

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> customerLoanService.getCustLoanInformation(customerId));

        assertEquals("Customer Loan Details not found by Customer Id :" + customerId, usernameNotFoundException.getMessage());
    }

    @Test
    void createCustomerLoanInformationSuccess() {
        LoanRequest loanRequest = LoanRequest.builder().customerId(101L).customerFullName("Test").loanAmount(BigDecimal.valueOf(2000)).build();
        CustomerEntity customer = getloanrequest();
        when(loanRepositoryMock.save(Mockito.any())).thenReturn(customer);

        CustomerEntity CustLoanRequestResponse = customerLoanService.saveCustomerLoan(loanRequest);

        assertNotNull(CustLoanRequestResponse);
        assertEquals(loanRequest.getCustomerId(), CustLoanRequestResponse.getCustomerId());
    }

    @Test
    void saveCustomerLoanInformationFailed() {
        LoanRequest loanRequest = LoanRequest.builder().customerId(101L).customerFullName("Test").loanAmount(BigDecimal.valueOf(2000)).build();
        when(loanRepositoryMock.save(Mockito.any())).thenThrow(HttpClientErrorException.BadRequest.class);
        assertThrows(HttpClientErrorException.BadRequest.class, () -> customerLoanService.saveCustomerLoan(loanRequest));
    }

    private CustomerEntity getloanrequest() {
        return CustomerEntity.builder().customerId(101L).customerFullName("Test").loanRequestDtos(List.of(LoanEntity.builder().loanAmount(BigDecimal.valueOf(600L)).build())).build();
    }

}
