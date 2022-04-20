package com.example.JpaVueShop_backend.service.admin;

import com.example.JpaVueShop_backend.domain.category.AdminCategoryRepo;
import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.dto.admin.category.CategoryRespDto;
import com.example.JpaVueShop_backend.dto.admin.category.CreateCategoryDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final AdminCategoryRepo adminCategoryRepo;

    /**
     * 카테고리 생성
     * @param createCategoryDto
     * @return
     */
    @Transactional
    public Long createCategory(CreateCategoryDto createCategoryDto) {
        String name = createCategoryDto.getName();
        if (adminCategoryRepo.existsByName(name)) {
            throw new CustomApiException("이미 존재하는 카테고리입니다.");
        }

        Category category = Category.createCategory(name);
        adminCategoryRepo.save(category);

        return category.getId();
    }

    /**
     * 카테고리 목록 가져오기
     * @return
     */
    @Transactional(readOnly = true)
    public List<CategoryRespDto> getCategoryList() {
        List<Category> categoryList = adminCategoryRepo.findAll();
        List<CategoryRespDto> categoryRespDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryRespDto categoryRespDto = new CategoryRespDto(category);
            categoryRespDtoList.add(categoryRespDto);
        }

        return categoryRespDtoList;
    }
}
