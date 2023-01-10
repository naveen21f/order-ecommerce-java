package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Order;
import com.order.ecommerce.entity.OrderItem;
import com.order.ecommerce.entity.OrderItemPk;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, OrderItemPk> {

}
