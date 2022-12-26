package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends CrudRepository<Order, String> {
}
