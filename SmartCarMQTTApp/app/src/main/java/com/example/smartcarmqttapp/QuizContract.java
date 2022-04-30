package com.example.smartcarmqttapp;

import android.provider.BaseColumns;

public final class QuizContract {
    private QuizContract(){
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "question_table";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER1 = "first_answer";
        public static final String COLUMN_ANSWER2 = "second_answer";
        public static final String COLUMN_ANSWER3 = "third_answer";
        public static final String COLUMN_ANSWER4 = "fourth_answer";
        public static final String COLUMN_CORRECT_ANSWER = "answer_number";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_NEEDS_REVIEW = "needs_review";
        public static final String COLUMN_CATEGORY = "category";
    }
}
