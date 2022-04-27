package com.example.JpaVueShop_backend.service.admin;

import com.example.JpaVueShop_backend.domain.category.AdminCategoryRepo;
import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.domain.item.AdminItemRepo;
import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemEsRepo;
import com.example.JpaVueShop_backend.dto.admin.item.CreateItemDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminItemService {

    private final AdminItemRepo adminItemRepo;
    private final AdminCategoryRepo adminCategoryRepo;
    private final ItemEsRepo itemEsRepo;

    /**
     * 상품 생성
     * @param createItemDto
     * @return
     */
    public Long createItem(CreateItemDto createItemDto) {
        Category category = adminCategoryRepo.findByName(createItemDto.getCategory()).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 카테고리를 찾지 못했습니다.");
        });

        Item item = Item.createItem(createItemDto, category);
        Item esItem = Item.createEsItem(createItemDto);
        itemEsRepo.save(esItem);
        return adminItemRepo.save(item).getId();
    }
}
