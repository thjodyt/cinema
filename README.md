# Cinema Seat Reservation System

## Description:

This application was developed as part of the "team project" coursework. The project aimed to create a comprehensive cinema seat reservation system that caters to both cinema customers and employees.

### Features:

For Customers:

* User Account Creation:
  Allows users to create accounts, enabling them to access additional features such as reservations and reservation history.
* Cinema Repertoire Browsing:
  Non-registered users can browse available movies, the schedule, and seat availability.
* Seat Reservation:
  Enables customers to reserve seats for any screening by selecting seats from an interactive cinema hall map.
* Reservation History Viewing:
  Logged-in users can track their reservation history and view details of specific screenings.

For Cinema Employees:
* Adding Movies to Repertoire:
  Administrators can add new films to the cinema's repertoire.
* Scheduling Screenings:
  Administrators can plan and schedule new film screenings, defining dates, times, and cinema halls.
* Adding Employees:
  Allows administrators to add new employees with various permissions, such as administrator or cashier.
* Cashier Reservation Handling:
  Cashiers can handle reservations for customers at the cinema's counter using the system.


## Installation:

To get started with the application, you'll need to have Java 21 or later installed on your machine.

1. Download this application by executing the following command in your cmd:

`git clone https://github.com/thjodyt/cinema.git`

2. Rename file: `/cinema/src/main/resources/application.properties.template` to: `/cinema/src/main/resources/application.properties` and paste address to your empty MySql database, username and password. Come back to your cmd and run following commands:

`cd cinema/`

`mvn install`

`cd target/`

`java -jar cinema-0.0.1-SNAPSHOT.jar`

3. Open browser, and go to: `localhost:8080/cinema`. You can create new user or sign in as predefined admin - email: `admin@admin.com`, password: `pass`

## Dependencies:

- [Thymeleaf](https://www.thymeleaf.org/) - Template engine for the view layer.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Simple and efficient database integration using JPA (Java Persistence API).
- [Spring Security](https://spring.io/projects/spring-security) - Provides security features such as authentication, authorization, and session management.
- [Spring Validation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-form-validation) - Module for validating input data in the controller layer.
- [Liquibase](https://www.liquibase.org/) - A tool for controlling and managing the version of the database structure.
- [Lombok](https://projectlombok.org/) - A library that makes writing code easier by automatically generating methods such as getters, setters, etc.
