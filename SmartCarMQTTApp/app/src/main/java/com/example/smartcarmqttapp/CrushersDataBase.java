package com.example.smartcarmqttapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.smartcarmqttapp.QuizContract.*;

import androidx.annotation.Nullable;

public class CrushersDataBase extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private Context context;
    private static final String DATABASE_NAME = "CrushersDataBase.db";
    private static final int DATABASE_VERSION = 1;


    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String createTableQuery = "CREATE TABLE " + QuestionsTable.TABLE_NAME+
            " (" + QuestionsTable._ID + " INTEGER PRIMARY KEY, " +
            QuestionsTable.COLUMN_QUESTION + " TEXT," +
            QuestionsTable.COLUMN_ANSWER1 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER2 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER3 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER4 + " TEXT, " +
            QuestionsTable.COLUMN_CORRECT_ANSWER + " INTEGER," +
            QuestionsTable.COLUMN_CATEGORY + " TEXT);";
    db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    onCreate(db);
    }

    private void populateQuestionsTable(Question question){
        //Place holder question values for now
        Question q1 = new Question("Question","ans1","ans2","ans3","ans4",3,"Safety");
        populateQuestionsTable(q1);
    }

    private void createQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_ANSWER1, question.getAnswer_1());
        cv.put(QuestionsTable.COLUMN_ANSWER2, question.getAnswer_2());
        cv.put(QuestionsTable.COLUMN_ANSWER3, question.getAnswer_3());
        cv.put(QuestionsTable.COLUMN_ANSWER4, question.getAnswer_4());
        cv.put(QuestionsTable.COLUMN_CORRECT_ANSWER, question.getCorrect_Answer());
        cv.put(QuestionsTable.COLUMN_CATEGORY, question.getCategory());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);


    }


}
