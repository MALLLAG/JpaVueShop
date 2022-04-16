package com.example.JpaVueShop_backend.domain.userCoupon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepo extends JpaRepository<UserCoupon, Long> {
    UserCoupon findByCouponId(Long couponId);
}
