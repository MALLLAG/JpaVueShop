package com.example.JpaVueShop_backend.domain.item;

import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.dto.admin.item.RegisterItemDto;
import com.example.JpaVueShop_backend.dto.api.item.CreateItemDto;
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

    @ColumnDefault("0")
    private int discountRate; // 할인율

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }

    /**
     * 상품 생성
     * @param registerItemDto
     * @return
     */
    public static Item createItem(RegisterItemDto registerItemDto) {
        Item item = new Item();
        item.setName(registerItemDto.getName());
        item.setPrice(registerItemDto.getPrice());
        item.setDiscountRate(registerItemDto.getDiscountRate());

        return item;
    }
}
