package com.tht.abstract_crud.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tht.abstract_crud.enums.Ordering;
import com.tht.abstract_crud.enums.Paging;
import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.base.FilterCondition;
import com.tht.abstract_crud.model.base.PageResponse;
import com.tht.abstract_crud.utils.QueryDslPredicateBuilder;

public abstract class BaseQueryDslListService<T, F, R> {
  protected abstract JPAQueryFactory getQueryFactory();

  protected abstract List<FilterCondition> buildFilterConditions(F filter);

  protected abstract Expression<R> buildSelect();

  protected abstract EntityPathBase<T> getRoot();

  protected abstract void applyJoin(JPAQuery<?> query);

  protected SimpleExpression<?> getIdExpression() {
    return null;
  }

  protected ResultTransformer<List<R>> getGroupByTransformer() {
    return null;
  }

  public Object find(BaseListRequest<F> request) {
    BooleanBuilder predicate = QueryDslPredicateBuilder.build(getRoot(),
        this.buildFilterConditions(request.getFilter()));

    JPAQuery<?> baseQuery = getQueryFactory()
        .from(getRoot())
        .where(predicate);

    applyJoin(baseQuery);

    // Handle GroupBy / ID-based Pagination
    if (getGroupByTransformer() != null) {
      return findWithGroupBy(baseQuery, request);
    }

    // Apply sort
    String sort = request.getSort();
    buildSort(baseQuery, sort);

    // Apply select
    JPAQuery<R> query = (JPAQuery<R>) baseQuery.clone().select(buildSelect());

    // Apply paging
    if (request.getIsPaging().equalsIgnoreCase(Paging.YES.getCode())) {
      long total = query.fetch().size();

      List<R> results = query.offset(request.getPage() * request.getSize())
          .limit(request.getSize())
          .fetch();

      Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
      return PageResponse.from(new PageImpl<>(results, pageable, total));
    }

    return query.fetch();
  }

  private Object findWithGroupBy(JPAQuery<?> query, BaseListRequest<F> request) {
    if (request.getIsPaging().equalsIgnoreCase(Paging.YES.getCode())) {
      SimpleExpression<?> idPath = getIdExpression();
      if (idPath == null) {
        throw new IllegalStateException("ID Expression must be provided for GroupBy pagination");
      }

      // Fetch Distinct IDs for the page
      long total = query.clone().select(idPath).distinct().fetch().size();

      // Apply sort
      buildSort(query, request.getSort());

      List<?> ids = query.clone()
          .select(idPath)
          .distinct()
          .offset(request.getPage() * request.getSize())
          .limit(request.getSize())
          .fetch();

      List<R> results = new ArrayList<>();
      if (!ids.isEmpty()) {
        // 2. Fetch Data for IDs
        results = query.clone()
            .where(((SimpleExpression) idPath).in(ids))
            .transform(getGroupByTransformer());
      }

      Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
      return PageResponse.from(new PageImpl<>(results, pageable, total));
    }

    // No Paging + GroupBy
    return query.transform(getGroupByTransformer());
  }

  private void buildSort(JPAQuery<?> query, String sort) {
    if (sort.isBlank())
      return;

    PathBuilder<?> pathBuilder = new PathBuilder<>(getRoot().getType(), getRoot().getMetadata());
    String[] orders = sort.split(";");
    for (String orderStr : orders) {

      String[] orderParts = orderStr.trim().split(",");
      String field = orderParts[0];
      String direction = orderParts.length > 1 ? orderParts[1] : Ordering.DESC.getCode();

      Order sortOrder = direction.equalsIgnoreCase(Ordering.DESC.getCode())
          ? Order.DESC
          : Order.ASC;

      ComparableExpressionBase<?> expression = pathBuilder.getComparable(field, Comparable.class);

      query.orderBy(new OrderSpecifier<>(sortOrder, expression));
    }
  }
}
