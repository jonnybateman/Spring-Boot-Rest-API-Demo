package com.cqueltech.restapi.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  // Add support for JDBC...no more hard coded users. Tells Spring Boot that we are
  // now using a database for user authentication using the details in the
  // application.properties file.
  // Passwords are BCRYPTed in database, user passwords are 'fun123'
  @Bean
  public UserDetailsManager userDetailsManager(DataSource dataSource) {

    // Here we set up configuration for a custom schema for user authentication.
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

    // Define query to retrieve a user by username. Question mark '?' placeholder will be assigned
    // the username from login by Spring automatically.
    jdbcUserDetailsManager.setUsersByUsernameQuery(
        "select username, password, active from users where username=?");

    // Define query to retrieve the authorities/roles by username.
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
        "select username, role from roles where username=?");

    return jdbcUserDetailsManager;
  }
  
  // Configure security of web paths in application for login, logout, etc.
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(configurer -> configurer
            // Configure the CSS directory to be accesible to all without authentication.
            // Ensures styles.css is accessible even when user has not bee authenticated.
            .requestMatchers("/css/**").permitAll()
            // Allow anyone to access the register new user page.
            .requestMatchers("/showFormAddUser").permitAll()
            .requestMatchers("/authenticateNewUser").permitAll()
            // Allow users with the STUDENT role to access the app's root page after authentication.
            .requestMatchers("/").hasAnyRole("STUDENT", "INSTRUCTOR", "ADMIN")
            // Allow only users with the STUDENT role to access the /courses page.
            .requestMatchers("/displayCourses").hasRole("STUDENT")
            // Allow only users with the STUDENT role to access the /reviews page.
            .requestMatchers("/reviews/**").hasRole("STUDENT")
            // Allow only users with the INSTRUCTOR role to access the /instructor page.
            .requestMatchers("/instructors/**").hasRole("INSTRUCTOR")
            // Allow only users with the INSTRUCTOR role to access the /students page.
            .requestMatchers("/students/**").hasRole("INSTRUCTOR")
            // Allow only users with the ADMIN role to access the /create-entity page.
            .requestMatchers("/create-entity/**").hasRole("ADMIN")
            // Any request to the app must be authenticated
            .anyRequest().authenticated())  
        // We are customizing the form login process
        .formLogin(form -> form
            // Show the custom form at this mapping. See controller for this mapping.
            .loginPage("/showMyLoginPage")
            // Login form should post data to this URL for processing (check user id and password)
            // No mapping required for this Spring will handle this behind the scenes
            .loginProcessingUrl("/authenticateTheUser")
            // Allow everyone one to see the login page
            .permitAll())
        // Add logout support for default URL (/logout).
        .logout(logout -> logout.permitAll())
        // Setup exception handling for access denied based on role of user.
        .exceptionHandling(configurer -> configurer
            .accessDeniedPage("/access-denied"));

    return http.build();
  }

  // Define the BCryptPasswordEncoder as a bean for our application. Will be used to
  // encrypt passwords when registering new users.
  @Bean
  public PasswordEncoder encoder() {

    return new BCryptPasswordEncoder();
  }
}
