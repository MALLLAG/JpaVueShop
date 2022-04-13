package com.example.JpaVueShop_backend.dto.admin.coupon;

import com.example.JpaVueShop_backend.domain.coupon.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CouponRespDto {
    private Long couponId;
    private String couponName;
    private String couponNumber;
    private int amount;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private LocalDateTime regDate;

    public CouponRespDto(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.couponNumber = coupon.getCouponNumber();
        this.amount = coupon.getAmount();
        this.startDate = coupon.getStartDate();
        this.endDate = coupon.getEndDate();
        this.regDate = coupon.getRegDate();
    }
}
