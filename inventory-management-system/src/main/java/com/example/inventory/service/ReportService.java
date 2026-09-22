package com.example.inventory.service;

import com.example.inventory.io.CsvReportExporter;
import com.example.inventory.manager.InventoryManager;
import com.example.inventory.model.Product;

import java.util.*;
import java.util.concurrent.*;

/**
 * ReportService
 * ---------------------------------------------------------
 * Generates the "Inventory Value Report" using a thread pool:
 * one Callable task runs per category, computing that category's
 * item count and total stock value concurrently. The results are
 * then aggregated on the main thread - the same worker-pool +
 * aggregator pattern used by CsvFileProcessor / ReportAggregator
 * in "Module - 2/MultiThreadedFileProcessor".
 */
public class ReportService {

    private final InventoryManager manager;

    public ReportService(InventoryManager manager) {
        this.manager = manager;
    }

    /**
     * One task's result: totals for a single category.
     */
    private static class CategoryTotal {
        final String category;
        final int itemCount;
        final double totalValue;

        CategoryTotal(String category, int itemCount, double totalValue) {
            this.category = category;
            this.itemCount = itemCount;
            this.totalValue = totalValue;
        }
    }

    public void generateValueReport() {

        List<Product> products = manager.getProductsSnapshot();

        if (products.isEmpty()) {
            System.out.println("\nNo products available to report on.");
            return;
        }

        // Distinct category names present in the current product list
        Set<String> categoryNames = new TreeSet<>();
        for (Product p : products) {
            categoryNames.add(p.getCategory());
        }

        int threadCount = Math.min(categoryNames.size(), 4);
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(threadCount, 1));

        List<Future<CategoryTotal>> futures = new ArrayList<>();

        for (String category : categoryNames) {
            Callable<CategoryTotal> task = () -> {
                int count = 0;
                double value = 0.0;
                for (Product p : products) {
                    if (p.getCategory().equalsIgnoreCase(category)) {
                        count++;
                        value += p.getStockValue();
                    }
                }
                return new CategoryTotal(category, count, value);
            };
            futures.add(executor.submit(task));
        }

        System.out.println("\n============== INVENTORY VALUE REPORT ==============");
        System.out.printf("%-20s %10s %15s%n", "Category", "Items", "Total Value");
        System.out.println("-----------------------------------------------------");

        double grandTotal = 0.0;
        int grandCount = 0;

        try {
            for (Future<CategoryTotal> future : futures) {
                CategoryTotal result = future.get(5, TimeUnit.SECONDS);
                System.out.printf("%-20s %10d %15.2f%n",
                        result.category, result.itemCount, result.totalValue);
                grandTotal += result.totalValue;
                grandCount += result.itemCount;
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Error while aggregating report: " + e.getMessage());
        } finally {
            executor.shutdown();
        }

        System.out.println("-----------------------------------------------------");
        System.out.printf("%-20s %10d %15.2f%n", "GRAND TOTAL", grandCount, grandTotal);
        System.out.println("=====================================================");
    }

    /**
     * Exports the current products to CSV (see CsvReportExporter),
     * using the same "Module - 2" CSV report format as the class
     * content examples.
     */
    public void exportCsvReport() {
        List<Product> products = manager.getProductsSnapshot();
        String fileName = "inventory_report.csv";
        CsvReportExporter.exportProducts(products, fileName);
    }
}
