package com.example.JpaVueShop_backend.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.JpaVueShop_backend.domain.user.QUser.user;

@Repository
public class UserRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public UserRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * refreshToken DB에 저장
     * @param userId
     * @param refreshToken
     */
    public void setRefreshToken(Long userId, String refreshToken) {
        jpaQueryFactory
                .update(user)
                .set(user.refreshToken, refreshToken)
                .where(user.id.eq(userId))
                .execute();
    }

}
