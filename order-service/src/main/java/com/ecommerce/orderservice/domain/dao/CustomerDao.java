package com.ecommerce.orderservice.domain.dao;

import com.ecommerce.orderservice.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.dui = ?1")
    Optional<Customer> findByDui(String dui);
}
