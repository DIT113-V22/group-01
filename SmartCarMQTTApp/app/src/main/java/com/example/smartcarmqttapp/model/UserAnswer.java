package com.example.smartcarmqttapp.model;

// Class for creating each possible answer
public class UserAnswer {

    private int index;
    private boolean isCorrect;

    // Constructor with every field
    public UserAnswer(
            int index,
            boolean isCorrect
            ) {
        this.index = index;
        this.isCorrect = isCorrect;
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

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "index=" + index +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
