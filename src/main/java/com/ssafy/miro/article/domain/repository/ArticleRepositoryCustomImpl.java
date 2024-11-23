package com.ssafy.miro.article.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.article.domain.ArticleSearchType;
import com.ssafy.miro.article.domain.QArticle;
import com.ssafy.miro.article.domain.QArticleLike;
import com.ssafy.miro.attraction.domain.QAttraction;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ArticleItems> getArticleItems(ArticleCategory articleCategory, String search, ArticleSearchType articleSearchType, Pageable pageable) {
        QArticle article = QArticle.article;
        QArticleLike articleLike = QArticleLike.articleLike;
        BooleanBuilder builder = buildConditions(articleCategory, search, articleSearchType, article);
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable);

        // QueryDSL 쿼리 실행
        List<ArticleItems> content = queryFactory
                .select(Projections.constructor(ArticleItems.class, article.id, article.title, article.view, article.createdAt, articleLike.count().as("likeCount")))
                .from(article)
                .leftJoin(articleLike).on(articleLike.article.eq(article))
                .where(builder)
                .groupBy(article.id)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 카운트 쿼리
        Long count = queryFactory
                .select(article.count())
                .from(article)
                .where(builder)
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> count==null?0:count);
    }

    private BooleanBuilder buildConditions(ArticleCategory articleCategory, String search, ArticleSearchType articleSearchType, QArticle article) {
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건: 카테고리
        builder.and(article.category.eq(articleCategory));

        // 검색 조건
        if (search != null) {
            if (articleSearchType.equals(ArticleSearchType.TITLE)) {
                builder.and(article.title.contains(search));
            } else if (articleSearchType.equals(ArticleSearchType.ANY)) {
                // 제목이나 내용에 검색어가 포함된 경우
                builder.and(article.title.contains(search).or(article.content.contains(search)));
            } else {
                // 작성자 순
                builder.and(article.user.nickname.eq(search));
            }
        }

        return builder;
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        // Sort 객체에서 Order 정보를 추출
        for (Sort.Order order : pageable.getSort()) {
            // 정렬 방향
            Order queryDslOrder = order.isAscending() ? Order.ASC : Order.DESC;

            // 정렬 대상 필드
            String property = order.getProperty();
            if(property.equals("recent")){
                property="createdAt";
            } else if (property.equals("popular")) {
                property="likeCount";
            }

            // QueryDSL의 PathBuilder를 사용하여 동적으로 필드 생성
            Expression expression= Expressions.path(QArticle.class, QArticle.article,property);
            orderSpecifiers.add(new OrderSpecifier<>(queryDslOrder, expression));
        }

        return orderSpecifiers;
    }
}
