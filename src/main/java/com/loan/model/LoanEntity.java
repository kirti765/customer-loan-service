package com.loan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="LOAN_REQUEST")
public class LoanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOAN")
    @SequenceGenerator(name = "SEQ_LOAN", allocationSize = 1)
    @Column(name = "LOAN_ID")
    private Long loanId;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

}
