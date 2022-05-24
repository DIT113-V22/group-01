package com.example.smartcarmqttapp;

import android.content.Context;

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

import java.util.List;

@RunWith(JUnit4.class)
public class QuizUnitTest {

    //fix this either using Robolectric testing (doesnt work atm) or something else
    // cannot create instantiated object of a android view class
    private QuizQuestionActivity quizQuestionActivity;

    @Test
    //TODO georg
    public void environmentCategoryChosenShouldMatchCategoryInQuiz(){
        //insert test here
        // when custom quiz, with category = "Environment"
        //check list and if all questions have category env = pass
        String categoryChosen = "Environment";
        int questionCount = 10;

        List<Question> categorySpecificList = quizQuestionActivity.customQuiz(questionCount, categoryChosen);
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }

        //if each question has the correct category based on checking each category = true
        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    //TODO georg
    public void trafficRulesCategoryChosenShouldMatchCategoryInQuiz(){
        String categoryChosen = "Basic Traffic Rules and Signs";
        int questionCount = 10;

        List<Question> categorySpecificList = quizQuestionActivity.customQuiz(questionCount, categoryChosen);
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }

        //if each question has the correct category based on checking each category = true
        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    //TODO georg
    public void safeDrivingCategoryChosenShouldMatchCategoryInQuiz(){
        String categoryChosen = "Safety and Best Practices";
        int questionCount = 10;

        List<Question> categorySpecificList = quizQuestionActivity.customQuiz(questionCount, categoryChosen);
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }

        //if each question has the correct category based on checking each category = true
        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    //TODO Ansis
    public void onCorrectAnswerShouldIncrementScore(){}

    @Test
    //TODO ansis
    public void onWrongAnswerShouldNotIncrementScore(){}

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
        //get all questions
        //check if their needs review value > 0 and < 2
        /*
        CrushersDataBase db = new CrushersDataBase();
        for(Question question : db.getAllQuestions()){
            if(question.getNeedsReview() > 2 || question.getNeedsReview() < 0){
                //one question has a bad needsReview value meaning test fails
                assert false;
            }
        }
        assert true;
         */
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

