package com.example.event_planner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.event_planner.models.Event;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    private DatabaseHelper dbHelper;

    public EventDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long addEvent(Event event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EVENT_USER_ID, event.getUserId());
        values.put(DatabaseHelper.COLUMN_EVENT_TITLE, event.getTitle());
        values.put(DatabaseHelper.COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(DatabaseHelper.COLUMN_EVENT_LOCATION, event.getLocation());
        values.put(DatabaseHelper.COLUMN_EVENT_START_DATETIME, event.getStartDateTime());
        values.put(DatabaseHelper.COLUMN_EVENT_END_DATETIME, event.getEndDateTime());
        values.put(DatabaseHelper.COLUMN_EVENT_STATUS, event.getStatus());
        values.put(DatabaseHelper.COLUMN_EVENT_PRIORITY, event.getPriority());
        values.put(DatabaseHelper.COLUMN_EVENT_HAS_REMINDER, event.hasReminder() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_EVENT_REMINDER_TIME, event.getReminderTime());
        values.put(DatabaseHelper.COLUMN_EVENT_CATEGORY, event.getCategory());
        values.put(DatabaseHelper.COLUMN_EVENT_NOTES, event.getNotes());
        values.put(DatabaseHelper.COLUMN_EVENT_CREATED_AT, System.currentTimeMillis());
        values.put(DatabaseHelper.COLUMN_EVENT_UPDATED_AT, System.currentTimeMillis());

        long id = db.insert(DatabaseHelper.TABLE_EVENTS, null, values);
        db.close();

        return id;
    }

    public Event getEventById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = getAllColumns();
        String selection = DatabaseHelper.COLUMN_EVENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, columns, selection, selectionArgs, null, null, null);

        Event event = null;
        if (cursor != null && cursor.moveToFirst()) {
            event = cursorToEvent(cursor);
            cursor.close();
        }

        db.close();
        return event;
    }

    public List<Event> getEventsByUser(int userId) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = getAllColumns();
        String selection = DatabaseHelper.COLUMN_EVENT_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        String orderBy = DatabaseHelper.COLUMN_EVENT_START_DATETIME + " ASC";

        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                events.add(cursorToEvent(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return events;
    }

    public List<Event> getUpcomingEventsByUser(int userId) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = getAllColumns();
        String selection = DatabaseHelper.COLUMN_EVENT_USER_ID + " = ? AND " +
                DatabaseHelper.COLUMN_EVENT_START_DATETIME + " > ? AND " +
                DatabaseHelper.COLUMN_EVENT_STATUS + " != ?";
        String[] selectionArgs = {String.valueOf(userId),
                String.valueOf(System.currentTimeMillis()),
                Event.STATUS_CANCELLED};
        String orderBy = DatabaseHelper.COLUMN_EVENT_START_DATETIME + " ASC";

        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                events.add(cursorToEvent(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return events;
    }

    public List<Event> getEventsByDateRange(int userId, long startDate, long endDate) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = getAllColumns();
        String selection = DatabaseHelper.COLUMN_EVENT_USER_ID + " = ? AND " +
                DatabaseHelper.COLUMN_EVENT_START_DATETIME + " >= ? AND " +
                DatabaseHelper.COLUMN_EVENT_START_DATETIME + " <= ?";
        String[] selectionArgs = {String.valueOf(userId), String.valueOf(startDate), String.valueOf(endDate)};
        String orderBy = DatabaseHelper.COLUMN_EVENT_START_DATETIME + " ASC";

        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                events.add(cursorToEvent(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return events;
    }

    public int updateEvent(Event event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EVENT_TITLE, event.getTitle());
        values.put(DatabaseHelper.COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(DatabaseHelper.COLUMN_EVENT_LOCATION, event.getLocation());
        values.put(DatabaseHelper.COLUMN_EVENT_START_DATETIME, event.getStartDateTime());
        values.put(DatabaseHelper.COLUMN_EVENT_END_DATETIME, event.getEndDateTime());
        values.put(DatabaseHelper.COLUMN_EVENT_STATUS, event.getStatus());
        values.put(DatabaseHelper.COLUMN_EVENT_PRIORITY, event.getPriority());
        values.put(DatabaseHelper.COLUMN_EVENT_HAS_REMINDER, event.hasReminder() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_EVENT_REMINDER_TIME, event.getReminderTime());
        values.put(DatabaseHelper.COLUMN_EVENT_CATEGORY, event.getCategory());
        values.put(DatabaseHelper.COLUMN_EVENT_NOTES, event.getNotes());
        values.put(DatabaseHelper.COLUMN_EVENT_UPDATED_AT, System.currentTimeMillis());

        String whereClause = DatabaseHelper.COLUMN_EVENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(event.getId())};

        int result = db.update(DatabaseHelper.TABLE_EVENTS, values, whereClause, whereArgs);
        db.close();

        return result;
    }

    public int deleteEvent(int eventId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = DatabaseHelper.COLUMN_EVENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(eventId)};

        int result = db.delete(DatabaseHelper.TABLE_EVENTS, whereClause, whereArgs);
        db.close();

        return result;
    }

    public List<Event> getEventsByStatus(int userId, String status) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = getAllColumns();
        String selection = DatabaseHelper.COLUMN_EVENT_USER_ID + " = ? AND " +
                DatabaseHelper.COLUMN_EVENT_STATUS + " = ?";
        String[] selectionArgs = {String.valueOf(userId), status};
        String orderBy = DatabaseHelper.COLUMN_EVENT_START_DATETIME + " ASC";

        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                events.add(cursorToEvent(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return events;
    }

    public List<Event> searchEvents(int userId, String query) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = getAllColumns();
        String selection = DatabaseHelper.COLUMN_EVENT_USER_ID + " = ? AND (" +
                DatabaseHelper.COLUMN_EVENT_TITLE + " LIKE ? OR " +
                DatabaseHelper.COLUMN_EVENT_DESCRIPTION + " LIKE ?)";
        String searchQuery = "%" + query + "%";
        String[] selectionArgs = {String.valueOf(userId), searchQuery, searchQuery};
        String orderBy = DatabaseHelper.COLUMN_EVENT_START_DATETIME + " ASC";

        Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                events.add(cursorToEvent(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return events;
    }

    private String[] getAllColumns() {
        return new String[]{
                DatabaseHelper.COLUMN_EVENT_ID,
                DatabaseHelper.COLUMN_EVENT_USER_ID,
                DatabaseHelper.COLUMN_EVENT_TITLE,
                DatabaseHelper.COLUMN_EVENT_DESCRIPTION,
                DatabaseHelper.COLUMN_EVENT_LOCATION,
                DatabaseHelper.COLUMN_EVENT_START_DATETIME,
                DatabaseHelper.COLUMN_EVENT_END_DATETIME,
                DatabaseHelper.COLUMN_EVENT_STATUS,
                DatabaseHelper.COLUMN_EVENT_PRIORITY,
                DatabaseHelper.COLUMN_EVENT_HAS_REMINDER,
                DatabaseHelper.COLUMN_EVENT_REMINDER_TIME,
                DatabaseHelper.COLUMN_EVENT_CATEGORY,
                DatabaseHelper.COLUMN_EVENT_NOTES,
                DatabaseHelper.COLUMN_EVENT_CREATED_AT,
                DatabaseHelper.COLUMN_EVENT_UPDATED_AT
        };
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_ID)));
        event.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_USER_ID)));
        event.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_TITLE)));
        event.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_DESCRIPTION)));
        event.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_LOCATION)));
        event.setStartDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_START_DATETIME)));
        event.setEndDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_END_DATETIME)));
        event.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_STATUS)));
        event.setPriority(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_PRIORITY)));
        event.setHasReminder(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_HAS_REMINDER)) == 1);
        event.setReminderTime(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_REMINDER_TIME)));
        event.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_CATEGORY)));
        event.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_NOTES)));
        event.setCreatedAt(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_CREATED_AT)));
        event.setUpdatedAt(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_UPDATED_AT)));
        return event;
    }
}