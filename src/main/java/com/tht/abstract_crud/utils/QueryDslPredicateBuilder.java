package com.tht.abstract_crud.utils;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.tht.abstract_crud.model.base.FilterCondition;

public class QueryDslPredicateBuilder {
  @SuppressWarnings("rawtypes")
  public static <T> BooleanBuilder build(
      EntityPathBase<T> root, List<FilterCondition> conditions) {
    BooleanBuilder builder = new BooleanBuilder();

    for (FilterCondition c : conditions) {
      PathBuilder<?> path = new PathBuilder<>(root.getType(), root.getMetadata());

      switch (c.getOperator()) {
        case EQ:
          builder.and(path.get(c.getField()).eq(c.getValue()));
          break;

        case NEQ:
          builder.and(path.get(c.getField()).ne(c.getValue()));
          break;

        case LIKE:
          builder.and(path.getString(c.getField()).like("%" + c.getValue() + "%"));
          break;

        case LT:
          builder.and(path.getComparable(c.getField(), Comparable.class).lt((Comparable) c.getValue()));
          break;

        case LTE:
          builder.and(path.getComparable(c.getField(), Comparable.class).loe((Comparable) c.getValue()));
          break;

        case GT:
          builder.and(path.getComparable(c.getField(), Comparable.class).gt((Comparable) c.getValue()));
          break;

        case GTE:
          builder.and(path.getComparable(c.getField(), Comparable.class).goe((Comparable) c.getValue()));
          break;

        case IN:
          builder.and(path.get(c.getField()).in(c.getValue()));
          break;

        case NIN:
          builder.and(path.get(c.getField()).notIn(c.getValue()));
          break;

        case BETWEEN:
          builder.and(path.getComparable(c.getField(), Comparable.class)
              .between((Comparable) c.getValue(),
                  (Comparable) c.getValueTo()));
          break;
      }
    }

    return builder;
  }
}
