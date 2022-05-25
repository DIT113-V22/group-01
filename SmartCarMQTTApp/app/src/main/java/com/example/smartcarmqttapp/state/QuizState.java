package com.example.smartcarmqttapp.state;

import com.example.smartcarmqttapp.model.Question;
import com.example.smartcarmqttapp.model.UserAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class for defining the current state of the quiz: in particular what questions are part of the quiz and what answers have been accumulated, as well as the current score.
public class QuizState {

    private int id;
    private boolean isTakingQuiz;
    private List<Question> questions;
    private List<UserAnswer> currentAnswers;
    private int score;
    private int currentPointer;
    private Map<String, Integer> options;

    // Constructor with all fields
    public QuizState(
            int id,
            boolean isTakingQuiz,
            List<Question> questions,
            List<UserAnswer> currentAnswers,
            int score,
            HashMap<String, Integer> options) {
        this.id = id;
        this.isTakingQuiz = isTakingQuiz;
        this.questions = questions;
        this.currentAnswers = currentAnswers;
        this.score = score;
        this.currentPointer = 0;
        this.options = options;
    }

    // Constructor without id
    public QuizState(
            boolean isTakingQuiz,
            List<Question> questions,
            List<UserAnswer> currentAnswers,
            int score) {
        this.isTakingQuiz = isTakingQuiz;
        this.questions = questions;
        this.currentAnswers = currentAnswers;
        this.score = score;
        this.currentPointer = 0;
        this.options = new HashMap<>();
    }


    public static QuizState instance = new QuizState();
    // Empty constructor
    private QuizState() {}

    /**
     * Helper methods
     */

    // Submits an answer for the current question
    // DO NOT call for questions when the user runs out of time
    public void answerQuestion(Question question, UserAnswer answer, boolean isTest) {
        if (question.getCorrectAnswer() == answer.getIndex()) {
            // Answer is correct. Increase the score and remove from review
            incrementScore();
            question.setNeedsReview(
                    question.getNeedsReview() - 1
            );
        } else {
            // Answer is incorrect. Add question to review
            question.setNeedsReview(2);
        }
        //currentAnswers.add(answer); // adds answer to list of current answers
        if(!isTest){
            incrementCurrentPointer(); // increases question number
        }
    }

    public void answerCurrentQuestion(UserAnswer answer) {
        answerQuestion(getCurrentQuestion(currentPointer), answer, false);
    }

    public Question getCurrentQuestion(int currentPointer) {
        return this.questions.get(currentPointer);
    }

    public Question getCurrentQuestion() {
        return this.questions.get(this.currentPointer);
    }

    public void incrementScore() {
        this.score++;
        // Made method separate in case UI actions needed
    }

    // Moves one question up (we don't need moving down)
    public void incrementCurrentPointer() {
        this.currentPointer++;
        if (this.currentPointer + 1 == this.questions.size())
            finishQuiz();
        // Made method separate in case UI actions needed
    }

    public void finishQuiz() {
        // ToDo: add logic for finishing quiz
        // public in case we want to add a button for stopping quiz, so it can be
        // accessed anywhere
    }

    public int calculateCurrentScore() {
        // Score is already updated, but method added just in case
        int calcScore = 0;
        for (int i = 0; i < currentAnswers.size(); i++) {
            if (questions.get(i).getCorrectAnswer() == currentAnswers.get(i).getIndex()) {
                calcScore++;
            }
        }
        return calcScore;
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

    public boolean getIsTakingQuiz() {
        return this.isTakingQuiz;
    }

    public void setIsTakingQuiz(boolean isTakingQuiz) {
        this.isTakingQuiz = isTakingQuiz;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<UserAnswer> getCurrentAnswers() {
        return this.currentAnswers;
    }

    public void setCurrentAnswers(List<UserAnswer> currentAnswers) {
        this.currentAnswers = currentAnswers;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentPointer() {
        return this.currentPointer;
    }

    public void setCurrentPointer(int currentPointer) {
        this.currentPointer = currentPointer;
    }

    public Map<String, Integer> getOptions(){
        return this.options;
    }

    @Override
    public String toString() {
        return "QuizState{" +
                "isTakingQuiz=" + isTakingQuiz +
                ", questions=" + questions +
                ", currentAnswers=" + currentAnswers +
                ", score=" + score +
                ", currentPointer=" + currentPointer +
                '}';
    }
}
