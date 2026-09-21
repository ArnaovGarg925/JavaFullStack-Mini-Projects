package com.campus.lostfound;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Path file;

    public UserRepository(String fileName) {
        file = Paths.get(fileName);
        initialize();
    }

    private void initialize() {
        try {
            if (Files.notExists(file)) {
                Files.createDirectories(file.getParent() == null ? Paths.get(".") : file.getParent());
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to initialize users file", e);
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 5) continue;
                users.add(new User(p[0], p[1], p[2], p[3], Boolean.parseBoolean(p[4])));
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read users", e);
        }
    }

    public void save(User user) {
        List<User> users = findAll();
        boolean replaced = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                replaced = true;
                break;
            }
        }
        if (!replaced) users.add(user);

        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(String.join("|", clean(u.getId()), clean(u.getName()),
                    clean(u.getEmail()), clean(u.getPassword()),
                    String.valueOf(u.isAdmin())));
        }
        try {
            Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save user", e);
        }
    }

    private String clean(String s) {
        return s == null ? "" : s.replace("|", "/").replace("\n", " ");
    }
}
