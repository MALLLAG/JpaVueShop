package com.example.JpaVueShop_backend.dto.admin.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class CreateItemDto {

    @NotBlank
    private String category;

    @NotBlank
    private String name;

    @Positive
    private int price;

    @Min(0)
    @Max(100)
    private int discountRate;
}
