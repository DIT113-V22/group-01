package com.example.smartcarmqttapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CrushersDataBaseManager {

    private CrushersDataBase crushersDataBase;
    private Context context;
    private SQLiteDatabase database;

    public CrushersDataBaseManager(Context context) {
        this.context = context;
    }

    public CrushersDataBaseManager open() {
        crushersDataBase = new CrushersDataBase(context);
        database = crushersDataBase.getWritableDatabase();
        return this;
    }

    public void close() {
        crushersDataBase.close();
    }

    public void finishQuiz(int score, int numOfCorrectAnswers, int numOfWrongAnswers) {
        ContentValues cv = new ContentValues();

        cv.put(CrushersDataBase.COLUMN_SCORE, score);
        cv.put(CrushersDataBase.COLUMN_CORRECT_ANSWERS, numOfCorrectAnswers);
        cv.put(CrushersDataBase.COLUMN_WRONG_ANSWERS, numOfWrongAnswers);

        database.insert(CrushersDataBase.TABLE_NAME, null, cv);
    }

    public Cursor fetch() {
        String[] columns = new String[] {CrushersDataBase.COLUMN_ID, CrushersDataBase.COLUMN_SCORE, CrushersDataBase.COLUMN_CORRECT_ANSWERS, CrushersDataBase.COLUMN_WRONG_ANSWERS};
        Cursor cursor = database.query(CrushersDataBase.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
}
