package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.PageDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.example.JpaVueShop_backend.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    /**
     * 아이템 리스트 가져오기
     * @param itemPageDto
     * @return
     */
    @PostMapping("/getItemList")
    public CMRespDto<?> getItemList(@RequestBody ItemPageDto itemPageDto) {
        return new CMRespDto<>(1, "아이템 리스트 가져오기 완료", itemService.getItemList(itemPageDto));
    }
}
