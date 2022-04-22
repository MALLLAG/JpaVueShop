package com.example.JpaVueShop_backend.dto.admin.category;

import com.example.JpaVueShop_backend.domain.category.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CategoryRespDto {
    private Long categoryId;
    private String name;
    private LocalDateTime regDate;

    public CategoryRespDto(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.regDate = category.getRegDate();
    }
}
