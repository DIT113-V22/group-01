package com.example.smartcarmqttapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartcarmqttapp.model.Result;

import java.util.ArrayList;
import java.util.List;

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

    public void finishQuiz(int score, int numOfCorrectAnswers, int numOfWrongAnswers, String category) {
        ContentValues cv = new ContentValues();

        cv.put(CrushersDataBase.COLUMN_SCORE, score);
        cv.put(CrushersDataBase.COLUMN_CORRECT_ANSWERS, numOfCorrectAnswers);
        cv.put(CrushersDataBase.COLUMN_WRONG_ANSWERS, numOfWrongAnswers);
        cv.put(CrushersDataBase.COLUMN_CATEGORY, category);

        database.insert(CrushersDataBase.TABLE_NAME, null, cv);
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        Cursor cursor = this.fetch();

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_SCORE)));
                result.setNumOfCorrectAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CORRECT_ANSWERS)));
                result.setNumOfWrongAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_WRONG_ANSWERS)));
                result.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CATEGORY)));
                results.add(result);
            } while (cursor.moveToNext());
        }

        return results;
    }

    private Cursor fetch() {
        String[] columns = new String[] {CrushersDataBase.COLUMN_ID, CrushersDataBase.COLUMN_SCORE, CrushersDataBase.COLUMN_CORRECT_ANSWERS, CrushersDataBase.COLUMN_WRONG_ANSWERS, CrushersDataBase.COLUMN_CATEGORY};
        Cursor cursor = database.query(CrushersDataBase.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public List<Result> getBasicTrafficRulesCategory() {
        List<Result> results = new ArrayList<>();
        database = crushersDataBase.getReadableDatabase();
        Cursor cursor = this.fetchBasicTrafficRulesCategoryRecords();

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_SCORE)));
                result.setNumOfCorrectAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CORRECT_ANSWERS)));
                result.setNumOfWrongAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_WRONG_ANSWERS)));
                result.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CATEGORY)));
                results.add(result);
            } while (cursor.moveToNext());
        }

        return results;
    }

    private Cursor fetchBasicTrafficRulesCategoryRecords() {
        String category = "Basic Traffic Rules and Signs";
        String[] columns = new String[] {CrushersDataBase.COLUMN_ID, CrushersDataBase.COLUMN_SCORE, CrushersDataBase.COLUMN_CORRECT_ANSWERS, CrushersDataBase.COLUMN_WRONG_ANSWERS, CrushersDataBase.COLUMN_CATEGORY};
        Cursor cursor = database.query(CrushersDataBase.TABLE_NAME, columns, CrushersDataBase.COLUMN_CATEGORY + " = '" + category + "'", null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public List<Result> getEnvironmentCategory() {
        List<Result> results = new ArrayList<>();
        database = crushersDataBase.getReadableDatabase();
        Cursor cursor = this.fetchEnvironmentCategoryRecords();

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_SCORE)));
                result.setNumOfCorrectAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CORRECT_ANSWERS)));
                result.setNumOfWrongAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_WRONG_ANSWERS)));
                result.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CATEGORY)));
                results.add(result);
            } while (cursor.moveToNext());
        }

        return results;
    }

    private Cursor fetchEnvironmentCategoryRecords() {
        String category = "Environment";
        String[] columns = new String[] {CrushersDataBase.COLUMN_ID, CrushersDataBase.COLUMN_SCORE, CrushersDataBase.COLUMN_CORRECT_ANSWERS, CrushersDataBase.COLUMN_WRONG_ANSWERS, CrushersDataBase.COLUMN_CATEGORY};
        Cursor cursor = database.query(CrushersDataBase.TABLE_NAME, columns, CrushersDataBase.COLUMN_CATEGORY + " = '" + category + "'", null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public List<Result> getSafetyAndBestPracticesCategory() {
        List<Result> results = new ArrayList<>();
        database = crushersDataBase.getReadableDatabase();
        Cursor cursor = this.fetchSafetyAndBestPracticesRecords();

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_SCORE)));
                result.setNumOfCorrectAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CORRECT_ANSWERS)));
                result.setNumOfWrongAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_WRONG_ANSWERS)));
                result.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CrushersDataBase.COLUMN_CATEGORY)));
                results.add(result);
            } while (cursor.moveToNext());
        }

        return results;
    }

    private Cursor fetchSafetyAndBestPracticesRecords() {
        String category = "Safety and Best Practices";
        String[] columns = new String[] {CrushersDataBase.COLUMN_ID, CrushersDataBase.COLUMN_SCORE, CrushersDataBase.COLUMN_CORRECT_ANSWERS, CrushersDataBase.COLUMN_WRONG_ANSWERS, CrushersDataBase.COLUMN_CATEGORY};
        Cursor cursor = database.query(CrushersDataBase.TABLE_NAME, columns, CrushersDataBase.COLUMN_CATEGORY + " = '" + category + "'", null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
}
