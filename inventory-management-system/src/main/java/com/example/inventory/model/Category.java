package com.example.inventory.model;

import com.example.inventory.interfaces.Reportable;

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
