package com.loan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loan.dto.request.LoanRequest;
import com.loan.exception.UsernameNotFoundException;
import com.loan.model.CustomerEntity;
import com.loan.dto.response.LoanResponse;
import com.loan.service.CustomerLoanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerLoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerLoanService customerLoanService;

    public static String createLoanUrl = "/loan/createLoan";

    public static String getLoanUrl = "/loan/";

    @Test
    void addCustomerLoanTestSuccess() throws Exception {
        CustomerEntity customer = CustomerEntity.builder().customerId(101L).customerFullName("Test").build();
        LoanRequest loanRequest = LoanRequest.builder().customerId(101L).customerFullName("Test").loanAmount(BigDecimal.valueOf(2000)).build();
        when(this.customerLoanService.saveCustomerLoan(Mockito.any())).thenReturn(customer);

        this.mockMvc.perform(post(createLoanUrl)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isCreated());

    }

    @Test
    void validationCheckExceptionaddCustomer() throws Exception {
        CustomerEntity customer = CustomerEntity.builder().customerId(null).customerFullName("Test").build();

        this.mockMvc.perform(post(createLoanUrl)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCustomerLoanAmountNotFoundSuccessful() throws Exception {
        long customerId = 101L;
        LoanResponse loanResponse = LoanResponse.builder().customerId(101L).customerFullName("Test").loanAmount(BigDecimal.valueOf(10000)).build();
        when(this.customerLoanService.getCustLoanInformation(customerId)).thenReturn(loanResponse);

        this.mockMvc.perform(get(getLoanUrl+customerId)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getCustomerLoanAmountNotFound() throws Exception {
        long customerId = 1L;
        when(this.customerLoanService.getCustLoanInformation(customerId)).thenThrow(new UsernameNotFoundException("Customer Loan Details not found by Customer Id :"+customerId));

        this.mockMvc.perform(get(getLoanUrl+customerId)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
