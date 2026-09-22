package com.example.inventory.model;

import com.example.inventory.interfaces.Discountable;
import com.example.inventory.interfaces.Reportable;

public class Product extends InventoryItem implements Reportable, Discountable {

    private static final long serialVersionUID = 1L;

    private String category;
    private double price;
    private int stock;

    public Product(int id, String name, String category, double price, int stock) {
        super(id, name);
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Getters

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setters

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Total value of this product currently held in stock.
     * Used by the multithreaded report service.
     */
    public double getStockValue() {
        return price * stock;
    }

    @Override
    public String getDetails() {
        return String.format(
                "ID: %d | Name: %s | Category: %s | Price: %.2f | Stock: %d",
                id, name, category, price, stock
        );
    }

    @Override
    public String generateReport() {
        return String.format(
                "[Product] #%d %-15s | Category: %-12s | Price: %8.2f | Stock: %4d | Value: %10.2f",
                id, name, category, price, stock, getStockValue()
        );
    }

    /**
     * Discountable implementation: reduces the price by the given
     * percentage and returns the new price. Rejects invalid ranges.
     */
    @Override
    public double applyDiscount(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
        this.price = this.price - (this.price * percent / 100.0);
        return this.price;
    }
}
