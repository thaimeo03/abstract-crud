package com.tht.abstract_crud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.querydsl.core.ResultTransformer;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tht.abstract_crud.enums.FilterOperator;
import com.tht.abstract_crud.model.base.FilterCondition;
import com.tht.abstract_crud.model.order.QOrder;
import com.tht.abstract_crud.model.order.response.OrderInfo;
import com.tht.abstract_crud.model.user.QUser;
import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.model.user.request.UserFilter;
import com.tht.abstract_crud.model.user.response.UserOrders;
import com.tht.abstract_crud.service.UserDslService;
import com.tht.abstract_crud.service.base.BaseQueryDslListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDslServiceImpl extends BaseQueryDslListService<User, UserFilter, UserOrders>
    implements UserDslService {
  private final JPAQueryFactory queryFactory;

  @Override
  protected void applyJoin(JPAQuery<?> query) {
    QUser user = QUser.user;
    QOrder order = QOrder.order;

    query.distinct()
        .leftJoin(order).on(order.user.eq(user));
  }

  @Override
  protected List<FilterCondition> buildFilterConditions(UserFilter filter) {
    List<FilterCondition> conditions = new ArrayList<>();

    if (filter == null)
      return conditions;

    if (filter.getKeyword().length() > 0) {
      conditions.add(new FilterCondition("fullName", FilterOperator.LIKE, filter.getKeyword(), null));
    }

    if (filter.getFromDate() != null) {
      conditions.add(new FilterCondition("createdAt", FilterOperator.GTE, filter.getFromDate().atStartOfDay(), null));
      return conditions;
    }

    return conditions;
  }

  @Override
  protected Expression<UserOrders> buildSelect() {
    QUser user = QUser.user;
    QOrder order = QOrder.order;
    return Projections.constructor(UserOrders.class,
        user.id,
        user.fullName,
        user.address,
        Projections.list(
            Projections.constructor(OrderInfo.class,
                order.id,
                order.name,
                order.price,
                order.description)));
  }

  @Override
  protected EntityPathBase<User> getRoot() {
    return QUser.user;
  }

  @Override
  protected SimpleExpression<?> getIdExpression() {
    return QUser.user.id;
  }

  @Override
  protected ResultTransformer<List<UserOrders>> getGroupByTransformer() {
    QUser user = QUser.user;
    QOrder order = QOrder.order;
    return GroupBy.groupBy(user.id).list(
        Projections.constructor(UserOrders.class,
            user.id,
            user.fullName,
            user.address,
            GroupBy.list(Projections.constructor(OrderInfo.class,
                order.id,
                order.name,
                order.price,
                order.description))));
  }

  @Override
  protected JPAQueryFactory getQueryFactory() {
    return queryFactory;
  }
}
