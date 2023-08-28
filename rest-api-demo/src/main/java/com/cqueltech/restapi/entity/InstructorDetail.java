package com.cqueltech.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Annotate the class as an entity and map to db table.
@Entity
@Table(name="instructor_detail")
public class InstructorDetail {

  // Annotate the fields with the db column names
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="age")
  private int age;

  @Column(name="sex")
  private String sex;

  @Column(name="address")
  private String address;

  // Define constructors
  public InstructorDetail() {
  }

  public InstructorDetail(Integer age, String sex, String address) {
    this.age = age;
    this.sex = sex;
    this.address = address;
  }

  // Define getters and setters.
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
