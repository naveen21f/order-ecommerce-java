package com.order.ecommerce.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ecommerce_product")
public class Product implements Serializable {

    @Id
    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, mappedBy = "product")
    private List<OrderItem> orderItems;
}
