package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.domain.category.CategoryRepo;
import com.example.JpaVueShop_backend.dto.api.category.CategoryRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    /**
     * 카테고리 목록 가져오기
     * @return
     */
    @Transactional(readOnly = true)
    public List<CategoryRespDto> getCategoryList() {
        List<Category> categoryList = categoryRepo.findAll();
        List<CategoryRespDto> categoryRespDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryRespDto categoryRespDto = new CategoryRespDto(category);
            categoryRespDtoList.add(categoryRespDto);
        }

        return categoryRespDtoList;
    }
}
