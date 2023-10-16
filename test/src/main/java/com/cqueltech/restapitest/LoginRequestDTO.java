package com.cqueltech.restapitest;

public class LoginRequestDTO {
  
  /*
   * Declare class fields
   */
  
   private String username;
   private String password;
 
   /*
    * Declare constructors
    */
 
   public LoginRequestDTO() {
     super();
   }
 
   public LoginRequestDTO(String username, String password) {
     this.username = username;
     this.password = password;
   }
 
   /*
    * Declare getter and setter methods
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
}
