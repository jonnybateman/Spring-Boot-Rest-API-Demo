package com.cqueltech.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  // Define the fields
  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "active")
  private int active;
  
  // Define the constructors
  public User(String username, String password, int active) {
    this.username = username;
    this.password = password;
    this.active = active;
  }

  public User() {
  }

  // Getters and setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }
  
}
