package com.tht.abstract_crud.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tht.abstract_crud.model.base.FilterCondition;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class GenericSpecificationBuilder<T> {
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Specification<T> build(List<FilterCondition> conditions) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      for (FilterCondition c : conditions) {
        if (c.getValue() == null)
          continue;

        Path<?> path = root.get(c.getField());

        switch (c.getOperator()) {
          case EQ:
            predicates.add(cb.equal(path, c.getValue()));
            break;

          case NEQ:
            predicates.add(cb.notEqual(path, c.getValue()));
            break;

          case LIKE:
            predicates.add(cb.like(path.as(String.class), "%" + c.getValue() + "%"));
            break;

          case LT:
            predicates.add(cb.lessThan(path.as(Comparable.class), (Comparable) c.getValue()));
            break;

          case LTE:
            predicates.add(cb.lessThanOrEqualTo(path.as(Comparable.class), (Comparable) c.getValue()));
            break;

          case GT:
            predicates.add(cb.greaterThan(path.as(Comparable.class), (Comparable) c.getValue()));
            break;

          case GTE:
            predicates.add(cb.greaterThanOrEqualTo(path.as(Comparable.class), (Comparable) c.getValue()));
            break;

          case IN:
            predicates.add(path.in((List<?>) c.getValue()));
            break;

          case NIN:
            predicates.add(cb.not(path.in((List<?>) c.getValue())));
            break;

          case BETWEEN:
            predicates.add(cb.between(
                path.as(Comparable.class),
                (Comparable) c.getValue(),
                (Comparable) c.getValueTo()));
            break;
        }

      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
