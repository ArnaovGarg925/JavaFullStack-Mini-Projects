package com.example.inventory.manager;

import com.example.inventory.io.FileManager;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * InventoryManager
 * ---------------------------------------------------------
 * The core business-logic (service) layer of the application.
 * Main.java (the UI/menu layer) never touches FileManager or the
 * raw product/category lists directly - it always goes through
 * this class, keeping responsibilities cleanly separated.
 *
 * Thread safety: several public methods are declared `synchronized`
 * because InventoryManager's data is shared between the main menu
 * thread and the background AutoSaveService thread (see the
 * `service` package). Synchronizing here prevents two threads from
 * modifying/reading the lists at the same time.
 */
public class InventoryManager {

    private final List<Product> products;
    private final List<Category> categories;

    public InventoryManager() {

        products = FileManager.loadProducts();
        categories = FileManager.loadCategories();
    }

    // =========================================================
    // PRODUCT OPERATIONS
    // =========================================================

    // Add Product
    public synchronized boolean addProduct(Product product) {

        if (findProductById(product.getId()) != null) {
            return false;
        }

        products.add(product);

        FileManager.saveProducts(products);

        return true;
    }

    // View All Products
    public void displayProducts() {

        if (products.isEmpty()) {

            System.out.println("\nNo products available.");

            return;
        }

        System.out.println("\n---------------- PRODUCT LIST ----------------");

        for (Product product : products) {
            System.out.println(product);
        }

        System.out.println("----------------------------------------------");
    }

    // Find Product
    public synchronized Product findProductById(int id) {

        for (Product product : products) {

            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    // Search Product by Name
    public void searchProduct(String name) {

        boolean found = false;

        for (Product product : products) {

            if (product.getName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {

                System.out.println(product);

                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found.");
        }
    }

    // Update Product
    public synchronized boolean updateProduct(
            int id,
            String name,
            String category,
            double price) {

        Product product = findProductById(id);

        if (product == null) {
            return false;
        }

        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);

        FileManager.saveProducts(products);

        return true;
    }

    // Delete Product
    public synchronized boolean deleteProduct(int id) {

        Product product = findProductById(id);

        if (product == null) {
            return false;
        }

        products.remove(product);

        FileManager.saveProducts(products);

        return true;
    }

    // Apply a discount to a product (uses the Discountable interface)
    public synchronized Double applyDiscountToProduct(int id, double percent) {

        Product product = findProductById(id);

        if (product == null) {
            return null;
        }

        double newPrice = product.applyDiscount(percent);

        FileManager.saveProducts(products);

        return newPrice;
    }

    // =========================================================
    // STOCK OPERATIONS
    // =========================================================

    // Add Stock
    public synchronized boolean addStock(int productId, int quantity) {

        Product product = findProductById(productId);

        if (product == null || quantity <= 0) {
            return false;
        }

        product.setStock(product.getStock() + quantity);

        FileManager.saveProducts(products);

        return true;
    }

    // Remove Stock
    public synchronized boolean removeStock(int productId, int quantity) {

        Product product = findProductById(productId);

        if (product == null || quantity <= 0) {
            return false;
        }

        if (product.getStock() < quantity) {
            return false;
        }

        product.setStock(product.getStock() - quantity);

        FileManager.saveProducts(products);

        return true;
    }

    // Display Low Stock Products
    public void displayLowStock(int limit) {

        boolean found = false;

        System.out.println("\n------------- LOW STOCK PRODUCTS -------------");

        for (Product product : products) {

            if (product.getStock() <= limit) {

                System.out.println(product);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No low-stock products.");
        }

        System.out.println("----------------------------------------------");
    }

    // =========================================================
    // CATEGORY OPERATIONS
    // =========================================================

    // Add Category
    public synchronized boolean addCategory(Category category) {

        if (findCategoryById(category.getId()) != null) {
            return false;
        }

        categories.add(category);

        FileManager.saveCategories(categories);

        return true;
    }

    // View Categories
    public void displayCategories() {

        if (categories.isEmpty()) {

            System.out.println("\nNo categories available.");

            return;
        }

        System.out.println("\n-------------- CATEGORY LIST ---------------");

        for (Category category : categories) {
            System.out.println(category);
        }

        System.out.println("---------------------------------------------");
    }

    // Find Category
    public synchronized Category findCategoryById(int id) {

        for (Category category : categories) {

            if (category.getId() == id) {
                return category;
            }
        }

        return null;
    }

    // Update Category
    public synchronized boolean updateCategory(int id, String name) {

        Category category = findCategoryById(id);

        if (category == null) {
            return false;
        }

        category.setName(name);

        FileManager.saveCategories(categories);

        return true;
    }

    // Delete Category
    public synchronized boolean deleteCategory(int id) {

        Category category = findCategoryById(id);

        if (category == null) {
            return false;
        }

        // Check whether category is used by any product
        for (Product product : products) {

            if (product.getCategory()
                    .equalsIgnoreCase(category.getName())) {

                return false;
            }
        }

        categories.remove(category);

        FileManager.saveCategories(categories);

        return true;
    }

    // =========================================================
    // UTILITY / ACCESSOR METHODS
    // =========================================================

    public int getNextProductId() {

        int maxId = 0;

        for (Product product : products) {

            if (product.getId() > maxId) {
                maxId = product.getId();
            }
        }

        return maxId + 1;
    }

    public int getNextCategoryId() {

        int maxId = 0;

        for (Category category : categories) {

            if (category.getId() > maxId) {
                maxId = category.getId();
            }
        }

        return maxId + 1;
    }

    /**
     * Read-only snapshot of the product list, used by the
     * multithreaded ReportService and CsvReportExporter so those
     * classes never mutate InventoryManager's internal state directly.
     */
    public synchronized List<Product> getProductsSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    public synchronized List<Category> getCategoriesSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(categories));
    }

    /**
     * Force-persist current in-memory state. Used by the
     * background AutoSaveService thread.
     */
    public synchronized void persistAll() {
        FileManager.saveProducts(products);
        FileManager.saveCategories(categories);
    }
}
