package com.example.smartcarmqttapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CrushersDataBase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "CrushersDataBase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "question_table";
    private static final String COLUMN_QID = "qid";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER1 = "ans_1";
    private static final String COLUMN_ANSWER2 = "ans_2";
    private static final String COLUMN_ANSWER3 = "ans_3";
    private static final String COLUMN_ANSWER4 = "ans_4";
    private static final int COLUMN_CORRECT_ANSWER = 0;
    private static  final String COLUMN_CATEGORY = "category";

    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String query = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_QID + " INTEGER PRIMARY KEY, " +
            COLUMN_QUESTION + " TEXT," +
            COLUMN_ANSWER1 + " TEXT, " +
            COLUMN_ANSWER2 + " TEXT, " +
            COLUMN_ANSWER3 + " TEXT, " +
            COLUMN_ANSWER4 + " TEXT, " +
            COLUMN_CORRECT_ANSWER + " TEXT," +
            COLUMN_CATEGORY + " TEXT);";
    sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(sqLiteDatabase);
    }


}
