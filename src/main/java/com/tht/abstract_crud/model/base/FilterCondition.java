package com.tht.abstract_crud.model.base;

import com.tht.abstract_crud.enums.FilterOperator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCondition {
  private String field;
  private FilterOperator operator;
  private Object value;
  private Object valueTo; // Used for BETWEEN operator
}
