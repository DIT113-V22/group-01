package com.example.smartcarmqttapp;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionsDAO {
    @Query("SELECT * FROM questions")
    List<Question> getAll();
}
