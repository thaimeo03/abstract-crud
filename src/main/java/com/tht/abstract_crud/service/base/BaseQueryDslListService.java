package com.tht.abstract_crud.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tht.abstract_crud.enums.Ordering;
import com.tht.abstract_crud.enums.Paging;
import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.base.FilterCondition;
import com.tht.abstract_crud.utils.QueryDslPredicateBuilder;

public abstract class BaseQueryDslListService<T, F, R> {
  protected abstract JPAQueryFactory getQueryMethodFactory();

  protected abstract List<FilterCondition> buildFilterConditions(F filter);

  protected abstract Expression<R> buildSelect();

  protected abstract EntityPathBase<T> getRoot();

  protected abstract void applyJoin(JPAQuery<?> query);

  public Page<R> find(BaseListRequest<F> request) {
    BooleanBuilder predicate = QueryDslPredicateBuilder.build(getRoot(),
        this.buildFilterConditions(request.getFilter()));

    JPAQuery<R> query = getQueryMethodFactory()
        .from(getRoot())
        .where(predicate)
        .select(buildSelect());

    applyJoin(query);

    List<R> results = query.fetch();

    if (request.getIsPaging().equalsIgnoreCase(Paging.YES.getCode())) {
      query.offset(request.getPage() * request.getSize())
          .limit(request.getSize());
    }

    // Apply sort
    String sort = request.getSort();
    if (sort != null && sort.length() > 0) {
      PathBuilder<?> pathBuilder = new PathBuilder<>(getRoot().getType(), getRoot().getMetadata());
      String[] orders = sort.split(";");
      for (String orderStr : orders) {

        String[] orderParts = orderStr.trim().split(" ");
        String field = orderParts[0];
        String direction = orderParts.length > 1 ? orderParts[1] : Ordering.DESC.getCode();

        Order sortOrder = direction.equals(Ordering.DESC.getCode())
            ? Order.DESC
            : Order.ASC;

        ComparableExpressionBase<?> expression = pathBuilder.getComparable(field, Comparable.class);

        query.orderBy(new OrderSpecifier<>(sortOrder, expression));
      }
    }

    // Apply paging
    long total = results.size();
    Pageable pageable = buildPageable(request.getPage(), request.getSize());

    return new PageImpl<>(results, pageable, total);
  }

  // Helpers
  protected Pageable buildPageable(Integer page, Integer size) {
    int p = page != null ? page : 0;
    int s = size != null ? size : 10;

    return PageRequest.of(p, s);
  }
}
