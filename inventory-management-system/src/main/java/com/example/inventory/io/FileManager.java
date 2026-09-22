package com.example.inventory.io;

import com.example.inventory.model.Category;
import com.example.inventory.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager
 * ---------------------------------------------------------
 * Handles binary persistence of products and categories using
 * Java object serialization (ObjectOutputStream/ObjectInputStream).
 * Both {@link com.example.inventory.model.Product} and
 * {@link com.example.inventory.model.Category} implement
 * Serializable (via the abstract InventoryItem class) so they
 * can be written to / read from products.dat and categories.dat.
 *
 * All methods are `synchronized` because this class is called
 * both from the main thread (every add/update/delete) and from
 * the background AutoSaveService thread.
 */
public class FileManager {

    private static final String PRODUCT_FILE = "products.dat";
    private static final String CATEGORY_FILE = "categories.dat";

    // Save products
    public static synchronized void saveProducts(List<Product> products) {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(PRODUCT_FILE))) {

            output.writeObject(products);

        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    // Load products
    @SuppressWarnings("unchecked")
    public static synchronized List<Product> loadProducts() {

        File file = new File(PRODUCT_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(PRODUCT_FILE))) {

            return (List<Product>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading products: " + e.getMessage());

            return new ArrayList<>();
        }
    }

    // Save categories
    public static synchronized void saveCategories(List<Category> categories) {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(CATEGORY_FILE))) {

            output.writeObject(categories);

        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    // Load categories
    @SuppressWarnings("unchecked")
    public static synchronized List<Category> loadCategories() {

        File file = new File(CATEGORY_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(CATEGORY_FILE))) {

            return (List<Category>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading categories: " + e.getMessage());

            return new ArrayList<>();
        }
    }
}
