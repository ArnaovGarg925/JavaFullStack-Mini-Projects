package com.campus.lostfound;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements Repository<Item> {
    private final Path file;

    public FileRepository(String fileName) {
        this.file = Paths.get(fileName);
        initialize();
    }

    private void initialize() {
        try {
            if (Files.notExists(file)) {
                Files.createDirectories(file.getParent() == null ? Paths.get(".") : file.getParent());
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to initialize data file: " + file, e);
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 9) continue;
                items.add(new Item(
                    p[0], p[1], p[2], p[3], p[4], p[5],
                    ReportType.valueOf(p[6]),
                    ItemStatus.valueOf(p[7]),
                    p[8]
                ));
            }
            return items;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read items", e);
        }
    }

    @Override
    public void save(Item value) {
        List<Item> items = findAll();
        boolean replaced = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(value.getId())) {
                items.set(i, value);
                replaced = true;
                break;
            }
        }
        if (!replaced) items.add(value);
        writeAll(items);
    }

    @Override
    public void delete(String id) {
        List<Item> items = findAll();
        items.removeIf(x -> x.getId().equals(id));
        writeAll(items);
    }

    private void writeAll(List<Item> items) {
        List<String> lines = new ArrayList<>();
        for (Item i : items) {
            lines.add(String.join("|",
                clean(i.getId()), clean(i.getTitle()), clean(i.getCategory()),
                clean(i.getLocation()), clean(i.getDate()), clean(i.getDescription()),
                i.getReportType().name(), i.getStatus().name(), clean(i.getOwnerId())
            ));
        }
        try {
            Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write items", e);
        }
    }

    private String clean(String s) {
        return s == null ? "" : s.replace("|", "/").replace("\n", " ");
    }
}
