package com.cqueltech.restapi.dto;

/*
 * A generic model class used to hold an object that is not determined until runtime.
 * A model class permits a bi-directional flow of data between the controller and the template.
 */

public class GenericType<T> {

  private T entity;

  public GenericType(T entity) {
    this.entity = entity;
  }

  public void setEntity(T entity) {
    this.entity = entity;
  }

  public T getEntity() {
    return entity;
  }
  
}
