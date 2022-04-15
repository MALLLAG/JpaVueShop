package com.example.JpaVueShop_backend.dto.api.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartOrderDto {
    private List<Long> cartItemId;
    private Long userCouponId;
    private int usedPoint;
}
