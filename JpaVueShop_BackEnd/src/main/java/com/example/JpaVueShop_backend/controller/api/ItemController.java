package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.example.JpaVueShop_backend.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/getItemList")
    public CMRespDto<?> getItemList(@RequestBody ItemPageDto itemPageDto) throws IOException {
        return new CMRespDto<>(1, "아이템 리스트 가져오기 완료", itemService.getItemList(itemPageDto));
    }

}
