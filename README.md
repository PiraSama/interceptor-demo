# Spring MVC Interceptor Demo

Demo project demonstrating Spring MVC Interceptors for:
1. Request processing time logging
2. Authentication and authorization checking

## Features
- LoggingInterceptor: Logs request execution time
- AuthInterceptor: Checks user authentication and authorization
- Automatic redirect to login page if not authenticated
- Role-based access control for admin pages

## Running the Application

### Prerequisites
- JDK 11 or higher
- Maven 3.6+

### Steps
1. Clone the repository
2. Run: `mvn spring-boot:run`
3. Access: http://localhost:8080

### Test Accounts
- Admin: username=admin, password=admin123
- User: username=user, password=user123

## Project Structure
See code comments for detailed file structure.

## Technologies
- Spring Boot 2.7.18
- Spring MVC
- Thymeleaf
- Maven"# interceptor-demo" 
