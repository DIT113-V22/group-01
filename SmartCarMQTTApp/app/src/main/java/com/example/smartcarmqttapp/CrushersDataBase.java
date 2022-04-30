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

    private Context context;
    private static final String DATABASE_NAME = "crushersDataBase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "crushersDataBase";

    private static final String COLUMN_ID = "_ID";
    private static final String COLUMN_SCORE = "SCORE";
    private static final String COLUMN_CORRECT_ANSWERS = "CORRECT_ANSWERS";
    private static final String COLUMN_WRONG_ANSWERS = "WRONG_ANSWERS";


    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*
    SQL Table for Results of a current Quiz

    ------------------------------------------------------
    | ID  |  SCORE  |  CorrectAns  |  WrongAns  |  COUNT  |
    -------------------------------------------------------
    |     |         |              |            |         |
    -------------------------------------------------------

    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TABLE_NAME +
                       " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_SCORE + "INT, " +
                       COLUMN_CORRECT_ANSWERS + "INT, " +
                       COLUMN_WRONG_ANSWERS + "INT " + ") ";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getFinalResult() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (!(db == null))
            cursor = db.rawQuery(query, null);

        return cursor;
    }

    public void finishQuiz(int score, int numOfCorrectAnswers, int NumOfWrongAnswers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SCORE, score);
        cv.put(COLUMN_CORRECT_ANSWERS, numOfCorrectAnswers);
        cv.put(COLUMN_WRONG_ANSWERS, NumOfWrongAnswers);

        db.insert(TABLE_NAME, null, cv);
    }
}
