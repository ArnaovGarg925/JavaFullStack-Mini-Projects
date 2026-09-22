package com.example.inventory.interfaces;

/**
 * Reportable
 * ---------------------------------------------------------
 * Any class that can produce a short, human-readable report
 * line implements this. This is the same "Interface in Java"
 * concept covered in Class Content -> "2 - Interface in Java.pdf".
 */
public interface Reportable {

    /**
     * @return a formatted, one-line (or multi-line) report summary.
     */
    String generateReport();
}
