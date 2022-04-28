// Class for defining the current state of the quiz: in particular what questions are part of the quiz and what answers have been accumulated, as well as the current score.
public class QuizState {

    private boolean isTakingQuiz;
    private List<Question> questions;
    private List<UserAnswer> currentAnswers;
    private int score;
    private int currentPointer;

    // Constructor with all fields
    public QuizState(
            boolean isTakingQuiz,
            List<Question> questions,
            List<UserAnswer> currentAnswers,
            int score) {
        this.isTakingQuiz = isTakingQuiz;
        this.questions = questions;
        this.currentAnswers = currentAnswers;
        this.score = score;
        this.currentPointer = 0;
    }

    // Empty constructor
    public QuizState() {

    }

    /**
     * Helper methods
     */

    public void answerQuestion(UserAnswer answer) {
        if (questions.get(currentPointer).getCorrectIndex() == answer.getIndex()) {
            incrementScore();
        }
        currentAnswers.add(answer); // adds answer to list of current answers
        incrementCurrentPointer(); // increases question number
    }

    private void incrementScore() {
        this.score++;
        // Made method separate in case UI actions needed
    }

    private void incrementCurrentPointer() {
        this.currentPointer++;
        if (this.currentPointer == this.questions.size())
            finishQuiz();
        // Made method separate in case UI actions needed
    }

    public void finishQuiz() {
        // ToDo: add logic for finishing quiz
        // public in case we want to add a button for stopping quiz, so it can be
        // accessed anywhere
    }

    public int calculateCurrentScore() {
        // Score is already updated, but method added just in case
        int calcScore = 0;
        for (int i = 0; i < currentAnswers.size(); i++) {
            if (questions.get(i).getCorrectIndex() == currentAnswers.get(i).getIndex()) {
                calcScore++;
            }
        }
        return calcScore;
    }

    /**
     * Getters and Setters
     */

    public boolean isIsTakingQuiz() {
        return this.isTakingQuiz;
    }

    public boolean getIsTakingQuiz() {
        return this.isTakingQuiz;
    }

    public void setIsTakingQuiz(boolean isTakingQuiz) {
        this.isTakingQuiz = isTakingQuiz;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<UserAnswer> getCurrentAnswers() {
        return this.currentAnswers;
    }

    public void setCurrentAnswers(List<UserAnswer> currentAnswers) {
        this.currentAnswers = currentAnswers;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentPointer() {
        return this.currentPointer;
    }

    public void setCurrentPointer(int currentPointer) {
        this.currentPointer = currentPointer;
    }

    @Override
    public String toString() {
        return "{" +
                " isTakingQuiz='" + isIsTakingQuiz() + "'" +
                ", questions='" + getQuestions() + "'" +
                ", currentAnswers='" + getCurrentAnswers() + "'" +
                ", score='" + getScore() + "'" +
                ", currentPointer='" + getCurrentPointer() + "'" +
                "}";
    }

}
