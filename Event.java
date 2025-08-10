package com.example.event_planner.models;

public class Event {
    public static final String STATUS_UPCOMING = "upcoming";
    public static final String STATUS_ONGOING = "ongoing";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_CANCELLED = "cancelled";

    public static final String PRIORITY_LOW = "low";
    public static final String PRIORITY_MEDIUM = "medium";
    public static final String PRIORITY_HIGH = "high";

    private int id;
    private int userId;
    private String title;
    private String description;
    private String location;
    private long startDateTime;
    private long endDateTime;
    private String status;
    private String priority;
    private boolean hasReminder;
    private long reminderTime;
    private String category;
    private String notes;
    private long createdAt;
    private long updatedAt;

    public Event() {
    }

    public Event(int userId, String title, String description, long startDateTime, long endDateTime) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = STATUS_UPCOMING;
        this.priority = PRIORITY_MEDIUM;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public long getStartDateTime() { return startDateTime; }
    public long getEndDateTime() { return endDateTime; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public boolean hasReminder() { return hasReminder; }
    public long getReminderTime() { return reminderTime; }
    public String getCategory() { return category; }
    public String getNotes() { return notes; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setStartDateTime(long startDateTime) { this.startDateTime = startDateTime; }
    public void setEndDateTime(long endDateTime) { this.endDateTime = endDateTime; }
    public void setStatus(String status) { this.status = status; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setHasReminder(boolean hasReminder) { this.hasReminder = hasReminder; }
    public void setReminderTime(long reminderTime) { this.reminderTime = reminderTime; }
    public void setCategory(String category) { this.category = category; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public boolean isValid() {
        return title != null && !title.trim().isEmpty() &&
                startDateTime > 0 &&
                endDateTime > startDateTime;
    }

    public boolean isUpcoming() {
        return STATUS_UPCOMING.equals(status) && startDateTime > System.currentTimeMillis();
    }

    public boolean isOngoing() {
        long currentTime = System.currentTimeMillis();
        return STATUS_ONGOING.equals(status) ||
                (startDateTime <= currentTime && endDateTime > currentTime);
    }

    public boolean isCompleted() {
        return STATUS_COMPLETED.equals(status) || endDateTime <= System.currentTimeMillis();
    }

    public long getDurationMillis() {
        return endDateTime - startDateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", status='" + status + '\'' +
                '}';
    }

    public int getDate() {
        return 0;
    }

    public int getTime() {
        return 0;
    }
}