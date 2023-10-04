package com.cqueltech.restapi.service;

/*
 * A service implemented by the Authentication Manager to determine if the user's
 * username and password passed via the http request exists in
 * the database. Implements UserDetailsService to specifically tell Spring
 * Security how to look for our user during authentication.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cqueltech.restapi.entity.User;
import com.cqueltech.restapi.repository.JwtUserRepository;

@Service
public class JwtUserService implements UserDetailsService {

  @Autowired
  private JwtUserRepository jwtUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    // Find the user by username from the JwtUserRepsoitory, if nothing found throw exception.
    User user = jwtUserRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not valid");
    }

    return user;
  }
  
}
