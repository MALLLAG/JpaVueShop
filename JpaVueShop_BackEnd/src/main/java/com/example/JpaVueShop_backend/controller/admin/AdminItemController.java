package com.example.JpaVueShop_backend.controller.admin;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.admin.item.RegisterItemDto;
import com.example.JpaVueShop_backend.service.admin.AdminItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/item")
public class AdminItemController {

    private final AdminItemService adminItemService;

    /**
     * 상품 등록
     * @param registerItemDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/registerItem")
    public CMRespDto<?> registerItem(@Valid @RequestBody RegisterItemDto registerItemDto,
                                     BindingResult bindingResult) {
        return new CMRespDto<>(1, "아이템 등록 완료", adminItemService.registerItem(registerItemDto));
    }
}
