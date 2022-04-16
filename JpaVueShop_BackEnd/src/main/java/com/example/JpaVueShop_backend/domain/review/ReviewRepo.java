package com.example.JpaVueShop_backend.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Review findByUserIdAndOrderIdAndItemId(Long userId, Long orderId, Long itemId);
}
