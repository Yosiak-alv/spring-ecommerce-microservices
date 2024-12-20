package com.ecommerce.paymentservice.domain.dao;

import com.ecommerce.paymentservice.domain.model.TransactionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionalDetailsDao extends JpaRepository<TransactionalDetails, Long> {
    @Query("SELECT t FROM TransactionalDetails t WHERE t.orderId = ?1")
    Optional<TransactionalDetails> findByOrderId(Long orderId);
}
