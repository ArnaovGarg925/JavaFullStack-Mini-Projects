import config.ProcessorConfig;
import model.FileProcessingResult;
import processor.CsvFileProcessor;
import report.ReportAggregator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/** Entry point for the enhanced Multi-Threaded CSV Sales Processor. */
public class Main {
    public static void main(String[] args) {
        long programStart = System.currentTimeMillis();

        System.out.println("============================================================");
        System.out.println("       MULTI-THREADED CSV SALES ANALYTICS PROCESSOR");
        System.out.println("============================================================\n");

        int availableCores = Runtime.getRuntime().availableProcessors();
        int threadCount = Math.max(2, Math.min(availableCores, 8));

        ProcessorConfig config = new ProcessorConfig.Builder()
                .threadCount(threadCount)
                .inputFolder("data/")
                .outputFile("report.txt")
                .csvOutputFile("processed_sales.csv")
                .skipHeader(true)
                .delimiter(',')
                .build();

        System.out.println("Configuration: " + config);
        System.out.println("Available CPU cores: " + availableCores);
        System.out.println();

        File folder = new File(config.getInputFolder());
        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            System.err.println("No CSV files found in: " + config.getInputFolder());
            return;
        }

        System.out.println("Found " + csvFiles.length + " CSV file(s):");
        for (File file : csvFiles) System.out.println("  -> " + file.getName());
        System.out.println("\nProcessing files concurrently...\n");

        ExecutorService executor = Executors.newFixedThreadPool(config.getThreadCount());
        List<Future<FileProcessingResult>> futures = new ArrayList<>();

        for (File csvFile : csvFiles) {
            futures.add(executor.submit(new CsvFileProcessor(csvFile.getPath(), config)));
        }

        ReportAggregator aggregator = new ReportAggregator();
        for (Future<FileProcessingResult> future : futures) {
            try {
                aggregator.addResult(future.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Main thread interrupted.");
                break;
            } catch (ExecutionException e) {
                System.err.println("Worker failed: " + e.getCause());
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        long totalTime = System.currentTimeMillis() - programStart;
        aggregator.generateReport(config.getOutputFile(), config.getCsvOutputFile(), totalTime, config.getThreadCount());
        System.out.println("\nCompleted in " + totalTime + " ms.");
        System.out.println("Enhanced features: automatic thread sizing, validation, timing, Top-3 analytics, CSV export.");
    }
}
