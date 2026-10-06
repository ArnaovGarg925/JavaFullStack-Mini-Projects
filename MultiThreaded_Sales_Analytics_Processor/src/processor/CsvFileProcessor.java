package processor;

import config.ProcessorConfig;
import model.FileProcessingResult;
import model.SalesRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/** Worker task: one thread processes one CSV file. */
public class CsvFileProcessor implements Callable<FileProcessingResult> {
    private final String filePath;
    private final ProcessorConfig config;

    public CsvFileProcessor(String filePath, ProcessorConfig config) {
        this.filePath = filePath;
        this.config = config;
    }

    @Override
    public FileProcessingResult call() throws Exception {
        long start = System.currentTimeMillis();
        List<SalesRecord> records = new ArrayList<>();
        int invalidRows = 0;

        String thread = Thread.currentThread().getName();
        System.out.println("[" + thread + "] Started: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine && config.isSkipHeader()) {
                    firstLine = false;
                    continue;
                }
                firstLine = false;
                line = line.trim();
                if (line.isEmpty()) continue;

                SalesRecord record = parseLine(line);
                if (record != null) records.add(record);
                else invalidRows++;
            }
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[" + thread + "] Finished: " + filePath
                + " | valid=" + records.size()
                + " | invalid=" + invalidRows
                + " | time=" + elapsed + " ms");

        return new FileProcessingResult(new java.io.File(filePath).getName(), records, invalidRows, elapsed);
    }

    private SalesRecord parseLine(String line) {
        try {
            String[] parts = line.split(String.valueOf(config.getDelimiter()), -1);
            if (parts.length < 4) {
                System.err.println("  [WARN] Skipping malformed row: " + line);
                return null;
            }

            String product = parts[0].trim();
            String category = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());
            double price = Double.parseDouble(parts[3].trim());

            if (product.isEmpty() || category.isEmpty() || quantity < 0 || price < 0) {
                System.err.println("  [WARN] Invalid values: " + line);
                return null;
            }
            return new SalesRecord(product, category, quantity, price);
        } catch (NumberFormatException e) {
            System.err.println("  [WARN] Invalid numeric value: " + line);
            return null;
        }
    }
}
