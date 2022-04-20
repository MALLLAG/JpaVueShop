package com.example.JpaVueShop_backend.domain.category;

import com.example.JpaVueShop_backend.dto.admin.category.CreateCategoryDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long id;
    private String name;

    private LocalDateTime regDate;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }

    /**
     * 카테고리 생성
     * @param name
     * @return
     */
    public static Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        return category;
    }
}
