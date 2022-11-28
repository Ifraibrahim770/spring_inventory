# Spring Store Inventory App
Spring Boot API for Inventory Management


## PreRequisites:<br>
- JAVA Version 17 (openjdk:17)
- Maven Version 3.8.4

## To deploy the project (Docker):<br>
- Install Docker (get it from [HERE](https://docs.docker.com/get-docker/):  and make sure its added to the PATH
- Navigate to the project's root directory
- Open a terminal session using Windows Terminal, GitBash or any other CLI tool
- Run the command:<br>
  ```
  docker-compose up
  ``` 
- Wait for the image to build and run on port 8080

## To deploy the project (Locally):<br>
- Clone the repository
- Navigate to the project's root directory
- Open a terminal session using Windows Terminal, GitBash or any other CLI tool
- Run the command:<br>
  ```
  mvn spring-boot:run
  ``` 
- Wait for the image to build and run on port 8080

## Documentation:<br>
- The application has an embedded swagger web page showcasing all the endpoints
- To access the endpoint visit: 
 ```
  http://localhost:8080/swagger-ui/index.html
  ```
- For the spring doc alternative visit
```
  http://localhost:8080/api-docs
  ```
- For the PostMan Collection Look under
  [file.pdf](/docs/INVENTORY%20COLLECTION.postman_collection.json)

## Tests <br>
- To run all the unit tests Run the command:<br>
  ```
  mvn clean test
  ``` 


