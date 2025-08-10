package com.example.event_planner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_REMINDER_TRIGGERED = "reminder_triggered";

    private static final String DATABASE_NAME = "EventPlanner.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_REMINDERS = "reminders";

    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_FIRST_NAME = "first_name";
    public static final String COLUMN_USER_LAST_NAME = "last_name";
    public static final String COLUMN_USER_PHONE = "phone_number";
    public static final String COLUMN_USER_CREATED_AT = "created_at";
    public static final String COLUMN_USER_UPDATED_AT = "updated_at";

    public static final String COLUMN_EVENT_ID = "id";
    public static final String COLUMN_EVENT_USER_ID = "user_id";
    public static final String COLUMN_EVENT_TITLE = "title";
    public static final String COLUMN_EVENT_DESCRIPTION = "description";
    public static final String COLUMN_EVENT_LOCATION = "location";
    public static final String COLUMN_EVENT_START_DATETIME = "start_datetime";
    public static final String COLUMN_EVENT_END_DATETIME = "end_datetime";
    public static final String COLUMN_EVENT_STATUS = "status";
    public static final String COLUMN_EVENT_PRIORITY = "priority";
    public static final String COLUMN_EVENT_HAS_REMINDER = "has_reminder";
    public static final String COLUMN_EVENT_REMINDER_TIME = "reminder_time";
    public static final String COLUMN_EVENT_CATEGORY = "category";
    public static final String COLUMN_EVENT_NOTES = "notes";
    public static final String COLUMN_EVENT_CREATED_AT = "created_at";
    public static final String COLUMN_EVENT_UPDATED_AT = "updated_at";

    public static final String COLUMN_REMINDER_ID = "id";
    public static final String COLUMN_REMINDER_EVENT_ID = "event_id";
    public static final String COLUMN_REMINDER_USER_ID = "user_id";
    public static final String COLUMN_REMINDER_TITLE = "title";
    public static final String COLUMN_REMINDER_MESSAGE = "message";
    public static final String COLUMN_REMINDER_TIME = "reminder_time";
    public static final String COLUMN_REMINDER_TYPE = "type";
    public static final String COLUMN_REMINDER_STATUS = "status";
    public static final String COLUMN_REMINDER_IS_REPEATING = "is_repeating";
    public static final String COLUMN_REMINDER_REPEAT_INTERVAL = "repeat_interval_minutes";
    public static final String COLUMN_REMINDER_CREATED_AT = "created_at";
    public static final String COLUMN_REMINDER_UPDATED_AT = "updated_at";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_USERNAME + " TEXT UNIQUE NOT NULL,"
            + COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL,"
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_USER_FIRST_NAME + " TEXT,"
            + COLUMN_USER_LAST_NAME + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT,"
            + COLUMN_USER_CREATED_AT + " INTEGER,"
            + COLUMN_USER_UPDATED_AT + " INTEGER"
            + ")";

    private static final String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EVENT_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_EVENT_TITLE + " TEXT NOT NULL,"
            + COLUMN_EVENT_DESCRIPTION + " TEXT,"
            + COLUMN_EVENT_LOCATION + " TEXT,"
            + COLUMN_EVENT_START_DATETIME + " INTEGER NOT NULL,"
            + COLUMN_EVENT_END_DATETIME + " INTEGER NOT NULL,"
            + COLUMN_EVENT_STATUS + " TEXT DEFAULT 'upcoming',"
            + COLUMN_EVENT_PRIORITY + " TEXT DEFAULT 'medium',"
            + COLUMN_EVENT_HAS_REMINDER + " INTEGER DEFAULT 0,"
            + COLUMN_EVENT_REMINDER_TIME + " INTEGER,"
            + COLUMN_EVENT_CATEGORY + " TEXT,"
            + COLUMN_EVENT_NOTES + " TEXT,"
            + COLUMN_EVENT_CREATED_AT + " INTEGER,"
            + COLUMN_EVENT_UPDATED_AT + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_EVENT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
            + ")";

    private static final String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "("
            + COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REMINDER_EVENT_ID + " INTEGER NOT NULL,"
            + COLUMN_REMINDER_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_REMINDER_TITLE + " TEXT NOT NULL,"
            + COLUMN_REMINDER_MESSAGE + " TEXT,"
            + COLUMN_REMINDER_TIME + " INTEGER NOT NULL,"
            + COLUMN_REMINDER_TYPE + " TEXT DEFAULT 'notification',"
            + COLUMN_REMINDER_STATUS + " TEXT DEFAULT 'pending',"
            + COLUMN_REMINDER_IS_REPEATING + " INTEGER DEFAULT 0,"
            + COLUMN_REMINDER_REPEAT_INTERVAL + " INTEGER DEFAULT 0,"
            + COLUMN_REMINDER_CREATED_AT + " INTEGER,"
            + COLUMN_REMINDER_UPDATED_AT + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_REMINDER_EVENT_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + "),"
            + "FOREIGN KEY(" + COLUMN_REMINDER_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
            + ")";

    private static DatabaseHelper instance;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_REMINDERS_TABLE);

        db.execSQL("CREATE INDEX idx_events_user_id ON " + TABLE_EVENTS + "(" + COLUMN_EVENT_USER_ID + ")");
        db.execSQL("CREATE INDEX idx_events_start_datetime ON " + TABLE_EVENTS + "(" + COLUMN_EVENT_START_DATETIME + ")");
        db.execSQL("CREATE INDEX idx_reminders_event_id ON " + TABLE_REMINDERS + "(" + COLUMN_REMINDER_EVENT_ID + ")");
        db.execSQL("CREATE INDEX idx_reminders_user_id ON " + TABLE_REMINDERS + "(" + COLUMN_REMINDER_USER_ID + ")");
        db.execSQL("CREATE INDEX idx_reminders_time ON " + TABLE_REMINDERS + "(" + COLUMN_REMINDER_TIME + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}