package com.example.inventory.interfaces;

/**
 * Discountable
 * ---------------------------------------------------------
 * Implemented by items that support a percentage discount.
 * Demonstrates multiple-interface implementation (a class can
 * implement both Reportable and Discountable at once), which
 * is one of the key points taught in the Interface in Java
 * class content.
 */
public interface Discountable {

    /**
     * Applies a percentage discount to the item's price.
     *
     * @param percent discount percentage (0-100)
     * @return the new price after discount
     */
    double applyDiscount(double percent);
}
