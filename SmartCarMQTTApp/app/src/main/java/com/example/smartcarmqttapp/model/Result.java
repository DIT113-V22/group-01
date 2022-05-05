package com.example.smartcarmqttapp.model;

public class Result {

    private int score;
    private int numOfCorrectAnswers;
    private int numOfWrongAnswers;
    private String category;

    public Result() {

    }

    public Result(int score, int numOfCorrectAnswers, int numOfWrongAnswers, String category) {
        this.score = score;
        this.numOfCorrectAnswers = numOfCorrectAnswers;
        this.numOfWrongAnswers = numOfWrongAnswers;
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumOfCorrectAnswers() {
        return numOfCorrectAnswers;
    }

    public void setNumOfCorrectAnswers(int numOfCorrectAnswers) {
        this.numOfCorrectAnswers = numOfCorrectAnswers;
    }

    public int getNumOfWrongAnswers() {
        return numOfWrongAnswers;
    }

    public void setNumOfWrongAnswers(int numOfWrongAnswers) {
        this.numOfWrongAnswers = numOfWrongAnswers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Result {" +
                "score = " + score +
                ", numOfCorrectAnswers = " + numOfCorrectAnswers +
                ", numOfWrongAnswers = " + numOfWrongAnswers +
                ", category = '" + category + '\'' +
                '}';
    }
}
