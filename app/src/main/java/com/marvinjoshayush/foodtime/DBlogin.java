package com.marvinjoshayush.foodtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBlogin extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "UserManager.db";
    // User table name
    public static final String TABLE_USER = "user";
    // User Table Columns names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_FNAME = "firstname";
    public static final String COLUMN_USER_LNAME = "lastname";
    public  static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";


    public DBlogin(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }


    // create table sql query

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER+ "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastnme TEXT,email TEXT,password TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USER);
        onCreate(db);

    }
}