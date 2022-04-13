package com.example.JpaVueShop_backend.dto.admin.coupon;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PublishCouponDto {
    private String couponName;
    private String couponNumber;
    private int amount;
    private java.util.Date startDate;
    private java.util.Date endDate;
}
