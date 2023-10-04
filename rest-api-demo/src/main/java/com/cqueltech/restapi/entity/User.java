package com.cqueltech.restapi.entity;

/*
 * Entity class to define the fields and joins of the User table.
 */

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  /*
   * Define the class fields
   */

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "active")
  private int active;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "username", referencedColumnName = "username")
  private Set<Role> roles;
  
  /*
   * Define the class constructors
   */

  public User(String username, String password, int active) {
    this.username = username;
    this.password = password;
    this.active = active;
  }

  public User() {
  }

  /*
   * Define the class getter and setter methods
   */

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

  // Required by the UserDetails interface. Used for Oauth2 JWT web service authentication.
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  public void setAuthorities(Set<Role> authorities) {
    this.roles = authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
  
}
