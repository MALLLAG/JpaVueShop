package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.example.JpaVueShop_backend.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/getEsList")
    public CMRespDto<?> getEsList() {
        return new CMRespDto<>(1, "엘라스틱서치 리스트 가져오기 완료", itemService.getEsList());
    }

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
