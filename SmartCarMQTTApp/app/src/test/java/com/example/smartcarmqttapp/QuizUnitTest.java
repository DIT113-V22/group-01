package com.example.smartcarmqttapp;

import com.example.smartcarmqttapp.database.CrushersDataBase;
import com.example.smartcarmqttapp.model.Question;
import com.example.smartcarmqttapp.model.UserAnswer;
import com.example.smartcarmqttapp.screens.quiz.QuizQuestionActivity;
import com.example.smartcarmqttapp.state.QuizState;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert.*;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class QuizUnitTest {

    //fix this either using Robolectric testing (doesnt work atm) or something else
    // cannot create instantiated object of a android view class
    private QuizQuestionActivity quizQuestionActivity = Robolectric.setupActivity(QuizQuestionActivity.class);

    private static List<Question> getTestableQuestions() {
        return Arrays.asList(
                new Question(
                        "Test Question 1",
                        "First Answer",
                        "Second Answer",
                        "Third Answer",
                        "Fourth Answer",
                        1,
                        "Correct Answer is First Answer",
                        0,
                        "Test Category 1",
                        null,
                        0
                ),
                new Question(
                        "Test Question 2",
                        "First Answer",
                        "Second Answer",
                        "Third Answer",
                        "Fourth Answer",
                        2,
                        "Correct Answer is Second Answer",
                        0,
                        "Test Category 2",
                        null,
                        0
                ),
                new Question(
                        "Test Question 3",
                        "First Answer",
                        "Second Answer",
                        "Third Answer",
                        "Fourth Answer",
                        3,
                        "Correct Answer is Third Answer",
                        0,
                        "Test Category 1",
                        null,
                        0
                ),
                new Question(
                        "Test Question 4",
                        "First Answer",
                        "Second Answer",
                        "Third Answer",
                        "Fourth Answer",
                        4,
                        "Correct Answer is Fourth Answer",
                        0,
                        "Test Category 2",
                        null,
                        0
                )
        );
    }

    @Test
    //TODO georg
    public void environmentCategoryChosenShouldMatchCategoryInQuiz(){
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());
        // when custom quiz, with category = "Environment"
        //check list and if all questions have category env = pass
        String categoryChosen = "Environment";
        int questionCount = 10;

        List<Question> categorySpecificList = QuizState.instance.customQuiz(questionCount, categoryChosen, db, new ArrayList<>());
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }
        db.close();
        //if each question has the correct category based on checking each category = true
        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    //TODO georg
    public void trafficRulesCategoryChosenShouldMatchCategoryInQuiz(){
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String categoryChosen = "Basic Traffic Rules and Signs";
        int questionCount = 10;

        List<Question> categorySpecificList = QuizState.instance.customQuiz(questionCount, categoryChosen, db, new ArrayList<>());
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }
        db.close();
        //if each question has the correct category based on checking each category = true
        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    //TODO georg
    public void safeDrivingCategoryChosenShouldMatchCategoryInQuiz(){
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String categoryChosen = "Safety and Best Practices";
        int questionCount = 10;

        List<Question> categorySpecificList = QuizState.instance.customQuiz(questionCount, categoryChosen, db, new ArrayList<>());
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }
        db.close();
        //if each question has the correct category based on checking each category = true
        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    //TODO Ansis
    public void onCorrectAnswerShouldIncrementScore(){

    }

    /**
     * Start Quiz with score 32
     * After answering incorrectly, score should remain 32
     */
    @Test
    //TODO ansis
    public void onWrongAnswerShouldNotIncrementScore(){
        int expectedScore = 32;

        List<Question> questions = getTestableQuestions();
        UserAnswer incorrectAnswer = new UserAnswer(3, false);
        QuizState quiz = new QuizState(true, questions, null, 32);

        String failMessage = "Quiz with starting score 32\n" +
                "Current Question has correct answer at index: " + quiz.getCurrentQuestion().getCorrectAnswer() + "\n" +
                "Answered Question has index: " + incorrectAnswer.getIndex() + "\n" +
                "Quiz should still have score " + expectedScore + "\n";

        quiz.answerCurrentQuestion(incorrectAnswer);
        int actualScore = quiz.getScore();
        Assert.assertEquals(failMessage, expectedScore, actualScore);
    }

    @Test
    //TODO georg
    public void onWrongAnswerShouldSetNeedsReviewField(){
        Question question = new Question("test", "1", "2",
                                "3", "4", 2,
                                "test", 0, "test", "test", 0);

        QuizState.instance.answerQuestion(question, new UserAnswer(1, false), true);
        Assert.assertEquals(2, question.getNeedsReview());
        //somehow get a question
        //answer question wrongly then check its needsReview field
        //expected value = 2
    }

    @Test
    //TODO georg
    public void onCorrectAnswerShouldDecreaseNeedsReviewField(){
        Question question = new Question("test", "1", "2",
                "3", "4", 2,
                "test", 2, "test", "test", 0);

        QuizState.instance.answerQuestion(question, new UserAnswer(2, true), true);
        Assert.assertEquals(1, question.getNeedsReview());
        //create question with having set review = 2
        //answer that question correctly
        //expected value = 1
    }

    @Test
    //TODO georg
    public void onSecondCorrectAnswerShouldRemoveNeedsReviewValue(){
        Question question = new Question("test", "1", "2",
                "3", "4", 2,
                "test", 1, "test", "test", 0);

        QuizState.instance.answerQuestion(question, new UserAnswer(2, true), true);
        Assert.assertEquals(0, question.getNeedsReview());
        //using existing question with set review = 1
        //answer question correctly
        //expected value = 0
    }

    @Test
    //TODO georg
    public void allQuestionsShouldHaveValidNeedsReviewValue(){
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());
        for(Question question : db.getAllQuestions()){
            if(question.getNeedsReview() > 2 || question.getNeedsReview() < 0){
                //one question has a bad needsReview value meaning test fails
                db.close();
                assert false;
            }
        }
        db.close();
        assert true;
    }

    @Test
    //TODO ansis
    public void theoryExamShouldContainSetAmountOfQuestions(){}

    //for all customQuiz tests --- DONT FORGET EDGE CASES (negative vlaues, invalid values, missing)

    @Test
    //TODO ansis
    public void onCustomQuizWhenSelectingBothOptionsCustomQuizReflectsChoices(){}

    @Test
    //TODO ansis
    public void onCustomQuizWhenSelectingQuestionCountOptionCustomQuizReflectsChoice(){}

    @Test
    //TODO ansis
    public void onCustomQuizWhenSelectingCategoryOptionCustomQuizReflectsChoice(){}

    @Test
    //TODO ansis
    public void onCustomQuizWhenSelectingNoOptionsCustomQuizShouldChooseRandomly(){}
}

