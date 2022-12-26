package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends CrudRepository<Address, String> {
}
