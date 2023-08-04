# Beer Dispenser App
This is a sample beer dispenser management application built with Spring Boot.

It allows you to:

* Manage beers, taps, promoters, admins and attendees
* Start and stop beer servings
* View serving details and calculate billing
* View promoter earnings based on servings

### Features
* REST API with endpoints to manage all resources and operations
* Swagger UI documentation available at http://localhost:8080/swagger-ui/index.html
* In-memory H2 database
* Exception handling and input validation

### Technologies Used
* Java 17
* Spring Boot
* H2 Database
* Lombok
* Swagger
* Gradle 

## Getting Started
### Prerequisites
* Java 17
* Gradle

### Installing
Clone the repository
```
git clone https://github.com/rohit1729/beer-dispenser-app.git --no-checkout --filter=blob:limit=50m
```

### Build using Gradle
./gradlew build

### Run the application
./gradlew bootRun

### Running the JAR file directly
* Download the jar file from here https://github.com/rohit1729/beertap/blob/main/beerdispenser-app.jar
* Run ``` java -jar beerdispenser-app.jar ```

### API Reference
The API documentation can be found at http://localhost:8080/swagger-ui/index.html after starting the application.

It contains details on the endpoints to:

* Manage beers, taps, promoters, admins, attendees
* Start and stop servings
* Get serving details
* Get promoter earnings
