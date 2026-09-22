package com.example.inventory;

import com.example.inventory.manager.InventoryManager;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.service.AutoSaveService;
import com.example.inventory.service.ReportService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final InventoryManager manager = new InventoryManager();
    private static final ReportService reportService = new ReportService(manager);

    // Background thread: auto-saves inventory to disk every 30 seconds
    private static final AutoSaveService autoSaveService =
            new AutoSaveService(manager, 30_000);

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("     INVENTORY MANAGEMENT SYSTEM");
        System.out.println("==========================================");

        autoSaveService.start();

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    productMenu();
                    break;

                case 2:
                    categoryMenu();
                    break;

                case 3:
                    stockMenu();
                    break;

                case 4:
                    reportsMenu();
                    break;

                case 5:
                    System.out.println("\nThank you for using");
                    System.out.println("Inventory Management System.");

                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }
        }

        autoSaveService.stop();
        manager.persistAll();
        scanner.close();
    }

    // =========================================================
    // MAIN MENU
    // =========================================================

    private static void displayMainMenu() {
        System.out.println("\n==========================================");
        System.out.println("              MAIN MENU");
        System.out.println("==========================================");

        System.out.println("1. Product Management");
        System.out.println("2. Category Management");
        System.out.println("3. Stock Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");

        System.out.println("==========================================");
    }

    // =========================================================
    // PRODUCT MENU
    // =========================================================

    private static void productMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n------------------------------------------");
            System.out.println("          PRODUCT MANAGEMENT");
            System.out.println("------------------------------------------");

            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Apply Discount");
            System.out.println("7. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;

                case 2:
                    manager.displayProducts();
                    break;

                case 3:
                    searchProduct();
                    break;

                case 4:
                    updateProduct();
                    break;

                case 5:
                    deleteProduct();
                    break;

                case 6:
                    applyDiscount();
                    break;

                case 7:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // ADD PRODUCT
    // =========================================================

    private static void addProduct() {
        System.out.println("\n----------- ADD PRODUCT -----------");

        int id = manager.getNextProductId();

        System.out.println("Product ID: " + id);

        String name = readString("Enter product name: ");

        String category = readString("Enter category: ");

        double price = readDouble("Enter price: ");

        int stock = readInt("Enter initial stock: ");

        if (price < 0 || stock < 0) {
            System.out.println("Price and stock cannot be negative.");
            return;
        }
        Product product = new Product(id, name, category, price, stock);

        if (manager.addProduct(product)) {
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Unable to add product.");
        }
    }

    // =========================================================
    // SEARCH PRODUCT
    // =========================================================

    private static void searchProduct() {
        System.out.println("\n----------- SEARCH PRODUCT -----------");
        String name = readString("Enter product name: ");
        manager.searchProduct(name);
    }

    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    private static void updateProduct() {
        System.out.println("\n----------- UPDATE PRODUCT -----------");
        int id = readInt("Enter product ID: ");
        Product product = manager.findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\nCurrent Product:");
        System.out.println(product);
        String name = readString("Enter new name: ");
        String category = readString("Enter new category: ");
        double price = readDouble("Enter new price: ");

        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }

        if (manager.updateProduct(id, name, category, price)) {
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Unable to update product.");
        }
    }

    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    private static void deleteProduct() {
        System.out.println("\n----------- DELETE PRODUCT -----------");
        int id = readInt("Enter product ID: ");
        Product product = manager.findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println(product);

        String confirmation = readString("Are you sure? (yes/no): ");

        if (confirmation.equalsIgnoreCase("yes")) {

            if (manager.deleteProduct(id)) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Unable to delete product.");
            }

        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    // =========================================================
    // APPLY DISCOUNT (uses the Discountable interface)
    // =========================================================

    private static void applyDiscount() {
        System.out.println("\n----------- APPLY DISCOUNT -----------");
        int id = readInt("Enter product ID: ");
        Product product = manager.findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Current price: " + product.getPrice());

        double percent = readDouble("Enter discount percentage (0-100): ");

        try {
            Double newPrice = manager.applyDiscountToProduct(id, percent);
            System.out.printf("New price after %.2f%% discount: %.2f%n", percent, newPrice);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid discount: " + e.getMessage());
        }
    }

    // =========================================================
    // CATEGORY MENU
    // =========================================================

    private static void categoryMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n------------------------------------------");
            System.out.println("          CATEGORY MANAGEMENT");
            System.out.println("------------------------------------------");

            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addCategory();
                    break;

                case 2:
                    manager.displayCategories();
                    break;

                case 3:
                    updateCategory();
                    break;

                case 4:
                    deleteCategory();
                    break;

                case 5:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // ADD CATEGORY
    // =========================================================

    private static void addCategory() {

        System.out.println("\n----------- ADD CATEGORY -----------");

        int id = manager.getNextCategoryId();

        System.out.println("Category ID: " + id);

        String name = readString("Enter category name: ");

        Category category = new Category(id, name);

        if (manager.addCategory(category)) {
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Category already exists.");
        }
    }

    // =========================================================
    // UPDATE CATEGORY
    // =========================================================

    private static void updateCategory() {

        System.out.println("\n----------- UPDATE CATEGORY -----------");

        int id = readInt("Enter category ID: ");

        Category category = manager.findCategoryById(id);

        if (category == null) {
            System.out.println("Category not found.");
            return;
        }

        System.out.println("Current category: " + category.getName());

        String name = readString("Enter new category name: ");

        if (manager.updateCategory(id, name)) {
            System.out.println("Category updated successfully.");
        } else {
            System.out.println("Unable to update category.");
        }
    }

    // =========================================================
    // DELETE CATEGORY
    // =========================================================

    private static void deleteCategory() {

        System.out.println("\n----------- DELETE CATEGORY -----------");

        int id = readInt("Enter category ID: ");

        Category category = manager.findCategoryById(id);

        if (category == null) {
            System.out.println("Category not found.");
            return;
        }

        System.out.println(category);

        String confirmation = readString("Are you sure? (yes/no): ");

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        if (manager.deleteCategory(id)) {
            System.out.println("Category deleted successfully.");
        } else {
            System.out.println("Cannot delete category.");
            System.out.println("It may be used by a product.");
        }
    }

    // =========================================================
    // STOCK MENU
    // =========================================================

    private static void stockMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n------------------------------------------");
            System.out.println("             STOCK MANAGEMENT");
            System.out.println("------------------------------------------");

            System.out.println("1. Add Stock");
            System.out.println("2. Remove Stock");
            System.out.println("3. View Products");
            System.out.println("4. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addStock();
                    break;

                case 2:
                    removeStock();
                    break;

                case 3:
                    manager.displayProducts();
                    break;

                case 4:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // ADD STOCK
    // =========================================================

    private static void addStock() {

        System.out.println("\n----------- ADD STOCK -----------");

        int id = readInt("Enter product ID: ");

        Product product = manager.findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt("Enter quantity to add: ");

        if (manager.addStock(id, quantity)) {
            System.out.println("Stock added successfully.");
            System.out.println("New stock: " + product.getStock());
        } else {
            System.out.println("Invalid quantity.");
        }
    }

    // =========================================================
    // REMOVE STOCK
    // =========================================================

    private static void removeStock() {

        System.out.println("\n----------- REMOVE STOCK -----------");

        int id = readInt("Enter product ID: ");

        Product product = manager.findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Current stock: " + product.getStock());

        int quantity = readInt("Enter quantity to remove: ");

        if (manager.removeStock(id, quantity)) {
            System.out.println("Stock removed successfully.");
            System.out.println("Remaining stock: " + product.getStock());
        } else {
            System.out.println("Unable to remove stock.");
            System.out.println("Check the quantity and available stock.");
        }
    }

    // =========================================================
    // REPORTS MENU (new - Low Stock, Multithreaded Value Report, CSV Export)
    // =========================================================

    private static void reportsMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n------------------------------------------");
            System.out.println("                 REPORTS");
            System.out.println("------------------------------------------");

            System.out.println("1. Low Stock Report");
            System.out.println("2. Inventory Value Report (multithreaded)");
            System.out.println("3. Export Products to CSV");
            System.out.println("4. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    int limit = readInt("Enter low stock threshold: ");
                    manager.displayLowStock(limit);
                    break;

                case 2:
                    reportService.generateValueReport();
                    break;

                case 3:
                    reportService.exportCsvReport();
                    break;

                case 4:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // INPUT METHODS
    // =========================================================

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private static String readString(String message) {

        while (true) {

            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Value cannot be empty.");
        }
    }
}
