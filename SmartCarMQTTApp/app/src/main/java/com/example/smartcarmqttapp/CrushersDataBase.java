package com.example.smartcarmqttapp;

import static java.sql.Types.INTEGER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class CrushersDataBase extends SQLiteOpenHelper {

    public Context context;
    public static final String DATABASE_NAME = "crushersDataBase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "crushersDataBase";

    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_SCORE = "SCORE";
    public static final String COLUMN_CORRECT_ANSWERS = "CORRECT_ANSWERS";
    public static final String COLUMN_WRONG_ANSWERS = "WRONG_ANSWERS";


    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*
    SQL Table for Results of a current Quiz

    ---------------------------------------------
    | ID  |  SCORE  |  CorrectAns  |  WrongAns  |
    ---------------------------------------------
    |     |         |              |            |
    ---------------------------------------------

    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TABLE_NAME +
                       " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_SCORE + " TEXT, " +
                       COLUMN_CORRECT_ANSWERS + " TEXT, " +
                       COLUMN_WRONG_ANSWERS + " TEXT " + ") ";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
