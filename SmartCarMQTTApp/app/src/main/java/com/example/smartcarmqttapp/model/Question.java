
// Class for creating each possible question and the answers & explanations to them
public class Question {

    private String question;
    private String explanation;
    private Map<Integer, String> possibleAnswers; // key: index and value: answer
    /*
     * PROPOSITION: make Question store index and UserAnswer have content
     * (Swap index to String content in UserAnswer)
     * private Map<Integer, UserAnswer> possibleAnswers;
     */
    private int correctIndex;
    private List<UserAnswer> previousAnswers;
    private Category category; // not in Acceptance Criteria but required

    // Constructor with all fields
    public Question(
            String question,
            String explanation,
            Map<Integer, String> possibleAnswers,
            int correctIndex,
            List<UserAnswer> previousAnswers,
            Category category) {
        this.question = question;
        this.explanation = explanation;
        this.possibleAnswers = possibleAnswers;
        this.correctIndex = correctIndex;
        this.previousAnswers = previousAnswers;
        this.category = category;
    }

    // Empty constructor
    public Question() {

    }

    /**
     * Getters and Setters
     */
    public String getQuestion() {
        return this.question;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Map<Integer, String> getPossibleAnswers() {
        return this.possibleAnswers;
    }

    public int getCorrectIndex() {
        return this.correctIndex;
    }

    public List<UserAnswer> getPreviousAnswers() {
        return this.previousAnswers;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setPossibleAnswers(String possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public void setPreviousAnswers(List<UserAnswer> previousAnswers) {
        this.previousAnswers = previousAnswers;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{" +
                " question='" + getQuestion() + "'" +
                ", explanation='" + getExplanation() + "'" +
                ", possibleAnswers='" + getPossibleAnswers() + "'" +
                ", correctIndex='" + getCorrectIndex() + "'" +
                ", previousAnswers='" + getPreviousAnswers() + "'" +
                ", category='" + getCategory() + "'" +
                "}";
    }

}
