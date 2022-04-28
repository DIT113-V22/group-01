package com.example.smartcarmqttapp.model;
import java.time.LocalDateTime;

// Class for creating each possible answer
public class UserAnswer {

    private int index;
    private boolean isCorrect;
    private LocalDateTime time;
    // private String content; // remove index

    // Constructor with every field
    public UserAnswer(
            int index,
            boolean isCorrect,
            LocalDateTime time) {
        this.index = index;
        this.isCorrect = isCorrect;
        this.time = time;
    }

    // Empty constructor
    public UserAnswer() {

    }

    /**
     * Getters and Setters
     */
    public int getIndex() {
        return this.index;
    }

    public boolean getIsCorrect() {
        return this.isCorrect;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                " index='" + getIndex() + "'" +
                ", isCorrect='" + getIsCorrect() + "'" +
                ", time='" + getTime() + "'" +
                "}";
    }

}
