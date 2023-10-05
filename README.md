# Spring Boot Rest API Demo

RESTful API is an interface that two computer systems use to exchange information securely over the internet. An application programming interface (API) defines the rules that you must follow to communicate with other software systems. Developers expose or create APIs so that other applications can communicate with their applications programmatically. You can think of a web API as a gateway between clients and resources on the web.

### Client

Clients are users who want to access information from the web. The client can be a person or a software system that uses the API.

### Resources

Resources are the information that different applications provide to their clients. Resources can be images, videos, text, numbers, or any type of data. The machine that gives the resource to the client is also called the server. Organizations use APIs to share resources and provide web services while maintaining security, control, and authentication.

### What is a REST API

REST stands for Representational State Transfer. This means that when a client requests a resource using a REST API, the server transfers back the current state of the resource in a standardized representation. API developers can design APIs using several different architectures. APIs that follow the REST architectural style are called REST APIs. Web services that implement REST architecture are called RESTful web services.

## RestApi Demo

The demo in this repository is for a RESTful API Web Service. It has a MySQL backend database which the web service can view and modify. In this demo the database tracks students and instructors in relation to courses. The entity relationship diagram for the database can be viewed below.

In addition to the Rest Web Service API a Web Application has also been developed to view and modify the resources hosted by the server. It can be accessed through the link [RestApi_Application](https://cqueltech.com/restapi-0.0.1-SNAPSHOT).

### Instructor-Student-Tracking schema
![Alt text](./RestApi-ERD-Dark-SVG.svg)

To use the web service/application one must login in first. A user has associated role(s), each role has it's own level of access to the database. The role depicts what can be viewed, modified or created within the datbase. The roles, and their associated database access, are listed below.

- Student role:
  - view courses and associated instructor conducting the course
  - view course reviews.
- Instructor role:
  - view instructors and the associated instructor detail
  - view students enrolled on courses
  - remove students from courses
  - enroll student onto course
- Admin role:
  - create a new course
  - create a new student
  - create a new instructor
 
To create a user just press the `Register User` button in the sign-in page. It just requires a username and password, no personal details are required or stored. By default the user will be created with the `Student` role. The `Instructor` role can also be assigned to the user by checking the applicable check box. The `Admin` role is for database administrators only.

The web service/application is deployed on an Apache Tomcat server with an Apache server acting as a reverse proxy to it.

## Web Service HTTP Request Configuration

### Request Types

All requests are made by adding a specified end point to the following URL: https://cqueltech.com/restapi-0.0.1-SNAPSHOT/api

|Method|Endpoint|Description|
|------|--------|-----------|
|POST|/login|Login to the service passing a JSON request body as set out in the parameters below. Returns an Oauth2 JWT Token to be used in authorizing further requests.|
|GET|/courses|This request returns a list of courses with associated instructor ids. Request requires an Authorization header of format "Bearer your-jwt-token".|
|GET|/course-reviews|Returns a list of submitted reviews for each course. Request requires an Authorization header of format "Bearer your-jwt-token".|
|GET|/instructors|Request returns a list of instructors with associated instructor detail. Requires the aforementioned Authorization header.|
|GET|/students|This request will return a list of courses with all students enrolled on these courses. Requires the aforementioned Authorization header.|
|POST|/enrol-student|This post request enrols a student onto a course. Requires the aforementioned Authorization header.|
|POST|/create-course|Will create a new course using parameters found in the supplied request body. Requires the aforementioned Authorization header.|
|POST|/create-student|Will create a new student using parameters found in the supplied request body. Requires the aforementioned Authorization header.|
|POST|/create-instructor|Will create a new instructor using parameters found in the supplied request body. Requires the aformentioned Authorization header.|

### Request Parameters
