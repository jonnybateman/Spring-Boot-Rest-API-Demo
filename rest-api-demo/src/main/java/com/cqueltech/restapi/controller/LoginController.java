package com.cqueltech.restapi.controller;

/*
 * Controllers are used for processing the web request (eg. form submition) and
 * rendering the response to the view.
 */

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.cqueltech.restapi.entity.User;
import com.cqueltech.restapi.modelclasses.NewUser;
import com.cqueltech.restapi.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

  private UserService userService;

  /*
   * Inject the UserService using constructor injection. The UserService acts as an
   * intermediary layer between the controller and the DAO that accesses the database.
   */
  public LoginController(UserService userService) {
    this.userService = userService;
  }

  /*
   * InitBinder used to customise the web request being sent to the controller. It helps
   * in controlling and formatting each and every request that comes to it.
   */
  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {

    // Register a StringTrimmerEditor object as a custom editor to the data binder with
    // String class as target source. InitBinder will now trim all the string values
    // coming as part of a request.
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
    webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  /*
   * Request mapping to show the user login form.
   */
  @GetMapping("/showMyLoginPage")
  public String showMyLoginPage() {

    // Dislplay the login html page. 'fancy-login' is the name of the login HTML file.
    return "fancy-login";
  }
  
  /*
   * Add request mapping for /access-denied. When user does not have valid authority/role
   * for the resource.
   */
  @GetMapping("/access-denied")
  public String showAccessDenied() {

    // Dislplay the access-denied html page.
    return "access-denied";
  }

  /*
   * Add request mapping for /showFormAddUser. Register a new user with required roles.
   */
  @GetMapping("/showFormAddUser")
  public String showFormAddUser(Model model) {

    // Add a model attribute. This is an object that will hold the form data for the data binding.
    NewUser newUser = new NewUser();
    model.addAttribute("newUser", newUser);

    // Display the add user page.
    return "add-user";
  }

  /*
   * Process new user form data to save user.
   * The Model Attribute is that setup as the th:object in the new user html form.
   */
  @PostMapping("/authenticateNewUser")
  public String saveUser(@Valid @ModelAttribute("newUser") NewUser newUser, BindingResult bindingResult, Model model) {

    // Form validation, check formatting of the supplied username and password fields.
    if (bindingResult.hasErrors()) {
      
      // Username and/or password entries not valid. Display error in registration form
      // by adding an attribute value to the applicable model attribute. 'modelUser' is
      // set up as a 'modelAttribute' in 'add-user.html' form.
      model.addAttribute("modelUser", new NewUser());
      model.addAttribute("registrationError", "Invalid username and/or password");
      return "/add-user";
    }
    // Check the new user does not already exist using the object 'newUser' from the
    // 'add-user.html' form.
    User user = userService.findUserByUsername(newUser.getUsername());
    if (user != null) {
      
      model.addAttribute("modelUser", new NewUser());
      model.addAttribute("registrationError", "Username already exists");
      return "/add-user";
    }

    // Ensure the passwords match.
    if (!newUser.getPassword1().equals(newUser.getPassword2())) {
      model.addAttribute("modelUser", new NewUser());
      model.addAttribute("registrationError", "Passwords do not match.");
      return "/add-user";
    }

    // Save the user.
    userService.save(newUser);

    // Use a redirect to prevent duplicate submissions and send user to login page.
    return "redirect:/fancy-login";
  }

}
