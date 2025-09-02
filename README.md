# Bioskop - Cinema Management System

A Spring Boot application demonstrating AOP (Aspect-Oriented Programming) logging using **only @After pointcuts**.

## Overview

This application implements a cinema management system with comprehensive logging using Spring AOP. The logging aspect is designed to use only @After pointcuts instead of @Before pointcuts, providing logging capabilities after method execution.

## AOP Logging Implementation

The logging aspect (`LoggingAspect`) uses three types of @After pointcuts:

1. **@After** - Logs after any method execution (successful or failed)
2. **@AfterReturning** - Logs after successful method execution with return value
3. **@AfterThrowing** - Logs after method execution that throws an exception

### Key Features

- ✅ Uses only @After pointcuts (no @Before pointcuts)
- ✅ Logs method execution completion
- ✅ Captures return values from successful executions
- ✅ Handles and logs exceptions
- ✅ Intercepts service layer methods
- ✅ Intercepts controller layer methods

## Project Structure

```
src/main/java/com/bioskop/
├── BioskopApplication.java          # Main Spring Boot application
├── aspect/
│   └── LoggingAspect.java           # AOP logging aspect with @After pointcuts
├── controller/
│   └── BioskopController.java       # REST controller
├── service/
│   ├── MovieService.java            # Movie management service
│   └── TicketService.java           # Ticket booking service
└── demo/
    └── AopLoggingDemo.java          # Demo application
```

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build and Test
```bash
mvn clean compile
mvn test
```

### Run the Demo
```bash
mvn spring-boot:run
```

The demo will automatically run and show the AOP logging in action.

### Example Output

When you run the application, you'll see logging output like this:

```
Method executed successfully: MovieService.addMovie, returned: Movie added: The Matrix
Method execution completed: MovieService.addMovie
Method executed successfully: TicketService.bookTicket, returned: Booked 3 tickets for John Doe. Remaining seats: 97
Method execution completed: TicketService.bookTicket
Method threw exception: MovieService.addMovie, exception: Movie title cannot be null or empty
Method execution completed: MovieService.addMovie
```

## API Endpoints

- `POST /api/movies` - Add a new movie
- `GET /api/movies` - Get all movies
- `POST /api/tickets/book` - Book tickets
- `GET /api/tickets/available` - Get available seats

## AOP Configuration

The application uses:
- `@EnableAspectJAutoProxy` to enable AOP
- Spring Boot Starter AOP for AspectJ support
- SLF4J for logging

## Testing

The project includes comprehensive tests in `AopLoggingTest.java` that validate:
- AOP logging functionality
- Exception handling logging
- Spring context loading with AOP enabled
