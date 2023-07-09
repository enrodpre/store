Hi there!

This is a Spring boot full example application

The project is a Multi-Module. The main technology stack is:

- JDK11
- Maven
- Spring Boot
- Tomcat (embedded)
- H2 (embedded)
- Lambok
- Swagger

To run the application just:

- Run "mvn clean install" for the root project.
- Run "mvn spring-boot:run" for the bootstrap project.

Once running go to localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config for the api specification.
