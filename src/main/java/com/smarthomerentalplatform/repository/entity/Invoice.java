package com.smarthomerentalplatform.repository.entity;

import com.smarthomerentalplatform.repository.status.InvoiceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(
        name = "invoices",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_invoices_contract_period",
                columnNames = {"contract_id", "billing_period"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    /** First day of the month this invoice covers. */
    @Column(name = "billing_period", nullable = false)
    private LocalDate billingPeriod;

    @Column(name = "rent_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal rentAmount;

    @Column(name = "previous_electricity_index", nullable = false, precision = 12, scale = 3)
    private BigDecimal previousElectricityIndex;

    @Column(name = "current_electricity_index", nullable = false, precision = 12, scale = 3)
    private BigDecimal currentElectricityIndex;

    @Column(name = "electricity_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal electricityAmount;

    @Column(name = "previous_water_index", nullable = false, precision = 12, scale = 3)
    private BigDecimal previousWaterIndex;

    @Column(name = "current_water_index", nullable = false, precision = 12, scale = 3)
    private BigDecimal currentWaterIndex;

    @Column(name = "water_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal waterAmount;

    @Column(name = "other_fees", nullable = false, precision = 15, scale = 2)
    private BigDecimal otherFees;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private InvoiceStatus status;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
}
