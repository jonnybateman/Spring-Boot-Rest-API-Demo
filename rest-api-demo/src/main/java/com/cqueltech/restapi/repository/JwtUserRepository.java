package com.cqueltech.restapi.repository;

/*
 * Repository interface since we are using Spring Data JPA. Repository used
 * for basic CRUD operations on User entity.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cqueltech.restapi.entity.User;

@Repository
public interface JwtUserRepository extends JpaRepository<User, String> {
  
  // Basic CRUD operations only find entities by the primamry key, therefore to
  // find a user entity by username we have to specify our own method. Spring JPA
  // will be able to automatically generate the CRUD operation based on method
  // name.
  User findByUsername(String username);
}
