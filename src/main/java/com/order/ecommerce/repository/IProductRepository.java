package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//TODO update extend to JPA Repo
@Repository
public interface IProductRepository extends JpaRepository<Product, String> {
}
