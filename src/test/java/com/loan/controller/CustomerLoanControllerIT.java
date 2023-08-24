package com.loan.controller;

import com.loan.exception.ApiError;
import com.loan.dto.request.LoanRequest;
import com.loan.dto.response.LoanResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerLoanControllerIT {
    @LocalServerPort
    private  int port;

    private String baseUrl ="http://localhost";
    public static String createLoanUrl = "/createLoan";
    public static String getLoanDetails = "/101";

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    private void setUp() {
        baseUrl= baseUrl.concat(":").concat(port +"").concat("/loan"+"");

    }

    @Test
    void testSaveLoan(){
        baseUrl=baseUrl.concat(createLoanUrl);
        LoanRequest customer = LoanRequest.builder().customerId(101L).customerFullName("Test").loanAmount(BigDecimal.valueOf(2000)).build();
        String loanResponse = restTemplate.postForObject(baseUrl, customer, String.class);
        Assertions.assertEquals("Loan created successfully for Customer ID "+customer.getCustomerId(),loanResponse);
    }

    @Test
    void exceptionToTestSaveLoan(){
        baseUrl = baseUrl.concat(createLoanUrl);
        LoanRequest customer = LoanRequest.builder().customerId(101L).customerFullName("Test").loanAmount(BigDecimal.valueOf(200)).build();

        HttpClientErrorException.BadRequest thrown = Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
        restTemplate.postForObject(baseUrl, customer, ApiError.class);
        });
    }

    @Test
    @Sql(statements = "insert into customer_ms (customer_id, customer_full_name) values (11L,'Test')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into loan_request (customer_id,loan_amount,loan_id) values(11L,800,101)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetLoan(){
        baseUrl=baseUrl.concat("/11");
        LoanResponse loanResponse = restTemplate.getForObject(baseUrl, LoanResponse.class);

        Assertions.assertEquals("Test",loanResponse.getCustomerFullName());
    }

    @Test
    void testExceptionGetLoanNotFound(){
        HttpClientErrorException.NotFound thrown = Assertions.assertThrows(HttpClientErrorException.NotFound.class,()->{
            baseUrl=baseUrl.concat("/13");
            restTemplate.getForObject(baseUrl, ApiError.class);
        });
    }
}
