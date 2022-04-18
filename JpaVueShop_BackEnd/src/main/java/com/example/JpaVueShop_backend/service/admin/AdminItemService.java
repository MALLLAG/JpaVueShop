package com.example.JpaVueShop_backend.service.admin;

import com.example.JpaVueShop_backend.domain.item.AdminItemRepo;
import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.dto.admin.item.RegisterItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminItemService {

    private final AdminItemRepo adminItemRepo;

    /**
     * 상품 등록
     * @param registerItemDto
     * @return
     */
    public Long registerItem(RegisterItemDto registerItemDto) {
        Item item = Item.createItem(registerItemDto);

        Item registeredItem = adminItemRepo.save(item);
        return registeredItem.getId();
    }
}
