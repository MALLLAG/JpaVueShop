package com.example.JpaVueShop_backend.domain.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminOrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByIdDesc(Pageable pageable);
}
