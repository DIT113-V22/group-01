package com.example.smartcarmqttapp.model;
import java.util.List;

// Class for creating each possible question and the answers & explanations to them
public class Question {

    private int id;
    private String question;
    private String explanation;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private int correctIndex;
    private Category category;
    private boolean needsReview;

    // Constructor with all fields
    public Question(
            int id,
            String question,
            String explanation,
            String firstAnswer,
            String secondAnswer,
            String thirdAnswer,
            String fourthAnswer,
            int correctIndex,
            Category category,
            boolean needsReview
    ) {
        this.id = id;
        this.question = question;
        this.explanation = explanation;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.correctIndex = correctIndex;
        this.category = category;
        this.needsReview = needsReview;
    }

    // Constructor without id
    public Question(
            String question,
            String explanation,
            String firstAnswer,
            String secondAnswer,
            String thirdAnswer,
            String fourthAnswer,
            int correctIndex,
            Category category,
            boolean needsReview
    ) {
        this.question = question;
        this.explanation = explanation;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.correctIndex = correctIndex;
        this.category = category;
        this.needsReview = needsReview;
    }

    // Empty constructor
    public Question() {

    }

    /**
     * Helper methods
     */

    // Returns correct answer string for this question
    public String getCorrectAnswer() throws RuntimeException {
        return getAnswerFromIndex(correctIndex);
    }

    public String getAnswerFromIndex(int index) throws RuntimeException{
        switch(index) {
            case 1:
                return firstAnswer;
            case 2:
                return secondAnswer;
            case 3:
                return thirdAnswer;
            case 4:
                return fourthAnswer;
            default:
                throw new RuntimeException("Correct Answer Index must be 1-4");
        }
    }

    /**
     * Getters and Setters
     */

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getNeedsReview() {
        return this.needsReview;
    }

    public void setNeedsReview(boolean needsReview) {
        this.needsReview = needsReview;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", explanation='" + explanation + '\'' +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                ", fourthAnswer='" + fourthAnswer + '\'' +
                ", correctIndex=" + correctIndex +
                ", category=" + category +
                ", needsReview=" + needsReview +
                '}';
    }
}
