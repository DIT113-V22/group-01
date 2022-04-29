package com.example.smartcarmqttapp.model;

// Class for creating categories, into which Questions are grouped
public class Category {

    private int id;
    private String name;
    private boolean isReview; // category for incorrectly answered questions
    private boolean isExam;

    // Constructor with all fields
    public Category(
            int id,
            String name,
            boolean isReview,
            boolean isExam) {
        this.id = id;
        this.name = name;
        this.isReview = isReview;
        this.isExam = isExam;
    }

    // Constructor without id
    public Category(
            String name,
            boolean isReview,
            boolean isExam) {
        this.name = name;
        this.isReview = isReview;
        this.isExam = isExam;
    }

    // Empty constructor
    public Category() {

    }

    /**
     * Getters and Setters
     */

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsReview() {
        return this.isReview;
    }

    public boolean getIsExam() {
        return this.isExam;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsReview(boolean isReview) {
        this.isReview = isReview;
    }

    public void setIsExam(boolean isExam) {
        this.isExam = isExam;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", isReview=" + isReview +
                ", isExam=" + isExam +
                '}';
    }
}