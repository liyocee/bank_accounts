package com.liyosi.core.models;

public class Currency extends BaseModel {

  private String isoCode;

  public Currency(Long id, String isoCode) {
    super(id);
    this.isoCode = isoCode;
  }

  public String getIsoCode() {
    return isoCode;
  }
}
