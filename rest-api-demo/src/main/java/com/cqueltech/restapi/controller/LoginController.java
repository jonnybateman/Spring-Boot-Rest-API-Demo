package com.cqueltech.restapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  // Controller to show the user login form.
  @GetMapping("/showMyLoginPage")
  public String showMyLoginPage() {

    // Dislplay the login html page. 'fancy-login' is the name of the login HTML file.
    return "fancy-login";
  }
  
  // Add request mapping for /access-denied.
  @GetMapping("/access-denied")
  public String showAccessDenied() {

    // Dislplay the access-denied html page.
    return "access-denied";
  }
}
