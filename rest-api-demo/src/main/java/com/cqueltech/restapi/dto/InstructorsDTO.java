package com.cqueltech.restapi.dto;

/*
 * A DTO query projection class to store the result set of a JPQL query. The fields
 * contained in this class map to the fields projected by the query.
 */

public class InstructorsDTO {

  /*
   * Declare class fields
   */
  
  private int instructorId;
  private String firstName;
  private String lastName;
  private String email;
  private int age;
  private String sex;
  private String address;

  /*
   * Define the class constructor
   */

  public InstructorsDTO(int instructorId,
                        String firstName,
                        String lastName,
                        String email,
                        int age,
                        String sex,
                        String address) {
    this.instructorId = instructorId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
    this.sex = sex;
    this.address = address;
  }

  /*
   * Define the class getter and setter methods
   */

  public int getInstructorId() {
    return instructorId;
  }

  public void setInstructorId(int instructorId) {
    this.instructorId = instructorId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
