package com.cqueltech.restapi.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cqueltech.restapi.entity.Course;
import com.cqueltech.restapi.entity.Instructor;
import com.cqueltech.restapi.entity.Role;
import com.cqueltech.restapi.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {

  // Define field for Entity Manager. EntityManager is an API that manages the
  // lifecycle of entity instances.
  private EntityManager entityManager;

  // Inject EntityManager using constructor injection.
  @Autowired
  public UserDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  
  @Override
  public User findUserByUsername(String username) {
    
    return entityManager.find(User.class, username);
  }

  @Override
  public void save(User user) {
    
    // Store the User object in the database.
    entityManager.persist(user);
  }

  @Override
  public void save(Role role) {
    
    // Store the Role object in the database.
    entityManager.persist(role);
  }

  @Override
  public List<Course> findAllCourses() {
    
    // Create a query.
    TypedQuery<Course> query = entityManager.createQuery("select c from Course c", Course.class);

    // Execute query and get the result list.
    List<Course> courses = query.getResultList();

    // Return the results
    return courses;
  }

  @Override
  public List<Instructor> findAllInstructors() {
    
    // Create a query.
    TypedQuery<Instructor> query = entityManager.createQuery("select i from Instructor i", Instructor.class);

    // Execute the query and get the result list.
    List<Instructor> instructors = query.getResultList();

    // Return the results.
    return instructors;
  }
}
