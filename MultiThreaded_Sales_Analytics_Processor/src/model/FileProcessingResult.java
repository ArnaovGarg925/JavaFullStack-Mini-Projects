package model;

import java.util.Collections;
import java.util.List;

/** Result returned by one worker thread after processing a CSV file. */
public class FileProcessingResult {
    private final String fileName;
    private final List<SalesRecord> records;
    private final int invalidRows;
    private final long processingTimeMs;

    public FileProcessingResult(String fileName, List<SalesRecord> records, int invalidRows, long processingTimeMs) {
        this.fileName = fileName;
        this.records = records;
        this.invalidRows = invalidRows;
        this.processingTimeMs = processingTimeMs;
    }

    public String getFileName() { return fileName; }
    public List<SalesRecord> getRecords() { return Collections.unmodifiableList(records); }
    public int getInvalidRows() { return invalidRows; }
    public long getProcessingTimeMs() { return processingTimeMs; }
}
