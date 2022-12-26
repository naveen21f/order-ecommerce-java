package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends CrudRepository<Payment, String> {
}
