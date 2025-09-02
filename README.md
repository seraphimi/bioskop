# Bioskop - Movie Theater Management System

Complete Spring Boot RESTful web service for movie theater management with multi-layer architecture.

## Project Overview

This project implements a comprehensive movie theater management system using Spring Boot 3.0 with JDK 17. The system provides full CRUD operations for movies, screenings, users, reservations, and reviews with a robust multi-layer architecture.

## Technical Stack

- **Framework**: Spring Boot 3.0
- **Java Version**: JDK 17
- **Database**: MySQL (production), H2 (testing)
- **Architecture**: Multi-layer (Controller → Service → DAO → Repository)
- **Communication**: JSON REST API
- **Logging**: AOP with pointcuts (@Before/@After/@Around)
- **Build Tool**: Maven
- **Testing**: JUnit 5, Spring Boot Test

## Architecture Components

### Entities (Domain Layer)
- **Film** - Movie information (title, genre, duration, director, cast, rating, description)
- **Projekcija** - Screening details (date/time, hall, available seats)
- **Korisnik** - User management (name, surname, email)
- **Rezervacija** - Reservation system (selected seats, status, total price)
- **Recenzija** - Review system (rating, review text, user, movie)

### Layers
1. **Controller Layer** - REST API endpoints with JSON communication
2. **Service Layer** - Business logic and validation
3. **DAO Layer** - Data access abstraction
4. **Repository Layer** - JPA repositories with custom queries

### Features
- ✅ Complete CRUD operations for all entities
- ✅ Advanced search and filtering capabilities
- ✅ Automatic seat availability management
- ✅ File-based review storage (TXT files)
- ✅ Comprehensive input validation
- ✅ AOP logging with multiple pointcuts
- ✅ CORS support for cross-origin requests
- ✅ Transaction management
- ✅ JPA relationships and cascading

## Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd bioskop
   ```

2. **Setup MySQL database**
   - Create database: `bioskop_db`
   - Update credentials in `application.properties`

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access API**
   - Base URL: `http://localhost:8080`
   - API endpoints: `/api/{filmovi|projekcije|korisnici|rezervacije|recenzije}`

## API Endpoints

### Core Operations
- **Movies**: `/api/filmovi` - Full CRUD + search by genre, director, rating
- **Screenings**: `/api/projekcije` - Schedule management + availability checks
- **Users**: `/api/korisnici` - User management + email lookup
- **Reservations**: `/api/rezervacije` - Booking system + ticket purchasing
- **Reviews**: `/api/recenzije` - Review system + average ratings

### Special Features
- Automatic price calculation (500 RSD per seat)
- Real-time seat availability updates
- Review file storage system
- Business rule validation
- Comprehensive error handling

## Documentation

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for detailed API usage examples and endpoint specifications.

## Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn clean test

# Integration tests use H2 in-memory database
```

## Development

```bash
# Compile only
mvn compile

# Package JAR
mvn package

# Clean build
mvn clean package
```

## AOP Logging

The system includes comprehensive logging using AspectJ:
- Method entry/exit logging
- Performance monitoring
- Exception tracking
- Result logging
- Service layer monitoring

## Database Schema

The system automatically creates MySQL tables with proper relationships:
- Foreign key constraints
- Cascade operations
- Index optimization
- Data validation constraints

## Contributing

1. Follow the existing code structure
2. Maintain the multi-layer architecture pattern
3. Add appropriate validation and error handling
4. Include unit tests for new functionality
5. Update documentation as needed

## License

This project is developed for educational and demonstration purposes.
