package com.example.smartcarmqttapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity
public class Question {
    private String question;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String answer_4;
    private int correct_Answer;
    private String category;

    public Question(){}

    public Question(String question, String answer_1, String answer_2, String answer_3, String answer_4, int correct_Answer, String category) {
        this.question = question;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.correct_Answer = correct_Answer;
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public int getCorrect_Answer() {
        return correct_Answer;
    }

    public void setCorrect_Answer(int correct_Answer) {
        this.correct_Answer = correct_Answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    /*
    @PrimaryKey(autoGenerate = true)
    public int qid;

    @ColumnInfo
    public String question;

    @ColumnInfo(defaultValue = "First Answer")
    public String ans_1;

    @ColumnInfo(defaultValue = "Second Answer")
    public String ans_2;

    @ColumnInfo(defaultValue = "Third Answer")
    public String ans_3;

    @ColumnInfo(defaultValue = "Fourth Answer")
    public String ans_4;

    @ColumnInfo(defaultValue = "Correct Answer")
    public String correct_answer;

    @ColumnInfo
    public String category;

     */
}
