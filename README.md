# Java Full Stack Mini Projects

A collection of Java-based mini projects demonstrating practical software development skills, object-oriented programming, desktop application development, inventory management, multithreaded data processing, and web development using Spring Boot and React.

## Student Details

- **Name:** Arnaov Garg
- **Program:** B.Tech — Computer Science and Engineering
- **Branch/Section:** CSE Core D
- **Registration No.:** RA2411003030227
- **Institute:** SRM Institute of Science and Technology

---

## Projects

### 1. Java OOP Calculator

A desktop calculator application developed using Java Swing and object-oriented programming concepts. It supports basic arithmetic, additional mathematical operations, memory functions, and calculation history.

**Features**
- Addition, subtraction, multiplication, and division
- Modulus and power operations
- Square and square root calculations
- Reciprocal calculation
- Positive/negative number toggle
- Average calculation
- Backspace and clear operations
- Memory operations: M+, M-, MR, and MC
- Calculation history and clear history
- Basic input validation and error handling

**Technologies:** Java, Java Swing, Object-Oriented Programming, Maven, JUnit

**Project Folder:** `calculator-executable-jar`

---

### 2. Inventory Management System

A console-based inventory management application developed using Core Java and object-oriented programming. It manages products, categories, stock levels, discounts, and inventory reports while demonstrating file persistence and multithreaded processing.

**Features**
- Product management: add, view, search, update, and delete
- Category management
- Stock addition and removal
- Product discount calculation
- Low-stock reports
- Inventory value calculation by category
- Multithreaded inventory reporting
- CSV report export
- Object serialization for data persistence
- Automatic background data saving
- Exception handling and input validation

**Technologies:** Java, Object-Oriented Programming, Collections Framework, Interfaces, Exception Handling, File I/O, Serialization, Multithreading, ExecutorService, Callable, Future, Maven

**Project Folder:** `inventory-management-system`

---

### 3. Multithreaded Sales Analytics Processor

A Java-based application that processes multiple sales CSV files concurrently and generates consolidated sales analytics and reports.

The application uses Java concurrency utilities to process independent files in parallel, validate sales records, aggregate results, and generate reports.

**Features**
- Concurrent processing of multiple CSV files
- Configurable thread-pool management
- Sales record validation
- Tracking of valid and invalid records
- Total quantity and revenue calculation
- Average revenue per record
- Category-wise sales and revenue analysis
- Top-selling product identification
- Top-three product analysis by quantity sold
- Individual file processing-time measurement
- Overall processing-time measurement
- Consolidated text report generation
- Processed sales data export to CSV
- Graceful thread-pool shutdown

**Technologies:** Java, Multithreading, ExecutorService, Callable, Future, Collections Framework, File I/O, CSV Processing, Exception Handling, Builder Design Pattern

**Project Folder:** `MultiThreaded_Sales_Analytics_Processor`

---

### 4. Student Portfolio Website

A responsive, five-page website developed using Java, Spring Boot, Spring Web, HTML, CSS, and JavaScript. It features a bright green-and-white design and provides multiple pages for presenting the website creator and its features.

**Pages**
1. **Home:** `index.html`
2. **Creator:** `about.html`
3. **Website Overview:** `product.html`
4. **Features:** `features.html`
5. **Contact:** `contact.html`

**Features**
- Five-page website structure
- Responsive navigation
- Mobile-friendly layout
- Consistent green-and-white styling
- Interactive user interface elements
- Static HTML pages served through Spring Boot
- CSS styling and JavaScript interactions

**Technologies:** Java 17+, Spring Boot, Spring Web, HTML5, CSS3, JavaScript, Maven

**Project Folder:** `StudentPortfolioWebsite`

---

### 5. Employee Management Web Application

A full-stack Spring Boot web application featuring dynamic Thymeleaf server-side rendering, role-based Spring Security, HMAC-SHA256 signed JSON Web Token (JWT) validation, and in-memory ArrayList data storage.

**Features**
- Complete Employee CRUD (Create, Read, Update, Delete)
- Department entity relationship and headcount tracking
- In-memory data management using Java `ArrayList` collections
- Custom authentication portal with Spring Security session management
- Spring JWT integration with `jjwt-api`, `jjwt-impl`, and `jjwt-jackson`
- Live JWT token showcase card and REST token verification endpoint (`/api/jwt/token`)
- Multi-field employee search by name, email, or designation
- Filter employees by department
- Executive KPI cards: Total employees, departments, monthly payroll, and average salary
- Modern, responsive UI with status badges and role authorization guards

**Technologies:** Java 17+, Spring Boot 3.3.4, Spring MVC, Spring Security, JJWT, Thymeleaf, HTML5, CSS3, Maven

**Project Folder:** `employee-management-web`

---

### 6. Employee Management System – React

A React-based employee management frontend designed to integrate with a Spring Boot REST backend. It provides an interactive dashboard and interfaces for employee and department administration, while retaining the classroom application's core employee-management workflows.

**Features**
- Employee dashboard
- Employee CRUD operations
- Department management
- Login system
- Role-based views for HR, Manager, and Employee
- Employee directory search
- Department-aware sorting
- Pagination
- Spring Boot REST API integration
- CSV export of the currently filtered employee directory

