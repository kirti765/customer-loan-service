package com.loan.repository;

import com.loan.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerLoanRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findCustomerByCustomerId(Long customerId);
}
