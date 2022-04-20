package com.example.JpaVueShop_backend.admin.category;

import com.example.JpaVueShop_backend.domain.category.AdminCategoryRepo;
import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.dto.admin.category.CategoryRespDto;
import com.example.JpaVueShop_backend.dto.admin.category.CreateCategoryDto;
import com.example.JpaVueShop_backend.dto.admin.coupon.PublishCouponDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import com.example.JpaVueShop_backend.service.admin.AdminCategoryService;
import com.example.JpaVueShop_backend.service.admin.AdminCouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AdminCategoryServiceTest {

    @Autowired
    private AdminCategoryService adminCategoryService;
    @Autowired
    private AdminCategoryRepo adminCategoryRepo;

    @Test
    @Transactional
    @DisplayName("중복 카테고리 생성 테스트")
    public void categoryDuplicateCheck() {
        CreateCategoryDto createCategoryDto1 = new CreateCategoryDto();
        createCategoryDto1.setName("카테고리");

        CreateCategoryDto createCategoryDto2 = new CreateCategoryDto();
        createCategoryDto2.setName("카테고리");

        adminCategoryService.createCategory(createCategoryDto1);

        CustomApiException e = assertThrows(CustomApiException.class, () -> adminCategoryService.createCategory(createCategoryDto2));
        assertEquals(e.getMessage(), "이미 존재하는 카테고리입니다.");
    }


    @Test
    @Transactional
    @DisplayName("카테고리 생성 테스트")
    public void createCategory() {
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName("카테고리");
        Long categoryId = adminCategoryService.createCategory(createCategoryDto);

        Optional<Category> category = adminCategoryRepo.findById(categoryId);

        assertEquals(categoryId, category.get().getId());
    }

}
