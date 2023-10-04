package com.cqueltech.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
// Class declartation for producing a WAR package to deploy to a Tomcat server.
public class RestapiApplication extends SpringBootServletInitializer {
// Class declaration for producing a JAR package to test application locally.
//public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

  /*
   * We need to override the configure() method to deploy to Tomcat container.
   * Method only required when producing WAR package for deploying to Tomcat server.
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(RestapiApplication.class);
  }

}
