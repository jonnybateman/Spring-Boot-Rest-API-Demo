package com.cqueltech.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

  // Define the fields
  @Id
  @Column(name = "username")
  private String username;

  @Id
  @Column(name = "role")
  private String role;

  // Define constructors
  public Role(String username, String role) {
    this.username = username;
    this.role = role;
  }

  public Role() {
  }

  // Getters and setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
  
  
}
