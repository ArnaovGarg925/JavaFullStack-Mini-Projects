package com.example.inventory.io;

import com.example.inventory.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * CsvReportExporter
 * ---------------------------------------------------------
 * Writes the current product list out as a CSV file, the same
 * kind of CSV report format used in
 * "Module Project/Module - 2/MultiThreadedFileProcessor"
 * (sales_january.csv, sales_february.csv, report.txt, etc).
 */
public class CsvReportExporter {

    public static void exportProducts(List<Product> products, String fileName) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("ID,Name,Category,Price,Stock,StockValue");

            for (Product product : products) {
                writer.printf(
                        "%d,%s,%s,%.2f,%d,%.2f%n",
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getPrice(),
                        product.getStock(),
                        product.getStockValue()
                );
            }

            System.out.println("CSV report exported to: " + fileName);

        } catch (IOException e) {
            System.out.println("Error exporting CSV report: " + e.getMessage());
        }
    }
}
