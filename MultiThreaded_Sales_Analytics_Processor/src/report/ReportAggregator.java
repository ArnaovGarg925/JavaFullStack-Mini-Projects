package report;

import model.FileProcessingResult;
import model.SalesRecord;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/** Aggregates results from all worker threads and creates text + CSV reports. */
public class ReportAggregator {
    private final List<SalesRecord> allRecords = new ArrayList<>();
    private final List<FileProcessingResult> fileResults = new ArrayList<>();

    public void addResult(FileProcessingResult result) {
        fileResults.add(result);
        allRecords.addAll(result.getRecords());
    }

    public void generateReport(String outputFilePath, String csvOutputPath, long totalTimeMs, int threadCount) {
        int totalRecords = allRecords.size();
        int totalQuantity = 0;
        double totalRevenue = 0;
        int invalidRows = fileResults.stream().mapToInt(FileProcessingResult::getInvalidRows).sum();

        Map<String, Double> revenueByCategory = new TreeMap<>();
        Map<String, Integer> quantityByCategory = new TreeMap<>();
        Map<String, Integer> quantityByProduct = new HashMap<>();

        for (SalesRecord r : allRecords) {
            totalQuantity += r.getQuantity();
            totalRevenue += r.getTotalRevenue();
            revenueByCategory.merge(r.getCategory(), r.getTotalRevenue(), Double::sum);
            quantityByCategory.merge(r.getCategory(), r.getQuantity(), Integer::sum);
            quantityByProduct.merge(r.getProduct(), r.getQuantity(), Integer::sum);
        }

        List<Map.Entry<String, Integer>> topProducts = quantityByProduct.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        String topCategory = revenueByCategory.entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");

        String separator = "=".repeat(64);
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(separator).append("\n");
        sb.append("          MULTI-THREADED SALES ANALYTICS REPORT\n");
        sb.append(separator).append("\n\n");
        sb.append(String.format("  Worker Threads Used      : %d%n", threadCount));
        sb.append(String.format("  Files Processed          : %d%n", fileResults.size()));
        sb.append(String.format("  Valid Records            : %,d%n", totalRecords));
        sb.append(String.format("  Invalid Rows Skipped     : %,d%n", invalidRows));
        sb.append(String.format("  Total Quantity Sold      : %,d units%n", totalQuantity));
        sb.append(String.format("  Total Revenue            : $%,.2f%n", totalRevenue));
        sb.append(String.format("  Average Revenue/Record   : $%,.2f%n", totalRecords > 0 ? totalRevenue / totalRecords : 0));
        sb.append(String.format("  Total Processing Time    : %,d ms%n", totalTimeMs));
        sb.append(String.format("  Top Revenue Category     : %s%n", topCategory));

        sb.append("\n").append(separator).append("\nTOP 3 PRODUCTS BY QUANTITY\n").append(separator).append("\n");
        if (topProducts.isEmpty()) sb.append("  No product data available.\n");
        else {
            int rank = 1;
            for (Map.Entry<String, Integer> e : topProducts) {
                sb.append(String.format("  %d. %-25s %,d units%n", rank++, e.getKey(), e.getValue()));
            }
        }

        sb.append("\n").append(separator).append("\nREVENUE BY CATEGORY\n").append(separator).append("\n");
        revenueByCategory.forEach((cat, rev) -> sb.append(String.format("  %-22s : $%,12.2f%n", cat, rev)));

        sb.append("\n").append(separator).append("\nFILE PROCESSING DETAILS\n").append(separator).append("\n");
        for (FileProcessingResult r : fileResults) {
            sb.append(String.format("  %-25s | valid=%-4d invalid=%-3d time=%d ms%n",
                    r.getFileName(), r.getRecords().size(), r.getInvalidRows(), r.getProcessingTimeMs()));
        }
        sb.append("\n").append(separator).append("\n");

        System.out.println(sb);
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            writer.print(sb);
            System.out.println("Text report saved to: " + outputFilePath);
        } catch (Exception e) {
            System.err.println("Could not write text report: " + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvOutputPath))) {
            writer.println("Product,Category,Quantity,UnitPrice,Revenue");
            for (SalesRecord r : allRecords) {
                writer.printf(Locale.US, "\"%s\",\"%s\",%d,%.2f,%.2f%n",
                        r.getProduct().replace("\"", "\"\""), r.getCategory().replace("\"", "\"\""),
                        r.getQuantity(), r.getPrice(), r.getTotalRevenue());
            }
            System.out.println("Detailed CSV saved to: " + csvOutputPath);
        } catch (Exception e) {
            System.err.println("Could not write CSV report: " + e.getMessage());
        }
    }
}
