package com.example.smartcarmqttapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class QuizUnitTest {
    @Test
    //TODO georg
    public void environmentCategoryChosenShouldMatchCategoryInQuiz(){
        //insert test here
    }

    @Test
    //TODO georg
    public void trafficRulesCategoryChosenShouldMatchCategoryInQuiz(){
        //insert test here
    }

    @Test
    //TODO georg
    public void safeDrivingCategoryChosenShouldMatchCategoryInQuiz(){
        //insert test here
    }

    @Test
    //TODO Ansis
    public void onCorrectAnswerShouldIncrementScore(){}

    @Test
    //TODO ansis
    public void onWrongAnswerShouldNotIncrementScore(){}

    @Test
    //TODO georg
    public void onWrongAnswerShouldSetNeedsReviewField(){}

    @Test
    //TODO georg
    public void onCorrectAnswerShouldDecreaseNeedsReviewField(){}

    @Test
    //TODO georg
    public void onSecondCorrectAnswerShouldRemoveNeedsReviewValue(){}

    @Test
    //TODO georg
    public void allQuestionsShouldHaveValidNeedsReviewValue(){}

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

