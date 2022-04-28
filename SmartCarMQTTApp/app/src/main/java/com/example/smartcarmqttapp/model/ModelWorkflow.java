package com.example.smartcarmqttapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ModelWorkflow {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void init() {

        Category category = new Category("Signs", false, false); // probably better to compose Question in Category
        // otherwise have to iterate over questions and check for category

        HashMap<Integer, String> mappyMap = new HashMap<>();
        mappyMap.put(1, "ponder life");
        mappyMap.put(2, "stop");
        mappyMap.put(3, "go to dentist");
        mappyMap.put(4, "full throttle");

        Question shouldStop = new Question(
                "What should you do at a STOP sign?",
                "STOP sign implies the vehicle should be completely stopped before continuing driving.",
                mappyMap, // here could have map of indices and UserAnswer objs
                2,
                null,
                category);

        UserAnswer ponderLife = new UserAnswer(1, false, LocalDate.now());
        UserAnswer stopAtSign = new UserAnswer(2, true, LocalDate.now());
        UserAnswer goToDentist = new UserAnswer(3, false, LocalDate.now());
        UserAnswer fullThrottle = new UserAnswer(4, false, LocalDate.now());

    }
}
