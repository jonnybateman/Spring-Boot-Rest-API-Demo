package com.cqueltech.restapitest;

import java.util.List;

public class ResponseDTO<T> {
  
  private int status;
  private String message;
  private String timestamp;
  private List<T> array;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public List<T> getArray() {
    return array;
  }

  public void setArray(List<T> array) {
    this.array = array;
  }
}
