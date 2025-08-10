package com.example.event_planner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.event_planner.models.Reminder;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAO {
    private DatabaseHelper dbHelper;

    public ReminderDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long createReminder(Reminder reminder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_REMINDER_EVENT_ID, reminder.getEventId());
        values.put(DatabaseHelper.COLUMN_REMINDER_USER_ID, reminder.getUserId());
        values.put(DatabaseHelper.COLUMN_REMINDER_TITLE, reminder.getTitle());
        values.put(DatabaseHelper.COLUMN_REMINDER_MESSAGE, reminder.getMessage());
        values.put(DatabaseHelper.COLUMN_REMINDER_TIME, reminder.getReminderTime());
        values.put(DatabaseHelper.COLUMN_REMINDER_TRIGGERED, reminder.isTriggered() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_REMINDER_CREATED_AT, reminder.getCreatedAt());
        long reminderId = db.insert(DatabaseHelper.TABLE_REMINDERS, null, values);
        db.close();
        return reminderId;
    }

    public List<Reminder> getPendingReminders() {
        List<Reminder> reminders = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long currentTime = System.currentTimeMillis();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_REMINDERS,
                null,
                DatabaseHelper.COLUMN_REMINDER_TIME + "<=? AND " + DatabaseHelper.COLUMN_REMINDER_TRIGGERED + "=0",
                new String[]{String.valueOf(currentTime)},
                null, null,
                DatabaseHelper.COLUMN_REMINDER_TIME + " ASC"
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                reminders.add(cursorToReminder(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return reminders;
    }

    public List<Reminder> getRemindersByEventId(int eventId) {
        List<Reminder> reminders = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_REMINDERS,
                null,
                DatabaseHelper.COLUMN_REMINDER_EVENT_ID + "=?",
                new String[]{String.valueOf(eventId)},
                null, null,
                DatabaseHelper.COLUMN_REMINDER_TIME + " ASC"
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                reminders.add(cursorToReminder(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return reminders;
    }

    public int markReminderAsTriggered(int reminderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_REMINDER_TRIGGERED, 1);
        int rowsAffected = db.update(
                DatabaseHelper.TABLE_REMINDERS,
                values,
                DatabaseHelper.COLUMN_REMINDER_ID + "=?",
                new String[]{String.valueOf(reminderId)}
        );
        db.close();
        return rowsAffected;
    }

    public int deleteRemindersByEventId(int eventId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(
                DatabaseHelper.TABLE_REMINDERS,
                DatabaseHelper.COLUMN_REMINDER_EVENT_ID + "=?",
                new String[]{String.valueOf(eventId)}
        );
        db.close();
        return rowsAffected;
    }

    private Reminder cursorToReminder(Cursor cursor) {
        Reminder reminder = new Reminder();
        reminder.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_ID)));
        reminder.setEventId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_EVENT_ID)));
        reminder.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_USER_ID)));
        reminder.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_TITLE)));
        reminder.setMessage(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_MESSAGE)));
        reminder.setReminderTime(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_TIME))));
        reminder.setTriggered(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_TRIGGERED)) == 1);
        reminder.setCreatedAt(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REMINDER_CREATED_AT))));
        return reminder;
    }
}
