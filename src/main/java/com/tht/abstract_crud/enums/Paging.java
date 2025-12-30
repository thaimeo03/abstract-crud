package com.tht.abstract_crud.enums;

public enum Paging {
  YES("Y"),
  NO("N");

  private final String code;

  Paging(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}