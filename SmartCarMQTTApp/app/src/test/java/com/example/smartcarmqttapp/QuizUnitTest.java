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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void environmentCategoryChosenShouldMatchCategoryInQuiz() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String categoryChosen = "Environment";
        int questionCount = 10;

        List<Question> categorySpecificList = QuizState.instance.customQuiz(questionCount, categoryChosen, db);
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }
        db.close();

        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    public void trafficRulesCategoryChosenShouldMatchCategoryInQuiz() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String categoryChosen = "Basic Traffic Rules and Signs";
        int questionCount = 10;

        List<Question> categorySpecificList = QuizState.instance.customQuiz(questionCount, categoryChosen, db);
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }
        db.close();

        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    public void safeDrivingCategoryChosenShouldMatchCategoryInQuiz() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String categoryChosen = "Safety and Best Practices";
        int questionCount = 10;

        List<Question> categorySpecificList = QuizState.instance.customQuiz(questionCount, categoryChosen, db);
        int correctCategoryQuestions = 0;
        for(Question question : categorySpecificList){
            if(question.getCategory().equals(categoryChosen)){
                correctCategoryQuestions++;
            }
        }
        db.close();

        Assert.assertEquals(questionCount, correctCategoryQuestions);
    }

    @Test
    public void onCorrectAnswerShouldIncrementScore() throws Exception {
        int startingScore = 15;
        int expectedScore = 16;

        List<Question> questions = getTestableQuestions();
        UserAnswer correctAnswer = new UserAnswer(1, true);
        QuizState quiz = new QuizState(true, questions, null, startingScore);

        String failMessage = "Quiz with starting score " + startingScore + "\n" +
                "Current Question has correct answer at index: " + quiz.getCurrentQuestion().getCorrectAnswer() + "\n" +
                "Answered Question has index: " + correctAnswer.getIndex() + "\n" +
                "Quiz should now have score " + expectedScore + "\n";

        quiz.answerCurrentQuestion(correctAnswer);
        int actualScore = quiz.getScore();
        Assert.assertEquals(failMessage, expectedScore, actualScore);
    }

    @Test
    public void onWrongAnswerShouldNotIncrementScore() throws Exception {
        int startingScore = 32;
        int expectedScore = 32;

        List<Question> questions = getTestableQuestions();
        UserAnswer incorrectAnswer = new UserAnswer(3, false);
        QuizState quiz = new QuizState(true, questions, null, startingScore);

        String failMessage = "Quiz with starting score " + startingScore + "\n" +
                "Current Question has correct answer at index: " + quiz.getCurrentQuestion().getCorrectAnswer() + "\n" +
                "Answered Question has index: " + incorrectAnswer.getIndex() + "\n" +
                "Quiz should still have score " + expectedScore + "\n";

        quiz.answerCurrentQuestion(incorrectAnswer);
        int actualScore = quiz.getScore();
        Assert.assertEquals(failMessage, expectedScore, actualScore);
    }

    @Test
    public void onWrongAnswerShouldSetNeedsReviewField() throws Exception {
        Question question = new Question("test", "1", "2",
                                "3", "4", 2,
                                "test", 0, "test", "test", 0);

        QuizState.instance.answerQuestion(question, new UserAnswer(1, false), true);

        Assert.assertEquals(2, question.getNeedsReview());
    }

    @Test
    public void onCorrectAnswerShouldDecreaseNeedsReviewField() throws Exception {
        Question question = new Question("test", "1", "2",
                "3", "4", 2,
                "test", 2, "test", "test", 0);

        QuizState.instance.answerQuestion(question, new UserAnswer(2, true), true);
        
        Assert.assertEquals(1, question.getNeedsReview());
    }

    @Test
    public void onSecondCorrectAnswerShouldRemoveNeedsReviewValue() throws Exception {
        Question question = new Question("test", "1", "2",
                "3", "4", 2,
                "test", 1, "test", "test", 0);

        QuizState.instance.answerQuestion(question, new UserAnswer(2, true), true);

        Assert.assertEquals(0, question.getNeedsReview());
    }

    @Test
    public void allQuestionsShouldHaveValidNeedsReviewValue() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());
        for(Question question : db.getAllQuestions()){
            if(question.getNeedsReview() > 2 || question.getNeedsReview() < 0){
                //needsReview can only have a minimum value of 0 and maximum of 2
                db.close();

                assert false;
            }
        }
        db.close();

        assert true;
    }

    @Test
    public void theoryExamShouldContainSetAmountOfQuestions() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String category = "No Category"; // Parameters for theory exam
        int questionCount = 45;

        List<Question> examQuestions = QuizState.instance.customQuiz(questionCount, category, db);
        db.close();

        int expectedNumberOfQuestions = 45;
        int actualNumberOfQuestions = examQuestions.size();

        String failMessage = "Theory Exam should have " + questionCount + " questions but received " + actualNumberOfQuestions + " questions";
        Assert.assertEquals(failMessage, expectedNumberOfQuestions, actualNumberOfQuestions);
    }

    @Test
    public void theoryExamShouldContainQuestionsFromEveryCategory() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String category = "No Category"; // Parameters for theory exam
        int questionCount = 45;

        List<Question> examQuestions = QuizState.instance.customQuiz(questionCount, category, db);
        db.close();

        Map<String, Integer> foundCategories = new HashMap<>();
        for(Question question: examQuestions) {
            String questionCategory = question.getCategory();
            if(foundCategories.containsKey(questionCategory)) {
                foundCategories.put(questionCategory, foundCategories.get(questionCategory) + 1);
            } else {
                foundCategories.put(questionCategory, 1);
            }
        }

        Integer[] expectedNumberOfQuestions = new Integer[]{15, 15, 15};
        Integer[] actualNumberOfQuestions = foundCategories.values().toArray(new Integer[]{});

        String failMessage = "Question distribution for every category should be " + Arrays.toString(expectedNumberOfQuestions) + "\n" +
                "but received " + Arrays.toString(actualNumberOfQuestions);

        Assert.assertArrayEquals(failMessage, expectedNumberOfQuestions, actualNumberOfQuestions);
    }

    @Test
    public void onCustomQuizWhenSelectingBothOptionsCustomQuizReflectsChoices() throws Exception {
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String category = "Environment"; // Both category and question count selected
        int questionCount = 5;

        List<Question> chosenQuestions = QuizState.instance.customQuiz(questionCount, category, db);
        db.close();

        boolean isCorrectCategory = true;
        for(Question question: chosenQuestions) {
            isCorrectCategory = isCorrectCategory && question.getCategory().equals(category);
        }

        int expectedNumberOfQuestions = 5;
        int actualNumberOfQuestions = chosenQuestions.size();

        String baseFailMessage = "Starting Custom Quiz on " + category + " (" + questionCount + " questions)\n";
        String questionFailMessage = baseFailMessage + "Quiz should have " + expectedNumberOfQuestions + " questions but received " + actualNumberOfQuestions;
        String categoryFailMessage = baseFailMessage + "Quiz should only have " + category + " questions, but received others.";

        Assert.assertEquals(questionFailMessage, expectedNumberOfQuestions, actualNumberOfQuestions);
        Assert.assertTrue(categoryFailMessage, isCorrectCategory);
    }

    @Test
    public void onCustomQuizWhenSelectingQuestionCountOptionCustomQuizReflectsChoice() throws Exception{
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String category = "No Category"; // Only number of questions selected
        int questionCount = 10;

        List<Question> chosenQuestions = QuizState.instance.customQuiz(questionCount, category, db);
        db.close();

        int expectedNumberOfQuestions = questionCount;
        int actualNumberOfQuestions = chosenQuestions.size();

        String failMessage = "Starting Custom quiz with " + questionCount + " questions but only received " + actualNumberOfQuestions + " questions";

        Assert.assertEquals(failMessage, expectedNumberOfQuestions, actualNumberOfQuestions);
    }

    @Test
    public void onCustomQuizWhenSelectingCategoryOptionCustomQuizReflectsChoice() throws Exception{
        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());

        String category = "Basic Traffic Rules and Signs"; // Only category selected
        int questionCount = 0;

        List<Question> chosenQuestions = QuizState.instance.customQuiz(questionCount, category, db);
        db.close();

        boolean isCorrectCategory = true;
        for(Question question: chosenQuestions) {
            isCorrectCategory = isCorrectCategory && question.getCategory().equals(category);
        }

        String failMessage = "Quiz should only have " + category + " questions, but received others.";

        Assert.assertTrue(failMessage, isCorrectCategory);
    }

//    @Test
//    //TODO ansis
//    public void onCustomQuizWhenSelectingNoOptionsCustomQuizShouldReturnQuestions() throws Exception{
//        CrushersDataBase db = new CrushersDataBase(quizQuestionActivity.getApplicationContext());
//
//        String category = "No Category"; // Both category and question count selected
//        int questionCount = 0;
//
//        List<Question> chosenQuestions = QuizState.instance.customQuiz(questionCount, category, db, new ArrayList<>());
//        db.close();
//
//        boolean questionsExist = chosenQuestions.size() != 0;
//
//        String failMessage = "Starting Custom Quiz with no options\n" +
//                "Should return some questions but did not";
//
//        Assert.assertTrue(failMessage, questionsExist);
//    }
}

