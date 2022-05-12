package com.example.JpaVueShop_backend.controller.admin;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.admin.category.CreateCategoryDto;
import com.example.JpaVueShop_backend.service.admin.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    /**
     * 카테고리 생성
     * @param createCategoryDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/createCategory")
    public CMRespDto<?> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto,
                                         BindingResult bindingResult) {
        return new CMRespDto<>(1, "카테고리 생성 완료", adminCategoryService.createCategory(createCategoryDto));
    }

    /**
     * 카테고리 목록 가져오기
     * @return
     */
    @GetMapping("/getCategoryList")
    public CMRespDto<?> getCategoryList() {
        return new CMRespDto<>(1, "카테고리 리스트 가져오기 완료", adminCategoryService.getCategoryList());
    }

}
