package com.example.JpaVueShop_backend.domain.item;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.dto.admin.item.CreateItemDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private Long id;
    private int price;
    private String name;
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ColumnDefault("0")
    private int discountRate; // 할인율

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
        item.setDiscountRate(createItemDto.getDiscountRate());

        return item;
    }
}
