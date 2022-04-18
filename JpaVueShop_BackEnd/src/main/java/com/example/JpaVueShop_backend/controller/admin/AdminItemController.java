package com.example.JpaVueShop_backend.controller.admin;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.service.admin.AdminItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/item")
public class AdminItemController {

    private final AdminItemService adminItemService;

    public CMRespDto<?> registerItem() {
        return new CMRespDto<>(1, "아이템 등록 완료", adminItemService.registerItem());
    }
}
