package com.cqueltech.restapi.dto;

/*
 * A custom response class used to send infomation back to the Rest Client.
 * Attributes:
 *    Status    : The status code of the HTTP request.
 *    Message   : The status response message indicating success, failure or error.
 *    Timestamp : format - dd-mm-yyyy hh24:mi:ss
 *    Array     : attribute for holding an array of data objects to be returned to the
 *                client.
 */

public class ServiceResponseDTO<T> {

  /*
   * Declare class fields
   */
  
  private int status;
  private String message;
  private String timestamp;
  private T array;

  /*
   * Declare constructors
   */

  public ServiceResponseDTO() {
  }

  public ServiceResponseDTO(int status, String message, String timestamp, T array) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
    this.array = array;
  }

  /*
   * Declare getter and setter methods
   */

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

  public T getArray() {
    return array;
  }

  public void setObject(T array) {
    this.array = array;
  }

}
