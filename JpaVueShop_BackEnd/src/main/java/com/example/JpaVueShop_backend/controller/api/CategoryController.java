package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.service.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 가져오기
     * @return
     */
    @GetMapping("/getCategoryList")
    public CMRespDto<?> getCategoryList() {
        return new CMRespDto<>(1, "카테고리 리스트 가져오기 완료", categoryService.getCategoryList());
    }
}
