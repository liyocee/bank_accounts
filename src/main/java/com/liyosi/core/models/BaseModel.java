package com.liyosi.core.models;

import javax.validation.constraints.NotNull;

abstract public class BaseModel {
  @NotNull
  private Long id;

  public BaseModel(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
