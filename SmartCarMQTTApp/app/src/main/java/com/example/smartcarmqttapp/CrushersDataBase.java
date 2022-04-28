package com.example.smartcarmqttapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CrushersDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "crushersDataBase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "crushersDataBase";

    private static final String COLUMN_ID = "_ID";
    private static final String COLUMN_SCORE = "SCORE";
    private static final String COLUMN_CORRECT_ANSWERS = "CORRECT_ANSWERS";
    private static final String COLUMN_WRONG_ANSWERS = "WRONG_ANSWERS";
    private static final String COLUMN_COUNT = "ANSWERED_QUESTIONS";

    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                       " (" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_SCORE + "TEXT, " +
                       COLUMN_CORRECT_ANSWERS + "INTEGER, " +
                       COLUMN_WRONG_ANSWERS + "INTEGER, " +
                       COLUMN_COUNT + "INTEGER" + ") ";
                        //COLUMN_TIME_TAKEN + "STRING" + ") ";

        /*
        SQL Table for Results of a current Quiz

        | ID  |  SCORE  |  CorrectAns  |  WrongAns  |  COUNT  |  TimeTaken |
        --------------------------------------------------------------------
        |     |         |              |            |         |            |

         */

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private class QuizState{
        private UserAnswer userAnswer;
        private int score;
        private int correctAns;
        private int wrongAns;
        private int timeTaken;

        public void finish(){
            //create entry with all attributes
            //INSERT score, correctAns, wrongAns, timeTaken into curshersDB(SCORE,
        }

        public void falseFinish(){
            //reset quiz state
        }

        public int getCorrectAnswer() {
            String query = "SELECT COUNT " + COLUMN_CORRECT_ANSWERS + "FROM " + TABLE_NAME +  "WHERE " + COLUMN_CORRECT_ANSWERS + "= " + true;
            return Integer.getInteger(query);
        }
        public int getWrongAnswer() {
            String query = "SELECT COUNT " + COLUMN_CORRECT_ANSWERS + "FROM " + TABLE_NAME +  "WHERE " + COLUMN_CORRECT_ANSWERS + "= " + false;
            return Integer.getInteger(query);
        }
        public int getFinalResult() {
            //getCorrect / getQuestionCount;
            return this.score;
        }

        public int getQuestionCount() {
            return 0;
        }

        public void finsihQUiz(){
            //write query to add all local fields from QUizState to the DB
            //set QuizState flag to false;;; finish quiz
        }
    }
    private class UserAnswer{
        private boolean isCorrect;
    }

    public boolean addData(QuizState quizState) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SCORE, quizState.getFinalResult());
        cv.put(COLUMN_CORRECT_ANSWERS, quizState.getCorrectAnswer());
        cv.put(COLUMN_WRONG_ANSWERS, quizState.getWrongAnswer());
        cv.put(COLUMN_COUNT, quizState.getQuestionCount());

        long result = db.insert(TABLE_NAME, null, cv);

        return (result == -1) ? false : true;
    }
}
