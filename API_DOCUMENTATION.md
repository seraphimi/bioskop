# Bioskop - Spring Boot RESTful Web Service

Kompletan Spring Boot projekat za upravljanje bioskopskim sistemom sa multi-layer arhitekturom.

## Tehnički zahtevi

- **Spring Boot 3.0** sa **JDK 17**
- **MySQL** baza podataka
- **Multi-layer arhitektura** (Controller, Service, Repository, DAO)
- **JSON komunikacija**
- **Logovanje sa AOP pointcut-ovima** (after/before)

## Pokretanje aplikacije

### Preduslovi
1. JDK 17 ili noviji
2. Maven 3.6 ili noviji
3. MySQL server

### Konfiguracija baze podataka
1. Kreirajte MySQL bazu podataka `bioskop_db`
2. Ažurirajte connection podatke u `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bioskop_db
spring.datasource.username=root
spring.datasource.password=password
```

### Pokretanje
```bash
mvn spring-boot:run
```

Aplikacija će biti dostupna na `http://localhost:8080`

## API Endpoints

### Filmovi (`/api/filmovi`)
- `GET /api/filmovi` - Pregled svih filmova
- `GET /api/filmovi/{id}` - Pregled filma po ID
- `POST /api/filmovi` - Kreiranje novog filma
- `PUT /api/filmovi/{id}` - Ažuriranje filma
- `DELETE /api/filmovi/{id}` - Brisanje filma
- `GET /api/filmovi/search/zanr?zanr=akcija` - Pretraga po žanru
- `GET /api/filmovi/search/naziv?naziv=film` - Pretraga po nazivu
- `GET /api/filmovi/search/reditelj?reditelj=ime` - Pretraga po rediteljу
- `GET /api/filmovi/search/ocena?minOcena=7.0` - Pretraga po minimalnoj oceni
- `GET /api/filmovi/search/trajanje?minTrajanje=90&maxTrajanje=180` - Pretraga po trajanju

### Projekcije (`/api/projekcije`)
- `GET /api/projekcije` - Pregled svih projekcija
- `GET /api/projekcije/{id}` - Pregled projekcije po ID
- `POST /api/projekcije` - Kreiranje nove projekcije
- `PUT /api/projekcije/{id}` - Ažuriranje projekcije
- `DELETE /api/projekcije/{id}` - Brisanje projekcije
- `GET /api/projekcije/film/{filmId}` - Projekcije određenog filma
- `GET /api/projekcije/available` - Dostupne projekcije (sa sedištima)
- `GET /api/projekcije/upcoming` - Nadolazeće projekcije
- `GET /api/projekcije/upcoming/film/{filmId}` - Nadolazeće projekcije filma

### Korisnici (`/api/korisnici`)
- `GET /api/korisnici` - Pregled svih korisnika
- `GET /api/korisnici/{id}` - Pregled korisnika po ID
- `POST /api/korisnici` - Kreiranje novog korisnika
- `PUT /api/korisnici/{id}` - Ažuriranje korisnika
- `DELETE /api/korisnici/{id}` - Brisanje korisnika
- `GET /api/korisnici/search/email?email=user@example.com` - Pretraga po email-u

### Rezervacije (`/api/rezervacije`)
- `GET /api/rezervacije` - Pregled svih rezervacija
- `GET /api/rezervacije/{id}` - Pregled rezervacije po ID
- `POST /api/rezervacije` - Kreiranje nove rezervacije
- `PUT /api/rezervacije/{id}` - Ažuriranje rezervacije
- `DELETE /api/rezervacije/{id}` - Brisanje rezervacije
- `GET /api/rezervacije/korisnik/{korisnikId}` - Rezervacije korisnika
- `GET /api/rezervacije/film/{filmId}` - Rezervacije za film
- `GET /api/rezervacije/projekcija/{projekcijaId}` - Rezervacije za projekciju
- `PUT /api/rezervacije/{id}/kupovina` - Kupovina karte

### Recenzije (`/api/recenzije`)
- `GET /api/recenzije` - Pregled svih recenzija
- `GET /api/recenzije/{id}` - Pregled recenzije po ID
- `POST /api/recenzije` - Kreiranje nove recenzije
- `PUT /api/recenzije/{id}` - Ažuriranje recenzije
- `DELETE /api/recenzije/{id}` - Brisanje recenzije
- `GET /api/recenzije/film/{filmId}` - Recenzije za film
- `GET /api/recenzije/korisnik/{korisnikId}` - Recenzije korisnika
- `GET /api/recenzije/film/{filmId}/prosecna-ocena` - Prosečna ocena filma

## Primeri JSON zahteva

### Kreiranje filma
```json
POST /api/filmovi
{
    "naziv": "Avengers: Endgame",
    "zanr": "Akcija/Sci-Fi",
    "trajanje": 181,
    "reditelj": "Anthony i Joe Russo",
    "glumci": "Robert Downey Jr., Chris Evans, Mark Ruffalo",
    "ocena": 8.4,
    "opis": "Epski završetak Infinity Sage..."
}
```

### Kreiranje projekcije
```json
POST /api/projekcije
{
    "datumVreme": "2024-12-31T20:00:00",
    "sala": 1,
    "ukupnaSedista": 120,
    "film": {
        "id": 1
    }
}
```

### Kreiranje korisnika
```json
POST /api/korisnici
{
    "ime": "Marko",
    "prezime": "Petrović",
    "email": "marko.petrovic@example.com"
}
```

### Kreiranje rezervacije
```json
POST /api/rezervacije
{
    "odabranaSedista": 2,
    "film": {
        "id": 1
    },
    "projekcija": {
        "id": 1
    },
    "korisnik": {
        "id": 1
    }
}
```

### Kreiranje recenzije
```json
POST /api/recenzije
{
    "ocena": 9.0,
    "tekstRecenzije": "Odličan film! Preporučujem svima.",
    "film": {
        "id": 1
    },
    "korisnik": {
        "id": 1
    }
}
```

## Karakteristike

### Multi-layer arhitektura
- **Entity** klase sa JPA anotacijama
- **Repository** interfejsi za pristup podacima
- **DAO** klase za rad sa podacima
- **Service** klase za business logiku
- **Controller** klase za REST API

### AOP Logovanje
Implementirano sa @Before, @After, @Around, @AfterReturning, @AfterThrowing pointcut-ovima:
- Logovanje ulaska u metode
- Logovanje izlaska iz metoda
- Merenje vremena izvršavanja
- Logovanje grešaka
- Logovanje rezultata

### Validacija
- Bean Validation (@NotNull, @NotBlank, @Email, @Min, @Max)
- Business logic validacija u servisnim klasama

### File Storage
- Recenzije se automatski čuvaju u TXT fajlove
- Putanja: `./reviews/film_{id}_recenzije.txt`

### CORS podrška
- Omogućena za sve origine
- Podržane HTTP metode: GET, POST, PUT, DELETE, OPTIONS

## Testiranje

```bash
# Pokretanje testova
mvn test

# Pokretanje sa izvештajima
mvn clean test
```

## Build

```bash
# Kompajliranje
mvn compile

# Kreiranje JAR fajla
mvn package

# Pokretanje JAR fajla
java -jar target/bioskop-0.0.1-SNAPSHOT.jar
```