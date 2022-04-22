package com.example.JpaVueShop_backend.domain.item;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.dto.PageDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.JpaVueShop_backend.domain.item.QItem.item;

@Repository
public class ItemRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public ItemRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(Item.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Long countItem(ItemPageDto itemPageDto, Category category, double size) {
        JPAQuery<Item> jpaQuery = jpaQueryFactory
                .select(item)
                .from(item);

        if (category != null) {
            jpaQuery.where(item.category.eq(category));
        }
        if (itemPageDto.getSearch() != null) {
            jpaQuery.where(item.name.containsIgnoreCase(itemPageDto.getSearch()));
        }

        return jpaQuery
                .offset(itemPageDto.getCurrentPage() * (int) size)
                .limit((int) size)
                .fetchCount();
    }

    public List<Item> getItemList(ItemPageDto itemPageDto, Category category, double size) {
        JPAQuery<Item> jpaQuery = jpaQueryFactory
                .select(item)
                .from(item);

        if (category != null) {
            jpaQuery.where(item.category.eq(category));
        }
        if (itemPageDto.getSearch() != null) {
            jpaQuery.where(item.name.containsIgnoreCase(itemPageDto.getSearch()));
        }

        return jpaQuery
                .offset(itemPageDto.getCurrentPage() * (int) size)
                .limit((int) size)
                .fetch();
    }

}
