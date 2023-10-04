package com.cqueltech.restapi.service;

/*
 * A service to generate a JWT token for an authenticated user. Used for
 * web service authentication.
 * 
 * Returns a JWT token string that can be used by the client for further
 * http requests to the web service.
 */

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

  // Inject the JWT encoder.
  @Autowired
  private JwtEncoder jwtEncoder;

  // Method to generate our JWT token.
  public String generateJwtToken(Authentication auth) {
    // Object auth from Spring Security will have all roles/authorities associated to the user

    // Get the current time.
    Instant now = Instant.now();
    // Set the token expiry time.
    Instant expires = now.plus(1, ChronoUnit.HOURS);

    // Get all the roles/authorities associated with the user. auth has all the roles and since we
    // implemented the GrantedAuthority interface to the role entity we can extract the roles from
    // auth using the getAuthority() method and put them into a space delimited string. The roles
    // will be included in the JWT token.
    String scope = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));

    // Construct the information that the JWT token will actually hold.
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(expires)
        .subject(auth.getName()) // name of person that the token is going to, in this case username.
        .claim("roles", scope)
        .build();

    // Use JWT Encoder to encode a new JWT token for the claims.
    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
  
}
