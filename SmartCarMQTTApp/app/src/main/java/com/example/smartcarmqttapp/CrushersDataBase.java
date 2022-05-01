package com.example.smartcarmqttapp;

import static java.sql.Types.INTEGER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.smartcarmqttapp.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
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

    private SQLiteDatabase db;


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
        this.db = db;

        String table = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   COLUMN_SCORE + " TEXT, " +
                   COLUMN_CORRECT_ANSWERS + " TEXT, " +
                   COLUMN_WRONG_ANSWERS + " TEXT " + ") ";
        db.execSQL(table);

        final String CREATE_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME+
            " (" + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionsTable.COLUMN_QUESTION + " TEXT," +
            QuestionsTable.COLUMN_ANSWER1 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER2 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER3 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER4 + " TEXT, " +
            QuestionsTable.COLUMN_CORRECT_ANSWER + " INTEGER," +
            QuestionsTable.COLUMN_EXPLANATION + " TEXT, " +
            QuestionsTable.COLUMN_NEEDS_REVIEW + " INTEGER, " +
            QuestionsTable.COLUMN_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
        populateQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int pastVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private List<Question> createQuestionsAddToList(){
        //Place holder question values (3 topics(categories), 15 questions each)
        return Arrays.asList(
                //CATEGORY 1: Basic Traffic Rules and Signs
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),

                //CATEGORY 2: Environment
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Environment"),

                //CATEGORY 3: Driving Safety and Best Practices
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices")
                );
    }

    private void populateQuestionsTable(){
        List<Question> questions = createQuestionsAddToList();
        for (Question question : questions) {
            createQuestion(question);
        }
    }

    private void createQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_ANSWER1, question.getFirstAnswer());
        cv.put(QuestionsTable.COLUMN_ANSWER2, question.getSecondAnswer());
        cv.put(QuestionsTable.COLUMN_ANSWER3, question.getThirdAnswer());
        cv.put(QuestionsTable.COLUMN_ANSWER4, question.getFourthAnswer());
        cv.put(QuestionsTable.COLUMN_CORRECT_ANSWER, question.getCorrectAnswer());
        cv.put(QuestionsTable.COLUMN_EXPLANATION, question.getExplanation());
        cv.put(QuestionsTable.COLUMN_NEEDS_REVIEW, question.getNeedsReview());
        cv.put(QuestionsTable.COLUMN_CATEGORY, question.getCategory());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions(){
    List<Question> questionList = new ArrayList<>();
    db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

    if(cursor.moveToFirst()){
        do {
        Question question = new Question();
        question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
        question.setFirstAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER1)));
        question.setSecondAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER2)));
        question.setThirdAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER3)));
        question.setFourthAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER4)));
        question.setCorrectAnswer(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_CORRECT_ANSWER)));
        question.setExplanation(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_EXPLANATION)));
        question.setNeedsReview(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_NEEDS_REVIEW)));
        question.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_CATEGORY)));

        questionList.add(question);

        } while (cursor.moveToNext());
    }
        cursor.close();
        return questionList;
    }

}
