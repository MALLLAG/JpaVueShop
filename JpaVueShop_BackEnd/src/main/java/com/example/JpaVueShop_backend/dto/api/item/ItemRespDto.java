package com.example.JpaVueShop_backend.dto.api.item;

import com.example.JpaVueShop_backend.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemRespDto {
    private Long itemId;
    private int price;
    private String name;
    private String category;
    private LocalDateTime regDate;

    public ItemRespDto(Item item) {
        this.itemId = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.category = item.getCategory().getName();
        this.regDate = item.getRegDate();
    }
}
