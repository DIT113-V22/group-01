// Class for creating categories, into which Questions are grouped
public class Category {

    private String name;
    private boolean isReview; // category for incorrectly answered questions
    private boolean isExam;
    // List<Question> questions; // would be nicer

    // Constructor with all fields
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
        return "{" +
                " name='" + getName() + "'" +
                ", isReview='" + isIsReview() + "'" +
                ", isExam='" + isIsExam() + "'" +
                "}";
    }

}