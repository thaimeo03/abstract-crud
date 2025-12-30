package com.tht.abstract_crud.enums;

public enum Ordering {
  ASC("ASC"),
  DESC("DESC");

  private final String code;

  Ordering(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}