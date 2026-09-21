package com.campus.lostfound;

public class Item {
    private final String id;
    private String title;
    private String category;
    private String location;
    private String date;
    private String description;
    private ReportType reportType;
    private ItemStatus status;
    private String ownerId;

    public Item(String id, String title, String category, String location,
                String date, String description, ReportType reportType,
                ItemStatus status, String ownerId) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.location = location;
        this.date = date;
        this.description = description;
        this.reportType = reportType;
        this.status = status;
        this.ownerId = ownerId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public ReportType getReportType() { return reportType; }
    public ItemStatus getStatus() { return status; }
    public String getOwnerId() { return ownerId; }

    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(String date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setReportType(ReportType reportType) { this.reportType = reportType; }
    public void setStatus(ItemStatus status) { this.status = status; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    @Override
    public String toString() {
        return id + " | " + reportType + " | " + title + " | " + category
                + " | " + location + " | " + status;
    }
}
