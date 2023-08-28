package com.cqueltech.restapi.dto;

/*
 * Abstract class used to wrap DTO classes when added to the Spring model.
 */

public abstract class EntityDTO {

  private String entityType;

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

}
