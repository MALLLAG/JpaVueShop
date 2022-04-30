package com.example.JpaVueShop_backend.domain.item;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.dto.admin.item.CreateItemDto;
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
@Table(name = "item")
//@Document(indexName = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
//    @Field(type = FieldType.Long)
    private Long id;
//    @Field(type = FieldType.Integer)
    private int price;
//    @Field(type = FieldType.Text)
    private String name;
//    @Field(type = FieldType.Date)
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
//    @Field(type = FieldType.Object)
    private Category category;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }

    /**
     * 상품 생성
     * @param createItemDto
     * @return
     */
    public static Item createItem(CreateItemDto createItemDto, Category category) {
        Item item = new Item();
        item.setCategory(category);
        item.setName(createItemDto.getName());
        item.setPrice(createItemDto.getPrice());

        return item;
    }

    public static Item createEsItem(CreateItemDto createItemDto) {
        Item item = new Item();
        item.setName("김치찌개");
        item.setPrice(8000);

        return item;
    }

}
