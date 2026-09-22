package com.example.inventory.model;

import com.example.inventory.interfaces.Reportable;

/**
 * Category
 * ---------------------------------------------------------
 * Represents a product category (e.g. "Electronics", "Groceries").
 *
 * OOP concepts demonstrated:
 *  - Inheritance : extends the abstract {@link InventoryItem} class.
 *  - Interfaces  : implements {@link Reportable}.
 *  - Polymorphism: overrides getDetails() and generateReport() with
 *                  its own category-specific formatting.
 */
public class Category extends InventoryItem implements Reportable {

    private static final long serialVersionUID = 1L;

    public Category(int id, String name) {
        super(id, name);
    }

    @Override
    public String getDetails() {
        return "ID: " + id + " | Category: " + name;
    }

    @Override
    public String generateReport() {
        return "[Category] #" + id + " - " + name;
    }
}
