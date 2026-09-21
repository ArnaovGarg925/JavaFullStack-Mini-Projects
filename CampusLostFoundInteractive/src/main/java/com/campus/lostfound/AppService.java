package com.campus.lostfound;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppService {
    private final Repository<Item> itemRepository;
    private final UserRepository userRepository;

    public AppService(Repository<Item> itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        seedAdmin();
    }

    private void seedAdmin() {
        boolean exists = userRepository.findAll().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase("admin@campus.com"));
        if (!exists) {
            userRepository.save(new User(
                "U-ADMIN", "Administrator", "admin@campus.com", "admin123", true
            ));
        }
    }

    public User register(String name, String email, String password) {
        if (name == null || name.isBlank() || email == null || email.isBlank()
                || password == null || password.length() < 4) {
            throw new IllegalArgumentException("Enter valid details. Password must be at least 4 characters.");
        }
        if (userRepository.findAll().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email.trim()))) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
        User user = new User(
            "U-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            name.trim(), email.trim(), password, false
        );
        userRepository.save(user);
        return user;
    }

    public User login(String email, String password) {
        return userRepository.findAll().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email.trim())
                    && u.getPassword().equals(password))
            .findFirst().orElse(null);
    }

    public List<User> users() {
        return userRepository.findAll();
    }

    public List<Item> items() {
        return itemRepository.findAll();
    }

    public List<Item> items(ReportType type, String query) {
        String q = query == null ? "" : query.trim().toLowerCase();
        List<Item> result = new ArrayList<>();
        for (Item item : itemRepository.findAll()) {
            boolean typeOk = item.getReportType() == type;
            boolean queryOk = q.isEmpty()
                || item.getTitle().toLowerCase().contains(q)
                || item.getCategory().toLowerCase().contains(q)
                || item.getLocation().toLowerCase().contains(q)
                || item.getDescription().toLowerCase().contains(q);
            if (typeOk && queryOk) result.add(item);
        }
        return result;
    }

    public Item addItem(String title, String category, String location, String date,
                         String description, ReportType type, String ownerId) {
        if (title.isBlank() || category.isBlank() || location.isBlank()) {
            throw new IllegalArgumentException("Title, category and location are required.");
        }
        Item item = new Item(
            "I-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            title.trim(), category.trim(), location.trim(),
            date == null || date.isBlank() ? LocalDate.now().toString() : date.trim(),
            description == null ? "" : description.trim(),
            type, ItemStatus.ACTIVE, ownerId
        );
        itemRepository.save(item);
        return item;
    }

    public void updateItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(String id, User currentUser) {
        Item item = items().stream()
            .filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (item == null) return;
        if (!currentUser.isAdmin() && !item.getOwnerId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You can only delete your own reports.");
        }
        itemRepository.delete(id);
    }

    public void claimItem(Item item) {
        if (item.getReportType() != ReportType.FOUND) {
            throw new IllegalArgumentException("Claims can be submitted for found items.");
        }
        if (item.getStatus() != ItemStatus.ACTIVE) {
            throw new IllegalArgumentException("This item is no longer available for a claim.");
        }
        item.setStatus(ItemStatus.CLAIMED);
        itemRepository.save(item);
    }
}
