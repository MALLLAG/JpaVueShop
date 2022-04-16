package com.example.JpaVueShop_backend.domain.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
