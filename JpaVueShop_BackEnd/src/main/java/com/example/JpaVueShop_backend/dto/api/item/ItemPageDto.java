package com.example.JpaVueShop_backend.dto.api.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemPageDto {
    private int currentPage;
    private String category;
    private String search;
}
