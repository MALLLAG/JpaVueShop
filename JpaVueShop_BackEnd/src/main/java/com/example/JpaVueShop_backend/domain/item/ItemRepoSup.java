package com.example.JpaVueShop_backend.domain.item;

import com.example.JpaVueShop_backend.dto.PageDto;
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

    public Long countItem() {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .fetchCount();
    }

    public List<Item> getItemList(PageDto pageDto, double size) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .offset(pageDto.getCurrentPage() * (int) size)
                .limit((int) size)
                .fetch();
    }
}
