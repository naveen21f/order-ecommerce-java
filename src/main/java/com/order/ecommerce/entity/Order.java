package com.order.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ecommerce_order")
public class Order implements Serializable {

    @Id
    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "sub_total")
    private double subTotal;

    @Column(name = "total_amt")
    private double totalAmt;

    @Column(name = "tax")
    private double tax;

    @Column(name = "shipping_charges")
    private double shippingCharges;

    @Column(name = "title")
    private String title;

    @Column(name = "shipping_mode")
    private String shippingMode;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "payment_id", name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(referencedColumnName = "address_id", name = "billing_address_id")
    private Address billingAddress;

    @OneToOne
    @JoinColumn(referencedColumnName = "address_id", name = "shipping_address_id")
    private Address shippingAddress;

    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems;
}
