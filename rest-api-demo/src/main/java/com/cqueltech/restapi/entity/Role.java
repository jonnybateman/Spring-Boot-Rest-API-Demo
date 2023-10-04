package com.cqueltech.restapi.entity;

/*
 * Entity class to define the fields and joins of the Role table.
 */

import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

  /*
   * Define the entity's fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  
  @Column(name = "username")
  private String username;

  @Column(name = "role")
  private String role;

  /*
   * Define class constructors
   */

  public Role(String username, String role) {
    this.username = username;
    this.role = role;
  }

  public Role() {
  }

  /*
   * Define class getter and setter methods
   */

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  /*
   * Override the GrantedAuthority interface method getAuthority(). Used to extract
   * the role from a Role object that is contained in a Spring Security Authentication
   * object. For Oauth2 JWT web service authentication.
   */
  @Override
  public String getAuthority() {
    return getRole();
  }
  
}
