# FoodBag App

Project for school course service side programming.
Goal is to create an app which keeps track of a weekly diet.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

## Prerequisites
Simplest way to start the program is to install Docker. https://www.docker.com/
Otherwise you'll be needing at least:
* MariaDb
* Java 8 at least
* Fix application.conf file accordingly 


#### Run the project
By typing in the projects home directory(where the docker-compose.yml is located):
````bash
docker-compose up -d
````

Head to http://localhost:8080<br>
Should see the login page.

There's one test user included so you can try login with these credentials:
* email: demo@demo.com
* pass: demo

Db details are found in src/main/resources/application.conf

## Running the tests
Only the tests can be run by typing:
````bash
./mvnw test
````

This will run all tests found in src/test folder, including mock and unit tests.

#### With docker:
/mvnw clean package
docker build -t foodbag .
docker run -p 8080:8080 foodbag

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot) - Java Framework
* [Mockito](https://site.mockito.org/) - Mocking Framework
* [HikariCp](https://github.com/brettwooldridge/HikariCP) - JDBC connection pool
