package com.loan.repository;

import com.loan.model.CustomerEntity;
import com.loan.model.LoanEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CustomerLoanRepositoryTest {

    @Autowired
    private CustomerLoanRepository customerLoanRepositoryTest;

    @AfterEach
    void tearDown() {
        customerLoanRepositoryTest.deleteAll();
    }

    @Test
    void itShouldCheckIfSaveLoanSucessful() {
        //given
        List<LoanEntity> loanRequestDtos = new ArrayList<>();
        LoanEntity loanRequestDto =new LoanEntity(1L, BigDecimal.valueOf(600));
        loanRequestDtos.add(loanRequestDto);
        CustomerEntity customerDto = new CustomerEntity(Long.valueOf(11),"Kirti",loanRequestDtos);

        //when
        CustomerEntity customerLoanData = customerLoanRepositoryTest.save(customerDto);

        //then
        assertEquals(11L, customerLoanData.getCustomerId());
    }

    @Test
    void itShouldCheckIfLoanExistsByCustomerId() {
        //given
        List<LoanEntity> loanRequestDtos = new ArrayList<>();
        LoanEntity loanRequestDto =new LoanEntity(1L, BigDecimal.valueOf(600));
        loanRequestDtos.add(loanRequestDto);
        CustomerEntity customerDto = new CustomerEntity(Long.valueOf(11),"Kirti",loanRequestDtos);

        //when
        customerLoanRepositoryTest.save(customerDto);

        //then
        boolean existingCustomer = customerLoanRepositoryTest.existsById(customerDto.getCustomerId());

        assertTrue(existingCustomer);
    }

    @Test
    void itShouldCheckIfLoanDoesNotExistsByCustomerId() {
        boolean existingCustomer = customerLoanRepositoryTest.existsById(Long.valueOf(101));
        assertFalse(existingCustomer);

    }

}