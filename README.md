# hmcts-dev-test-backend

This task has been implemented for the HMCTS developer role by using Java SpringBoot framework. 
This project implements functionality for managing tasks. The project has set of REST APIs to create, update, delete, and retrieve tasks.

Technologies
- Build Tool: Java - Maven
- Framework: Spring Boot
- Unit tests: JUnit 5 and Mockito

Prerequisites
You will need Java 17+ and Maven installed to run this project.

When you run the project locally, you can test it with Swagger on the port localhost:8080


How Does the Flow Work?
When we send a Task record to the POST /api/tasks endpoint via Swagger.
The Spring Controller receives and sends it to the Service layer. And then, Service calls repository.save(task)
The Repository saves this task into the TASK table in the H2 database.

As a result, there will be a TASK table in RAM.
We can go to 'http://localhost:8080/h2-console' and run "SELECT * FROM TASK;" query to see data.
When the application stops, the H2 database is reset (because it's in RAM).


2025 May®

End
