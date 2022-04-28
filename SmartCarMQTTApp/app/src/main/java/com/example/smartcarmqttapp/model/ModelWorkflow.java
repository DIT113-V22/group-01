package com.example.smartcarmqttapp.model;

import java.time.LocalDateTime;
import java.util.Map;

public class ModelWorkflow {

    public static void init() {

        Category category = new Category("Signs", false, false); // probably better to compose Question in Category
        // otherwise have to iterate over questions and check for category

        Question shouldStop = new Question(
                "What should you do at a STOP sign?",
                "STOP sign implies the vehicle should be completely stopped before continuing driving.",
                Map.of(
                        1, "ponder life",
                        2, "stop",
                        3, "go to the dentist",
                        4, "full throttle"), // here could have map of indices and UserAnswer objs
                2,
                null,
                category);

        UserAnswer ponderLife = new UserAnswer(1, false, LocalDateTime.now());
        UserAnswer stopAtSign = new UserAnswer(2, true, LocalDateTime.now());
        UserAnswer goToDentist = new UserAnswer(3, false, LocalDateTime.now());
        UserAnswer fullThrottle = new UserAnswer(4, false, LocalDateTime.now());

    }
}
