package com.order.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ecommerce_payment")
public class Payment implements Serializable {

    @Id
    @Column(name = "payment_id", nullable = false, unique = true)
    private String paymentId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "payment_mode", nullable = false)
    private String paymentMode;

    @Column(name = "confirmation_number", nullable = false)
    private String confirmationNumber;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private Order order;
}
