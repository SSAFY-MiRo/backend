package com.ssafy.miro.attraction.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.QAttraction;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.expression.spel.ast.Projection;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AttractionRepositoryCustomImpl implements AttractionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AttractionListItem> findAttractions(AttractionSearchFilter filter, Pageable pageable) {
        QAttraction attraction = QAttraction.attraction;

        // 필터 조건을 처리
        BooleanBuilder builder = buildConditions(filter, attraction);

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable);

        // QueryDSL 쿼리 실행
        List<AttractionListItem> content = queryFactory
                .select(Projections.constructor(AttractionListItem.class,
                        attraction.no,
                        attraction.title,
                        attraction.contentType.name,
                        attraction.view,
                        attraction.firstImage1,
                        attraction.addr1,
                        attraction.homepage,
                        attraction.overview,
                        attraction.longitude,
                        attraction.latitude
                ))
                .from(attraction)
                .where(builder)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 카운트 쿼리
        Long count = queryFactory
                .select(attraction.count())
                .from(attraction)
                .where(builder)
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> count==null?0:count);
    }

    private BooleanBuilder buildConditions(AttractionSearchFilter filter, QAttraction attraction) {
        BooleanBuilder builder = new BooleanBuilder();

        // 동적 조건 추가
        if (filter.sido() != null) {
            builder.and(attraction.sido.code.eq(filter.sido()));
        }
        if (filter.guguns() != null) {
            builder.and(attraction.guGunCode.gugunCode.in(filter.guguns()));
        }
        if (filter.attractionType() != null) {
            builder.and(attraction.contentType.id.in(filter.attractionType()));
        }
        if (filter.keyword() != null) {
            builder.and(attraction.title.contains(filter.keyword()));
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

            // QueryDSL의 PathBuilder를 사용하여 동적으로 필드 생성
            Expression expression=Expressions.path(QAttraction.class, QAttraction.attraction,property);
            orderSpecifiers.add(new OrderSpecifier<>(queryDslOrder, expression));
        }

        return orderSpecifiers;
    }
}
