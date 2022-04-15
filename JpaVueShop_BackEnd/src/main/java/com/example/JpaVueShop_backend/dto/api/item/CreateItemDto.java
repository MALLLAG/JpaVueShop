package com.example.JpaVueShop_backend.dto.api.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateItemDto {
    private int price;
    private String name;
}
