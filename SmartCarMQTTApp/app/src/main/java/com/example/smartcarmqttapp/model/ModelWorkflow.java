package com.example.smartcarmqttapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class ModelWorkflow {

    public static void init() {

        /*
        Example of how our model might be initialized:
        -Create Categories
        -Create Questions and give them Category references
        -Create QuizState and add questions to it
        -Create UserAnswers and submit them
         */

        // Create categories
        Category reviewCategory = new Category("Review Category", true, false);
        Category signsCategory = new Category("Signs Category", false, false);

        // Create questions
        Question q1 = new Question(
                "What's the meaning of life?",
                "The meaning of life is the number 42 because the internet says so",
                "69",
                "42",
                "to go to the dentist",
                "to be the very best",
                2,
                signsCategory,
                0
                );

        Question q2 = new Question(
                "What does a STOP sign mean?",
                "A vehicle must be stopped at a STOP sign completely before continuing",
                "idk",
                "42",
                "Vehicle must be stopped",
                "idk but i like this button better",
                3,
                signsCategory,
                0
        );

        // Create a quiz with two questions
        QuizState firstQuiz = new QuizState(
                true,
                Arrays.asList(q1, q2),
                new ArrayList<>(),
                0
        );

        // For each question, create answers
        UserAnswer answer1 = new UserAnswer(1, false);
        UserAnswer answer2 = new UserAnswer(2, true);
        UserAnswer answer3 = new UserAnswer(3, false);
        UserAnswer answer4 = new UserAnswer(4, false);

        firstQuiz.getCurrentQuestion(); // display the current question
        firstQuiz.answerQuestion(answer1); // answer the current question
        // if correct, increment score. increments pointer
        // else make question reviewable. increments pointer
        // ... repeat until all questions are finished then display firstQuiz.getScore();




    }
}
