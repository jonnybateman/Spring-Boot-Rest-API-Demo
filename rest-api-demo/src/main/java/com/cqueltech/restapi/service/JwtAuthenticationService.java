package com.cqueltech.restapi.service;

/*
 * A service which uses the Authentication Manager to look for for a user that corresponds
 * to the username and password that was passed in the http request. If the authentication
 * is successful a JWT token will be generated and passed back to the client as part of the
 * http login response.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.cqueltech.restapi.dto.JwtLoginResponseDTO;
import com.cqueltech.restapi.repository.JwtUserRepository;

@Service
public class JwtAuthenticationService {

  @Autowired
  private JwtUserRepository jwtUserRepository;

  // Uses instance of AuthenticationManager that we set up in our SecurityConfiguration.
  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtTokenService tokenService;

  /*
   * Method which takes the username and password, authenticates the user using the
   * Authentication Manager then generates the JWT token. Returns a response to the
   * client using the JwtLoginResponseDTO class.
   */
  public JwtLoginResponseDTO loginUser(String username, String password) {

    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    String token = tokenService.generateJwtToken(auth);

    // Supplied user details authenticated, return login response and JWT token to client.
    return new JwtLoginResponseDTO(jwtUserRepository.findByUsername(username), token);
  }
  
}
