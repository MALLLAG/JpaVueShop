package com.example.JpaVueShop_backend.domain.category;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Date)
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
