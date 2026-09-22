# Inventory Management System (Enhanced)

This is your original console-based **Inventory Management System**
(`Module Project/Module - 1/Inventory Management System`), rebuilt as a
proper Maven project and extended with features that map directly to
other topics already in the `SRM-IST-main` repo — nothing outside it.

## What changed and why

| Feature added | Where it comes from in your repo |
|---|---|
| `Reportable` and `Discountable` **interfaces**, implemented by `Product`/`Category` | `Class Content/2 - Interface in Java.pdf` |
| Abstract `InventoryItem` base class (`Product`/`Category` now extend it) | `Class Content/1 - Introduction to Java and OOPs Concept.pdf` |
| Background **auto-save thread** (`AutoSaveService`) that persists data every 30s | `Module Project/Module - 2/MultiThreadedFileProcessor` |
| **Multithreaded value report** — one thread per category, aggregated on the main thread (`ReportService`) | Same `MultiThreadedFileProcessor` / `ReportAggregator` pattern |
| **CSV export** of the inventory (`CsvReportExporter` → `inventory_report.csv`) | Same CSV report style as `sales_january.csv` / `report.txt` in `MultiThreadedFileProcessor` |
| Converted to a **Maven project** with an executable jar | `Maven Project/calculator-executable-jar`, `Maven Project/first-app`, `Class Content/11 - Maven Spring Boot Setup.pdf` |
| Product discount (`Apply Discount` menu option) | Uses the new `Discountable` interface above |

## Project layout

```
inventory-management-system/
├── pom.xml
├── src/main/java/com/example/inventory/
│   ├── Main.java                     # menu-driven entry point
│   ├── model/
│   │   ├── InventoryItem.java        # abstract base class
│   │   ├── Product.java              # extends InventoryItem, implements Reportable, Discountable
│   │   └── Category.java             # extends InventoryItem, implements Reportable
│   ├── interfaces/
│   │   ├── Reportable.java
│   │   └── Discountable.java
│   ├── manager/
│   │   └── InventoryManager.java     # core business logic (thread-safe)
│   ├── io/
│   │   ├── FileManager.java          # binary persistence (same as original)
│   │   └── CsvReportExporter.java    # new: CSV export
│   └── service/
│       ├── AutoSaveService.java      # new: background auto-save thread
│       └── ReportService.java        # new: multithreaded value report + CSV trigger
└── target/inventory-management-system-1.0-SNAPSHOT.jar   # prebuilt executable jar
```

## Menu structure

```
MAIN MENU
1. Product Management        -> Add / View / Search / Update / Delete / Apply Discount / Back
2. Category Management        -> Add / View / Update / Delete / Back
3. Stock Management            -> Add Stock / Remove Stock / View / Back
4. Reports                       -> Low Stock / Inventory Value Report (multithreaded) / Export CSV / Back
5. Exit
```

## Build & run

### With Maven
```bash
mvn clean package
java -jar target/inventory-management-system-1.0-SNAPSHOT.jar
```

### Without Maven (already compiled here)
```bash
java -jar target/inventory-management-system-1.0-SNAPSHOT.jar
```

Data is saved to `products.dat` / `categories.dat` (same serialized format as
before) in the working directory, plus `inventory_report.csv` when you use
the CSV export option.
