# Java Full Stack Mini Projects

A collection of Java-based mini projects demonstrating practical software development skills, object-oriented programming, desktop application development, inventory management, multithreaded data processing, and web development using Spring Boot.

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

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| Java Swing | Desktop graphical user interfaces |
| Spring Boot | Web application development |
| Spring Web | Serving web pages and handling web requests |
| HTML5 | Web page structure |
| CSS3 | Styling and responsive layouts |
| JavaScript | Interactive web elements |
| Maven | Build automation and dependency management |
| JUnit | Unit testing |
| Collections Framework | Data storage and manipulation |
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
├── MultiThreaded_Sales_Analytics_Processor/
│   ├── data/
│   │   ├── sales_february.csv
│   │   ├── sales_january.csv
│   │   └── sales_march.csv
│   ├── src/
│   │   ├── config/
│   │   │   └── ProcessorConfig.java
│   │   ├── model/
│   │   │   ├── FileProcessingResult.java
│   │   │   └── SalesRecord.java
│   │   ├── processor/
│   │   │   └── CsvFileProcessor.java
│   │   ├── report/
│   │   │   └── ReportAggregator.java
│   │   └── Main.java
│   ├── run.bat
│   └── README.md
│
├── StudentPortfolioWebsite/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── studentportfolio/
│   │       │           └── StudentPortfolioApplication.java
│   │       └── resources/
│   │           └── static/
│   │               ├── index.html
│   │               ├── about.html
│   │               ├── product.html
│   │               ├── features.html
│   │               ├── contact.html
│   │               ├── css/
│   │               │   └── style.css
│   │               └── js/
│   │                   └── main.js
│   ├── pom.xml
│   └── README.md
│
├── calculator-executable-jar/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── myapp/
│   │   │                   └── App.java
│   │   └── test/
│   │       └── java/
│   │           └── com/
│   │               └── example/
│   │                   └── myapp/
│   │                       └── AppTest.java
│   ├── pom.xml
│   └── README.md
│
├── inventory-management-system/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── example/
│   │                   └── inventory/
│   │                       ├── Main.java
│   │                       ├── interfaces/
│   │                       │   ├── Discountable.java
│   │                       │   └── Reportable.java
│   │                       ├── io/
│   │                       │   ├── CsvReportExporter.java
│   │                       │   └── FileManager.java
│   │                       ├── manager/
│   │                       │   └── InventoryManager.java
│   │                       ├── model/
│   │                       │   ├── Category.java
│   │                       │   ├── InventoryItem.java
│   │                       │   └── Product.java
│   │                       └── service/
│   │                           ├── AutoSaveService.java
│   │                           └── ReportService.java
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
- **Web Development:** Building a multi-page website using Spring Boot and frontend technologies.
- **Modular Programming:** Organizing code into reusable classes and components.
- **Build Management:** Using Maven to manage dependencies and project builds.
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
- Frontend development using HTML, CSS, and JavaScript
- Maven-based project organization and build management

---

## Purpose

This repository showcases my practical programming experience and continuous learning through the development of desktop, console-based, and web applications using Java and related technologies.

Each project focuses on applying programming concepts to practical problems while improving problem-solving, software development, and application design skills.

---

## Developer

**Arnaov Garg**  
B.Tech — Computer Science and Engineering  
CSE Core D  
SRM Institute of Science and Technology
