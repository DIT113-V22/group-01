package com.example.smartcarmqttapp;

import android.provider.BaseColumns;

public final class QuizContract {
    private QuizContract(){
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "question_table";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER1 = "ans_1";
        public static final String COLUMN_ANSWER2 = "ans_2";
        public static final String COLUMN_ANSWER3 = "ans_3";
        public static final String COLUMN_ANSWER4 = "ans_4";
        public static final String COLUMN_CORRECT_ANSWER = "answer_number";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_NEEDS_REVIEW = "needs_review";
        public static final String COLUMN_CATEGORY = "category";
    }
}
