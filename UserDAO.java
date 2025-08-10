package com.example.event_planner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.event_planner.models.User;

public class UserDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event_planner.db";
    private static final int DATABASE_VERSION = 1;

    public UserDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean isUsernameExists(String username) {
        return false;
    }

    public boolean isEmailExists(String email) {
        return false;
    }

    public long addUser(User user) {
        return 0;
    }

    public User authenticateUser(String username, String password) {
        return null;
    }
}
