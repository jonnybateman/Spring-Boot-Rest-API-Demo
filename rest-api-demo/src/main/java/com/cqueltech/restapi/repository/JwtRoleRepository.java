package com.cqueltech.restapi.repository;

/*
 * Repository interface since we are using Spring Data JPA. Repository used
 * for basic CRUD operations on Role entity.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cqueltech.restapi.entity.Role;
import java.util.Set;

@Repository
public interface JwtRoleRepository extends JpaRepository<Role, Integer> {

  // Basic CRUD operations only find entities by the primamry key, therefore to
  // find roles associated with a username we have to specify our own method.
  // Spring JPA will be able to automatically generate the CRUD operation based
  // on method name.
  Set<Role> findByUsername(String username);
}
