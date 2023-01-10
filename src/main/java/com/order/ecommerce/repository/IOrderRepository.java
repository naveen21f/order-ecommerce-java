package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends CrudRepository<Order, String>, RevisionRepository<Order, String, Integer> {

    Page<Order> findByCustomerId(String customerId, Pageable pageable);
    List<Order> findByCustomerId(String customerId);

}
