# MultiThreaded Sales Analytics Processor

A Java-based application for processing multiple CSV sales files concurrently and generating consolidated sales analytics.

## Overview

The application uses Java multithreading to process multiple sales CSV files at the same time. The processed records are aggregated into a single sales analysis report.

The project demonstrates practical use of:

- Java Multithreading
- ExecutorService
- Callable and Future
- CSV file processing
- Data validation
- Sales aggregation
- File-based reporting
- Builder Pattern

## Features

- Processes multiple CSV files concurrently
- Uses a configurable thread pool
- Validates sales records while processing
- Tracks valid and invalid records
- Calculates total quantity and revenue
- Calculates average revenue per record
- Provides category-wise sales and revenue analysis
- Identifies the top-selling product
- Displays the top 3 products by quantity sold
- Measures individual file processing time
- Measures total processing time
- Generates a detailed text report
- Exports processed records to CSV
- Handles executor shutdown safely

## Project Structure

```text
MultiThreadedFileProcessor/
├── data/
│   ├── sales_january.csv
│   ├── sales_february.csv
│   └── sales_march.csv
├── src/
│   ├── config/
│   │   └── ProcessorConfig.java
│   ├── model/
│   │   ├── SalesRecord.java
│   │   └── FileProcessingResult.java
│   ├── processor/
│   │   └── CsvFileProcessor.java
│   ├── report/
│   │   └── ReportAggregator.java
│   └── Main.java
├── run.bat
└── README.md
```

## Technologies Used

- Java
- Java Collections Framework
- Java Concurrency API
- ExecutorService
- Callable / Future
- File I/O
- CSV processing
- Object-Oriented Programming
- Builder Design Pattern

## How It Works

1. The application locates the CSV files in the `data` directory.
2. A thread pool is created for concurrent processing.
3. Each CSV file is assigned to a processing task.
4. The tasks read and validate sales records concurrently.
5. The results from all tasks are collected using `Future` objects.
6. The records are combined and analyzed.
7. Sales statistics and performance information are generated.
8. The final results are written to report files.

## Sample Input

Each CSV file follows this format:

```csv
Product,Category,Quantity,Price
Laptop,Electronics,5,75000
Mouse,Accessories,20,500
Keyboard,Accessories,10,1200
```

## Output

After execution, the application generates:

- `report.txt` — consolidated sales and processing report
- `processed_sales.csv` — processed sales data in CSV format

## Running the Project

### Option 1: Windows

Run:

```text
run.bat
```

### Option 2: Command Line

From the project directory:

```bash
javac -d out src/config/*.java src/model/*.java src/processor/*.java src/report/*.java src/Main.java
java -cp out Main
```

## Example Analytics

The report includes information such as:

- Number of files processed
- Number of valid and invalid records
- Total quantity sold
- Total revenue
- Average revenue
- Revenue by category
- Quantity by category
- Top-selling product
- Top 3 products
- Processing time for each file
- Overall processing time

## Learning Outcomes

This project demonstrates how Java can be used to process independent data sources concurrently and combine their results into meaningful analytics. It also provides practical experience with thread pools, task management, file handling, validation, collections, and report generation.
