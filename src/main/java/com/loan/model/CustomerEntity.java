package com.loan.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="CUSTOMER_MS")
public class CustomerEntity {

    @Id
    @Column(name = "customer_id", nullable = false, updatable = false)
    private Long customerId;

    @Column(name = "customer_full_name", updatable = false)
    private String  customerFullName;

    @OneToMany(targetEntity = LoanEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id", nullable = false, referencedColumnName = "customer_id", updatable = false)
    private List<LoanEntity> loanRequestDtos;




}
