# CourseMG
A Spring Boot basded Restful service to manage student course registration.

## Requirements

- JDK 1.8+
- IntelliJ IDE 
- Lombok plugin is required for IDE (https://projectlombok.org/setup/intellij)


## Installation

- Checkout code from `git clone https://github.com/peterwangxw/CourseMG.git`.
- Import the project into IntelliJ IDE.
- Change the mysql database configuration in `application.properties` as the below, then start the application.

```
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/course
spring.datasource.username=root
spring.datasource.password=Hanhan123!
```

## Test with Swagger UI

- Open `http://localhost:8080/swagger-ui.html` in your browser, you will see:
![MacDown Screenshot](https://i.ibb.co/FD45xBT/RestAPI.png)

#### student-controller
- `POST "/v1/student"` - create a student with the payload like `{"firstName":"Peter", "lastName":"Wang"}`
- `GET "/v1/student?course="` - retrive all the students
- `GET "/v1/student/{id}"` - retrieve a student by ID
- `DELETE "/v1/student/{id}"` - delete a student with his ID.

#### course-controller
- `POST "/v1/course"` - create a course with the payload like `{"name":"Math"}`
- `GET "/v1/course"` - retrive all the courses
- `GET "/v1/course/{id}"` - retrieve a course by ID
- `DELETE "/v1/course/{id}"` - delete a course with his ID
- `PUT "/v1/register/{courseId}/{studentId}"` - register a course