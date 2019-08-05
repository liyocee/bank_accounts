package com.liyosi.core.models;

import javax.validation.constraints.NotNull;

public class Customer extends BaseModel {

  @NotNull
  private String name;

  public Customer(Long id, String name) {
    super(id);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
