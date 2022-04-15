package com.example.JpaVueShop_backend.dto.api.myPage;

import com.example.JpaVueShop_backend.domain.userCoupon.UserCoupon;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserCouponDto {
    private Long userCouponId;
    private String couponName;
    private int amount;

    public UserCouponDto(UserCoupon userCoupon) {
        this.userCouponId = userCoupon.getId();
        this.couponName = userCoupon.getCoupon().getCouponName();
        this.amount = userCoupon.getCoupon().getAmount();
    }
}
