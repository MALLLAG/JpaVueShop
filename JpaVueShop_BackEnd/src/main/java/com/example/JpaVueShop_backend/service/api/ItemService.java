package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.domain.category.CategoryRepo;
import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemEsRepo;
import com.example.JpaVueShop_backend.domain.item.ItemRepoSup;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepoSup itemRepoSup;
    private final CategoryRepo categoryRepo;


    private final ItemEsRepo itemEsRepo;

    public Iterable<Item> esGetItemList() {
        Iterable<Item> itemList = itemEsRepo.findAll();

        for (Item item : itemList) {
            System.out.println(item.getName());
        }
        return itemList;
    }

    /**
     * 아이템 리스트 가져오기
     * @param itemPageDto
     * @return
     */
    public Map<String, Object> getItemList(ItemPageDto itemPageDto) {
        String categoryName = itemPageDto.getCategory();
        Category category = categoryRepo.findByName(categoryName);


        double size = 10.0;
        Long totalCount = itemRepoSup.countItem(itemPageDto, category, size);
        List<Item> itemList = itemRepoSup.getItemList(itemPageDto, category, size);
        List<ItemRespDto> itemRespDtoList = new ArrayList<>();
        Map<String, Object> customPageData = new HashMap<>();

        for (Item item : itemList) {
            ItemRespDto itemRespDto = new ItemRespDto(item);
            itemRespDtoList.add(itemRespDto);
        }

        customPageData.put("content", itemRespDtoList);
        customPageData.put("totalElements", totalCount);
        customPageData.put("size", size);
        customPageData.put("pageNumber", itemPageDto.getCurrentPage());

        return customPageData;
    }
}
