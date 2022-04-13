package com.example.JpaVueShop_backend.domain.coupon;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminCouponRepo extends JpaRepository<Coupon, Long> {
    Coupon findByCouponNumber(String couponNumber);
    List<Coupon> findAllByOrderByIdDesc(Pageable pageable);
}
