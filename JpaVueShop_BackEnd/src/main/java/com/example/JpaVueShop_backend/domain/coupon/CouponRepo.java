package com.example.JpaVueShop_backend.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepo extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponNumber(String couponNumber);
}