**Additional Improvements**
- Refreshed blue-and-teal visual theme
- Responsive controls and updated component styling
- CSV export for filtered employee records

**Technologies:** React 19, Vite, JavaScript, CSS3, Spring Boot REST APIs

**Project Folder:** `Module-4`

**Note:** The React frontend is run separately during development. Java files in `backend-integration/` are integration examples intended to be merged with the matching Spring Boot backend; they are not a standalone backend.

---

### 7. Hospital Appointment System

A web-based hospital management application developed using Spring Boot, Spring MVC, Spring Data JPA, MySQL, and Thymeleaf. It supports the management of hospital departments, doctors, patients, and appointments through a web interface.

**Features**
- Hospital department registration and directory
- Doctor registration
- Assignment of doctors to multiple departments
- Patient registration and directory
- Appointment booking with doctor, patient, date, time, and reason
- Appointment register with department information
- Cancellation of booked appointments
- Dashboard summary cards showing department, doctor, patient, and appointment totals
- Appointment reason displayed in the appointment register
- Readable date and time formatting

**Additional Improvements**
- Responsive navy, blue, and teal interface
- Clearer appointment status badges
- Improved form focus states and styling
- Dashboard summary cards for a quick overview

**Technologies:** Java 17+, Spring Boot 3.5.5, Spring MVC, Spring Data JPA, Hibernate, MySQL, Thymeleaf, HTML5, CSS3, Maven

**Project Folder:** `Module-5`

---

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| Java Swing | Desktop graphical user interfaces |
| Spring Boot | Web application development |
| Spring MVC | Web controllers, routing, and model binding |
| Spring Security | Role-based authorization and session protection |
| JJWT | JSON Web Token generation, claims parsing, and signing |
| Spring Data JPA | Database access and persistence |
| Hibernate | Object-relational mapping |
| MySQL | Relational database management |
| React | Component-based frontend development |
| Vite | Frontend development server and build tooling |
| Thymeleaf | Dynamic server-side HTML templating |
| Spring Web | Serving web pages and handling web requests |
| HTML5 | Web page structure |
| CSS3 | Styling and responsive layouts |
| JavaScript | Interactive web elements |
| Maven | Build automation and dependency management |
| JUnit | Unit testing |
| Collections Framework | In-memory data storage and manipulation |
| File I/O | File operations and data handling |
| CSV Processing | Structured data processing and analytics |
| Serialization | Object data persistence |
| Multithreading | Concurrent task execution |
| ExecutorService | Thread-pool management |
| Callable and Future | Executing concurrent tasks and retrieving results |
| Exception Handling | Managing errors and exceptional conditions |

---

## Repository Structure

```text
JavaFullStack-Mini-Projects/
│
├── calculator-executable-jar/
│
├── inventory-management-system/
│
├── MultiThreaded_Sales_Analytics_Processor/
│
├── StudentPortfolioWebsite/
│
├── employee-management-web/
│
├── Module-4/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   └── services/
│   ├── backend-integration/
│   ├── package.json
│   └── README.md
│
├── Module-5/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   ├── pom.xml
│   └── README.md
│
└── README.md
```

---

## Development Approach

These projects demonstrate different software development approaches and programming concepts.

- **Desktop Application Development:** Developing graphical applications using Java Swing.
- **Object-Oriented Programming:** Applying encapsulation, abstraction, inheritance, interfaces, and polymorphism.
- **Inventory Management:** Managing products, categories, stock levels, discounts, and inventory reports.
- **File Processing:** Reading, validating, processing, and exporting structured data.
- **Concurrent Programming:** Processing multiple files and tasks using Java concurrency utilities.
- **Web Development:** Building multi-page and full-stack web applications using Spring Boot and frontend technologies.
- **Frontend Development:** Creating component-based interfaces using React and Vite.
- **Database Management:** Managing hospital-related records using Spring Data JPA, Hibernate, and MySQL.
- **REST API Integration:** Connecting frontend applications with Spring Boot REST endpoints.
- **Modular Programming:** Organizing code into reusable classes and components.
- **Build Management:** Using Maven and Vite to manage dependencies and project builds.
- **Exception Handling:** Handling invalid inputs, runtime errors, and file-processing exceptions.

---

## Learning Outcomes

Through these projects, I have gained practical experience in:

- Java programming and object-oriented design
- Desktop GUI application development
- Collections and data management
- File handling and data persistence
- Serialization and CSV processing
- Exception handling and input validation
- Multithreading and concurrent programming
- Inventory management and sales analytics
- Report generation and data export
- Spring Boot web application development
- REST API development and integration
- React frontend development
- MySQL database connectivity
- Spring Data JPA and Hibernate
- Authentication and role-based authorization
- Responsive web application development
- Maven-based project organization and build management

---

## Purpose

This repository showcases my practical programming experience and continuous learning through the development of desktop, console-based, and full-stack web applications using Java and related technologies.

Each project focuses on applying programming concepts to practical problems while improving problem-solving, software development, database management, and application design skills.

---

## Developer

**Arnaov Garg**  
B.Tech — Computer Science and Engineering  
CSE Core D  
SRM Institute of Science and Technology
