package com.example.JpaVueShop_backend.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.JpaVueShop_backend.domain.user.QUser.user;

@Repository
public class AdminUserRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public AdminUserRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 총 유저 수 카운트
     * @return
     */
    public Long userCount() {
        return jpaQueryFactory
                .select(user)
                .from(user)
                .fetchCount();
    }

}
