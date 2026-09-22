# 📦 Inventory Management System

A console-based **Inventory Management System** built in core Java, using
**Object-Oriented Programming**, **Interfaces**, **Multithreading**, **File
I/O (serialization + CSV)**, and **Maven** as the build tool.

> Built as a Java Full Stack practical project. This version extends an
> original single-package implementation into a properly layered,
> Maven-based application with additional OOP and concurrency features.

---

## 📌 1. Objective

To design and implement a menu-driven inventory management application that
allows a store/warehouse operator to:

- Manage **products** (add, view, search, update, delete, discount)
- Manage **categories** (add, view, update, delete)
- Manage **stock levels** (add stock, remove stock, low-stock alerts)
- Generate **reports** (low-stock report, category-wise value report, CSV export)
- Persist all data across runs using file storage

while demonstrating core Java concepts: **classes & objects, inheritance,
abstraction, encapsulation, polymorphism, interfaces, exception handling,
collections, and multithreading**.

---

## 🛠️ 2. Technologies Used

| Category            | Technology                                   |
|----------------------|-----------------------------------------------|
| Language             | Java 11+                                       |
| Build Tool           | Apache Maven                                   |
| Persistence          | Java Object Serialization (`.dat` files)       |
| Reporting            | CSV export (`java.io`)                         |
| Concurrency          | `Thread`, `Runnable`, `ExecutorService`, `Callable`, `Future` |
| Input Handling        | `java.util.Scanner`                            |
| Packaging            | Maven Jar Plugin → runnable `.jar`             |

---

## ✨ 3. Key Features

### Product Management
- Add / View / Search / Update / Delete products
- Apply a percentage **discount** to a product's price

### Category Management
- Add / View / Update / Delete categories
- A category cannot be deleted if a product still references it

### Stock Management
- Add stock, remove stock (with quantity validation)
- View current stock levels for all products

### Reports
- **Low Stock Report** — lists products at or below a chosen threshold
- **Inventory Value Report (multithreaded)** — computes item count and
  total stock value **per category concurrently**, then aggregates the
  results into a single summary table
- **CSV Export** — writes the full product list to `inventory_report.csv`

### Reliability
- A background **auto-save thread** persists all data to disk every
  30 seconds automatically, independent of the menu thread

---

## 🧠 4. OOP & Java Concepts Demonstrated

| Concept                | Where it's used                                                                 |
|--------------------------|----------------------------------------------------------------------------------|
| **Abstraction**           | `InventoryItem` — abstract class with an abstract `getDetails()` method         |
| **Inheritance**           | `Product` and `Category` both `extends InventoryItem`                           |
| **Interfaces**            | `Reportable`, `Discountable` — `Product` implements both at once                |
| **Polymorphism**          | `getDetails()`, `generateReport()`, `toString()` overridden differently per class |
| **Encapsulation**         | All model fields are `private`, exposed only via getters/setters                |
| **Multithreading**        | `AutoSaveService` (background `Thread`), `ReportService` (`ExecutorService` + `Callable`/`Future`) |
| **Thread Synchronization**| `synchronized` methods in `InventoryManager` and `FileManager`                  |
| **Exception Handling**    | Input validation loops, `IllegalArgumentException` on invalid discounts, try/catch around I/O |
| **Collections**           | `List<Product>`, `List<Category>`, `Set<String>`, `Collections.unmodifiableList` |
| **File I/O**              | Object serialization (`.dat`) for persistence, `PrintWriter` for CSV export     |
| **Layered Architecture**  | UI (`Main`) → Service (`InventoryManager`, `ReportService`) → Persistence (`FileManager`, `CsvReportExporter`) |

---

## 🗂️ 5. Project Structure

```
inventory-management-system/
├── pom.xml
├── README.md
├── .gitignore
├── target/
│   └── inventory-management-system-1.0-SNAPSHOT.jar   # prebuilt runnable jar
└── src/main/java/com/example/inventory/
    ├── Main.java                    # console UI / menu layer (entry point)
    ├── model/
    │   ├── InventoryItem.java       # abstract base class (id, name, getDetails())
    │   ├── Product.java             # extends InventoryItem, implements Reportable, Discountable
    │   └── Category.java            # extends InventoryItem, implements Reportable
    ├── interfaces/
    │   ├── Reportable.java          # generateReport()
    │   └── Discountable.java        # applyDiscount(percent)
    ├── manager/
    │   └── InventoryManager.java    # business/service layer, thread-safe
    ├── io/
    │   ├── FileManager.java         # serialization to/from products.dat, categories.dat
    │   └── CsvReportExporter.java   # exports products to inventory_report.csv
    └── service/
        ├── AutoSaveService.java     # background auto-save thread
        └── ReportService.java       # multithreaded value report + CSV trigger
```

---

## 🏗️ 6. Class Overview (Simplified)

```
                 ┌────────────────────┐
                 │  InventoryItem     │  (abstract)
                 │  - id, name        │
                 │  + getDetails()    │
                 └─────────▲──────────┘
                            │ extends
        ┌───────────────────┴───────────────────┐
        │                                        │
┌───────────────┐                      ┌────────────────────┐
│   Category     │                      │      Product        │
│ implements     │                      │ implements           │
│ Reportable     │                      │ Reportable,           │
└────────────────┘                      │ Discountable          │
                                          └────────────────────┘

InventoryManager  ──uses──► FileManager (persistence)
Main (UI)         ──uses──► InventoryManager, ReportService, AutoSaveService
ReportService      ──uses──► ExecutorService (thread pool) + CsvReportExporter
AutoSaveService     ──runs on──► background Thread, calls InventoryManager.persistAll()
```

---

## ⚙️ 7. How to Build & Run

### Option A — Build and run with Maven
```bash
mvn clean package
java -jar target/inventory-management-system-1.0-SNAPSHOT.jar
```

### Option B — Compile manually (no Maven required)
```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
cd out
java com.example.inventory.Main
```

Data files (`products.dat`, `categories.dat`) and reports
(`inventory_report.csv`) are created in the current working directory
when the program runs.

---

## 🖥️ 8. Menu Structure

```
MAIN MENU
1. Product Management
   1. Add Product        4. Update Product
   2. View Products       5. Delete Product
   3. Search Product      6. Apply Discount
                           7. Back
2. Category Management
   1. Add Category        3. Update Category
   2. View Categories     4. Delete Category
                           5. Back
3. Stock Management
   1. Add Stock            3. View Products
   2. Remove Stock         4. Back
4. Reports
   1. Low Stock Report
   2. Inventory Value Report (multithreaded)
   3. Export Products to CSV
                           4. Back
5. Exit
```

---

## 🔮 9. Possible Future Enhancements

- Migrate to a **Spring MVC** web front-end (the concept is already covered
  separately in this repository's `spring-mvc` module)
- Move persistence from serialized `.dat` files to a relational database
- Add JUnit test cases for `InventoryManager`
- Add user authentication/roles (admin vs. staff)

---

## 👤 Submission Details

| Field            | Value                          |
|-------------------|---------------------------------|
| Project Title      | Inventory Management System    |
| Language           | Java                            |
| Institution        | *(fill in college name)*        |
| Course             | *(fill in course name)*         |
| Student Name       | *(fill in your name)*           |
| Roll No. / Reg No. | *(fill in)*                     |
| Submitted To       | *(fill in faculty name)*        |
| Date               | *(fill in submission date)*     |
