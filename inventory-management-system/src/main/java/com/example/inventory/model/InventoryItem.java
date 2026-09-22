package com.example.inventory.model;

import java.io.Serializable;

/**
 * InventoryItem
 * ---------------------------------------------------------
 * Abstract base class shared by Product and Category.
 * Demonstrates the OOP concepts (abstraction + inheritance)
 * covered in Class Content -> "1 - Introduction to Java and
 * OOPs Concept.pdf": every inventory item has an id/name, and
 * subclasses must define their own getDetails() behaviour.
 */
public abstract class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int id;
    protected String name;

    protected InventoryItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstraction: each subclass decides how it describes itself.
     */
    public abstract String getDetails();

    @Override
    public String toString() {
        return getDetails();
    }
}
