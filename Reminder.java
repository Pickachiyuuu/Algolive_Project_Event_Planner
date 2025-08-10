package com.example.event_planner.models;

public class Reminder {
    private int id;
    private int eventId;
    private int userId;
    private String reminderTime;
    private String reminderType; // notification, email, sms
    private String message;
    private boolean isActive;
    private String createdAt;

    public Reminder() {}

    public Reminder(int eventId, int userId, String reminderTime,
                    String reminderType, String message) {
        this.eventId = eventId;
        this.userId = userId;
        this.reminderTime = reminderTime;
        this.reminderType = reminderType;
        this.message = message;
        this.isActive = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getReminderTime() { return reminderTime; }
    public void setReminderTime(String reminderTime) { this.reminderTime = reminderTime; }

    public String getReminderType() { return reminderType; }
    public void setReminderType(String reminderType) { this.reminderType = reminderType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public byte[] getTitle() {
        return null;
    }

    public boolean isTriggered() {
        return false;
    }

    public void setTitle(String string) {
    }

    public void setTriggered(boolean b) {

    }
}