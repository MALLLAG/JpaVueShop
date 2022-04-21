package com.example.JpaVueShop_backend.dto.api.category;

import com.example.JpaVueShop_backend.domain.category.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRespDto {
    private String name;

    public CategoryRespDto(Category category) {
        this.name = category.getName();
    }
}
