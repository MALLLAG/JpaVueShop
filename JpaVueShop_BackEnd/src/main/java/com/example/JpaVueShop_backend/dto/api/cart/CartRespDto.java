package com.example.JpaVueShop_backend.dto.api.cart;

import com.example.JpaVueShop_backend.domain.cartItem.CartItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRespDto {
    private Long cartItemId;
    private int price;
    private String title;

    public CartRespDto(CartItem cartItem) {
        this.cartItemId = cartItem.getId();
        this.price = cartItem.getItem().getPrice();
        this.title = cartItem.getItem().getName();
    }
}
