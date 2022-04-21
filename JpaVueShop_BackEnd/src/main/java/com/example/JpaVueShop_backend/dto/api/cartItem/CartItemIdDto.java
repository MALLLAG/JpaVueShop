package com.example.JpaVueShop_backend.dto.api.cartItem;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CartItemIdDto {
    @NotNull
    private Long cartItemId;
}
