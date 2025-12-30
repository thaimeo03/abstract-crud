package com.tht.abstract_crud.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tht.abstract_crud.enums.Ordering;
import com.tht.abstract_crud.enums.Paging;
import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.base.FilterCondition;
import com.tht.abstract_crud.model.base.PageResponse;
import com.tht.abstract_crud.utils.GenericSpecificationBuilder;

public abstract class BaseListService<T, F> {
  protected abstract JpaSpecificationExecutor<T> getRepository();

  protected abstract List<FilterCondition> buildFilterConditions(F filter);

  public Object find(BaseListRequest<F> request) {
    List<FilterCondition> conditions = buildFilterConditions(request.getFilter());

    Specification<T> spec = new GenericSpecificationBuilder<T>().build(conditions);

    boolean isPaging = Paging.YES.getCode().equalsIgnoreCase(request.getIsPaging());

    if (isPaging) {
      Pageable pageable = buildPageable(request.getPage(), request.getSize(), request.getSort());

      Page<T> pageResult = getRepository().findAll(spec, pageable);

      return PageResponse.from(pageResult);
    }

    List<T> listResult = getRepository().findAll(spec, buildSort(request.getSort()));

    return listResult;
  }

  // Helpers
  protected Pageable buildPageable(Integer page, Integer size, String sort) {
    int p = page != null ? page : 0;
    int s = size != null ? size : 10;

    return PageRequest.of(p, s, buildSort(sort));
  }

  protected Sort buildSort(String sort) {
    if (sort == null || sort.isBlank())
      return Sort.unsorted();

    String[] orders = sort.split(";");
    List<Order> orderList = new ArrayList<>();

    for (String order : orders) {
      String[] parts = order.split(",");
      if (parts.length != 2)
        continue;

      String field = parts[0].trim();
      String directionStr = parts[1].trim().toUpperCase();

      Direction direction = directionStr.equals(Ordering.DESC.getCode()) ? Direction.DESC : Direction.ASC;

      orderList.add(new Order(direction, field));
    }

    return Sort.by(orderList);
  }
}
