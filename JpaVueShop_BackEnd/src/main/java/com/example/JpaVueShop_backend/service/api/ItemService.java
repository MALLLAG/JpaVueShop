package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemRepoSup;
import com.example.JpaVueShop_backend.dto.PageDto;
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

    /**
     * 아이템 리스트 가져오기
     * @return
     */
    public Map<String, Object> getItemList(PageDto pageDto) {
        Long totalCount = itemRepoSup.countItem();
        double size = 10.0;

        List<Item> itemList = itemRepoSup.getItemList(pageDto, size);
        List<ItemRespDto> itemRespDtoList = new ArrayList<>();
        Map<String, Object> customPageData = new HashMap<>();

        for (Item item : itemList) {
            ItemRespDto itemRespDto = new ItemRespDto(item);
            itemRespDtoList.add(itemRespDto);
        }

        customPageData.put("content", itemRespDtoList);
        customPageData.put("totalElements", totalCount);
        customPageData.put("size", size);
        customPageData.put("pageNumber", pageDto.getCurrentPage());

        return customPageData;
    }
}
