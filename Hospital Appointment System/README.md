# Module 5 — Hospital Appointment System

A Spring Boot + Spring Data JPA + MySQL + Thymeleaf web application for managing hospital departments, doctors, patients and appointments.

## Required classroom features
- Department registration and directory.
- Doctor registration with multi-department assignment.
- Patient registration and directory.
- Appointment booking with doctor, patient, date/time and reason.
- Appointment list with department information and cancellation of booked appointments.

## Added improvements
- Dashboard summary cards showing department, doctor, patient and appointment totals.
- Appointment reason is visible in the appointment register and date/time is formatted for readability.
- Refreshed responsive navy/blue/teal interface with clearer status badges and form focus states.

## Technology
Java 17+, Spring Boot 3.5.5, Spring MVC, Thymeleaf, Spring Data JPA, Hibernate and MySQL.

## Setup
1. Install JDK 17 or later and MySQL 8+.
2. Open `src/main/resources/application.properties` and set your own MySQL username/password. The default database name is `hospital_db` and can be created automatically by the JDBC URL; alternatively run `CREATE DATABASE hospital_db;`.
3. From this folder run:

```bash
mvn spring-boot:run
```

Or build and run:

```bash
mvn clean package
java -jar target/hospital-appointment-system-1.0.0.jar
```

4. Open `http://localhost:8080`.
5. Add at least one department, doctor and patient before booking an appointment. A doctor can be assigned to multiple departments using Ctrl/Command selection.

## Main routes
- `/` — dashboard
- `/departments` — department registration/list
- `/doctors` — doctor registration/list
- `/patients` — patient registration/list
- `/appointments` — booking, appointment register and cancellation

## Notes
The database schema is managed with `spring.jpa.hibernate.ddl-auto=update`. Use a local development database and do not expose it publicly with default credentials. This is an educational project, not a production hospital system; production use would require authentication, authorization, audit logs, privacy controls and stronger validation.
