package com.cqueltech.restapi.security;

/*
 * Spring Security Configuration is responsible for all the security, including protecting
 * the application/service URLs, validating submitted username and passwords, redirecting
 * to the login form, and so on.
 */

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.cqueltech.restapi.exceptionhandling.WebServiceGlobalExceptionHandler;
import com.cqueltech.restapi.service.JwtUserService;
import com.cqueltech.restapi.utils.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig<T> {

  /*
   * JWT configuration with beans to encode and decode JWT tokens.
   */

  private final RsaKeyProperties keys;

  public SecurityConfig(RsaKeyProperties keys) {
    // Autowire the 'keys' variable into a constructor which will automatically inject the
    // RSA Key value pair.
    this.keys = keys;
  }

  // Inject the web service global exception handler to handle role based resource access
  // failures. It implements AccessDeniedHandler to handle these situations.
  @Autowired
  WebServiceGlobalExceptionHandler<T> webServiceGlobalExceptionHandler;

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  /*
   * Add support for JDBC...no more hard coded users. Tells Spring Boot that we are
   * using a database for user authentication using the details in the
   * application.properties file. Passwords are BCRYPTed in database, user passwords
   * are 'fun123'
   */
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

  /*
   * An authentication manager to define how we are authorizing the users. For use
   * in Oauth2 JWT web service authentication.
   */
  @Bean
  public AuthenticationManager authManager(JwtUserService jwtUserDetailsService) {

    // Setup a DaoAuthenticationProvider to specify how the manager will extract user
    // data from the database to authenticate against.
    DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
    daoProvider.setUserDetailsService(jwtUserDetailsService);

    return new ProviderManager(daoProvider);
  }

  /*
   * Roles are stored in the database as 'role' and not 'ROLE_role'. Therefore, we need
   * a JwtGrantedAuthoriesConverter to apply the prefix 'ROLE_' to the beginning of each
   * granted authority. Spring Security convention uses 'ROLE_' as a prefix for roles/
   * authorities. The JwtAuthenticationConverter is utilised by the Oauth2ResourceServer
   * when authenticating a JWT token from a HTTP request.
   */
  @Bean
  public JwtAuthenticationConverter jwtAuthConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    // Apply the granted authorities as set in the token service. There the claim was set
    // as 'roles'.
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    // Set the prefix for our roles as set out by the Spring Security convention.
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    // Take the role names, append the prefix and return the new roles/authorities.
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  /*
   * Configure security of http requests for web service APIs. Any URL
   * which contains the /api path will be served by this SecurityFilterChain.
   * e.g. http://localhost:8080/api/
   */
  @Bean
  @Order(1)
  public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {

    // The Oauth2ResourceServer creates a BearerTokenAuthenticationFilter that intercepts
    // requests, extracts any bearer tokens and attempts to authenticate. It will validate
    // the bearer token before serving a resource to the client.
    http
        .csrf(csrf -> csrf.disable())
        .securityMatcher("/api/**")
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/api/login").permitAll();
          auth.requestMatchers("/api/courses").hasRole("STUDENT");
          auth.requestMatchers("/api/course-reviews").hasRole("STUDENT");
          auth.requestMatchers("/api/instructors").hasRole("INSTRUCTOR");
          auth.requestMatchers("/api/students").hasRole("INSTRUCTOR");
          auth.requestMatchers("/api/enrol-student").hasRole("INSTRUCTOR");
          auth.requestMatchers("/api/create-course").hasRole("ADMIN");
          auth.requestMatchers("/api/create-student").hasRole("ADMIN");
          auth.requestMatchers("/api/create-instructor").hasRole("ADMIN");
          auth.anyRequest().authenticated();
        })
        .oauth2ResourceServer(oauth2 -> {
          oauth2.jwt(Customizer.withDefaults());
          oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter()));
          oauth2.accessDeniedHandler(webServiceGlobalExceptionHandler);
        }) 
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
  
  /*
   * Configure security of http requests for web application.
   */
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
            .requestMatchers("/home").hasAnyAuthority("STUDENT", "INSTRUCTOR", "ADMIN")
            // Allow only users with the STUDENT role to access the /courses page.
            .requestMatchers("/courses/**").hasAuthority("STUDENT")
            // Allow only users with the STUDENT role to access the /reviews page.
            .requestMatchers("/course-reviews/**").hasAuthority("STUDENT")
            // Allow only users with the INSTRUCTOR role to access the /instructor page.
            .requestMatchers("/instructors/**").hasAuthority("INSTRUCTOR")
            // Allow only users with the INSTRUCTOR role to access the /students page.
            .requestMatchers("/courses-students/**").hasAuthority("INSTRUCTOR")
            // Allow only users with the ADMIN role to access the /create-entity page.
            .requestMatchers("/create-entity/**").hasAuthority("ADMIN")
            // Any request to the app must be authenticated
            .anyRequest().authenticated())  
        // We are customizing the form login process
        .formLogin(form -> form
            // Show the custom form at this mapping. See controller for this mapping.
            .loginPage("/showMyLoginPage")
            // Login form should post data to this URL for processing (check user id and password)
            // No mapping required for this Spring will handle this behind the scenes
            .loginProcessingUrl("/authenticateTheUser")
            // Define the default URL on successful login.
            .defaultSuccessUrl("/home", true)
            // Allow everyone one to see the login page
            .permitAll())
        // Add logout support for default URL (/logout).
        .logout(logout -> logout.permitAll())
        // Setup exception handling for access denied based on role of user.
        .exceptionHandling(configurer -> configurer
            .accessDeniedPage("/access-denied"));

    return http.build();
  }

  /*
   * Define the PasswordEncoder as a bean for our application. Will be used to
   * encrypt/decrypt passwords when registering/authorising users.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    String idForEncode = "bcrypt";
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put(idForEncode, new BCryptPasswordEncoder());

    //return new BCryptPasswordEncoder();
    return new DelegatingPasswordEncoder(idForEncode, encoders);
  }
}
