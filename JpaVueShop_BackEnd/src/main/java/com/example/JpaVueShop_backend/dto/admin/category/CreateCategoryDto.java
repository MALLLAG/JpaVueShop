package com.example.JpaVueShop_backend.dto.admin.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CreateCategoryDto {

    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String name;
}
