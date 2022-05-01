package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.smartcarmqttapp.model.UserAnswer;
import com.example.smartcarmqttapp.state.QuizState;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class QuizQuestionActivity extends AppCompatActivity {

    private TextView questionCountText;
    private TextView scoreText;
    private TextView timer;
    private ImageView questionImage;
    private int scoreNumber = 0;

    //Radio buttons
    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private RadioButton explanationButton;

    //correct answer choice from radio group (1,2,3, or 4)
    private String correctAns;
    private int correctAnswer;

    //TODO: get total questions from QuizState.instance.getQuestionCount
    private int currentQuestionNum = 0;
    private int clicks = 0;
    private int totalQuestions;

    private Drawable right;
    private Drawable wrong;

    private List<Question> questionList;
    private TooltipCompat tooltipCompat;

    private BottomNavigationView bottomNavigationView;
    private QuizState quizState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        right = getDrawable(R.drawable.correct_border);
        wrong = getDrawable(R.drawable.wrong_border);

        questionCountText = findViewById(R.id.questionCount);
        //questionsLeftText.setText(totalQuestions);

        scoreText = findViewById(R.id.score);
        scoreText.setText(Integer.toString(scoreNumber));
        timer = findViewById(R.id.header);
        questionImage = findViewById(R.id.questionImage);

        //Radio buttons
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        radioGroup = findViewById(R.id.radioGroup);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceTheory);

        //add questions to question list via helper method --> help us select question
        CrushersDataBase db = new CrushersDataBase(this);
        questionList = db.getAllQuestions();
        quizState = new QuizState(true, questionList, null, scoreNumber);
        totalQuestions = questionList.size();

        addQuestion();
        onNextQuestionButtonClicked();


        //TODO for @Lancear: Add a listener/if statement that essentially asks the user whether they are sure
        //TODO for @Lancear: Add dialog box with 2 buttons: yes -> driving theory screen, no -> back to current question
        //TODO for @Lancear: Decide where exactly dialog comes up: clicking bottom navigation bar, ... or another quit button at each question?
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.connectedCar:
                        startActivity(new Intent(getApplicationContext(), ConnectedCarActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.practiceDriving:
                        startActivity(new Intent(getApplicationContext(), PracticeDrivingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.practiceTheory:
                        return true;

                    case R.id.aboutUs:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * instantiates two buttons: finishQuizButton and checkAnswerBtn
     *
     * @button checkAnswerBtn finalizes the user's choice and makes sure that
     * the radio buttons are disabled so that the user cannot choose another answer
     *
     * @button finishQuizButton moves on to the next question, provided the user
     * has selected an answer and clicked the checkAnswerBtn. If that is not the case
     * then the user is prompted with if they want to skip the question. If skipped, that
     * question is marked as incorrect and stored as an incorrect answer.
     */
    public void onNextQuestionButtonClicked() {
        Button nextQuestionButton = findViewById(R.id.nextQuestionBTN);
        Button checkAnswerBtn = findViewById(R.id.checkAnswer);

        explanationButton = findViewById(correctAnswer);
        //TODO: set the correct answer, based on query, to have onclick listener with explanation
        explanationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option1.setTooltipText("Ipsum lorens, this should explain the nature of why the chosen option is correct");
            }
        });

        if(currentQuestionNum == totalQuestions){
            nextQuestionButton.setText("Finish Quiz");
        }

        checkAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView selectQ = findViewById(R.id.selectQuestion);
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    selectQ.setText("Select a question or skip by pressing 'Next Question' twice");
                } else {
                    selectQ.setText("");
                    //Set skip warning to transparent
                    TextView textView = findViewById(R.id.areYouSure);
                    textView.setText("");

                    //After confirmation of answer, cant select any other question
                    option1.setClickable(false);
                    option2.setClickable(false);
                    option3.setClickable(false);
                    option4.setClickable(false);

                    //show the user that they also cant re-press the button
                    Drawable drawable = getDrawable(R.drawable.button_border);
                    checkAnswerBtn.setBackground(drawable);

                    //switch case for setting style of correct answer
                    if (radioGroup.getCheckedRadioButtonId() == correctAnswer) {
                        scoreNumber++;
                    } else {
                        quizState.answerQuestion(new UserAnswer(currentQuestionNum, false));
                    }

                    switch (correctAnswer) {
                        case R.id.option1:
                            withBorderOpt1();
                            break;
                        case R.id.option2:
                            withBorderOpt2();
                            break;
                        case R.id.option3:
                            withBorderOpt3();
                            break;
                        case R.id.option4:
                            withBorderOpt4();
                            break;
                    }
                }
            }
        });

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerBtn.setBackgroundResource(android.R.drawable.btn_default);
                //if radio buttons are disabled or there were two clicks on next question button (skip)

                if(!option1.isClickable() || clicks == 1){
                    if(clicks == 1){
                        //if question was skipped the current question is flagged as 'incorrect'
                        quizState.answerQuestion(new UserAnswer(quizState.getCurrentPointer(), false));
                    }
                    //reset the skip feature
                    clicks = 0;

                    //When the amount of questions finish
                    if (currentQuestionNum == totalQuestions) {
                        //when the question count finished, go to the results screen
                        startActivity(new Intent());
                        //TODO: reset the QuizState class as a quiz is Terminated
                        //TODO: call results screen and set the back or exit button to go back to home screen
                    }

                    if (timer.getText().equals("0:00")){
                        //TODO: reset the QuizState class as a quiz is Terminated
                        //TODO for @Lancear: add logic for when the timer reaches zero -> goes to result screen
                        //TODO for @Lancear: move this in a method where it loops, checking timer until it reaches zero (talk to ivan about it)
                    }

                    resetRadioButtons();
                    addQuestion();
                    //TODO: call method for getting new question, after passing previous checks for quiz completion
                }
                else{
                    //Set text to say: please confirm an answer or click again to skip
                    TextView areYouSure = findViewById(R.id.areYouSure);
                    areYouSure.setText("Are you sure you want to skip?");
                    clicks++;
                }
            }
        });
    }

    /**
     * Adding a question to the question template
     *
     * Updates fields:
     *  - current question number
     *  - total questions
     *  - score
     *  - Question
     *  - all answer choices
     *  - current answer (assigns correct answer to check which radio button is correct
     */
    public void addQuestion(){
        radioGroup.clearCheck();

        Question currentQuestion = quizState.getCurrentQuestion();
        questionCountText.setText((quizState.getCurrentPointer() + 1) + " / " + quizState.getQuestions().size());
        scoreText.setText(Integer.toString(scoreNumber));

        //this makes sure that when the answer is checked
        //it can correctly color the correct answer and wrong answers
        correctAnswer = currentQuestion.getCorrectAnswer();
        switch(correctAnswer){
            case 1:
                correctAnswer = option1.getId();
                break;
            case 2:
                correctAnswer = option2.getId();
                break;
            case 3:
                correctAnswer = option3.getId();
                break;
            case 4:
                correctAnswer = option4.getId();
                break;
        }
        //sets all the textFields to the current question
        questionImage.setImageBitmap(null);
        TextView textView = findViewById(R.id.textReplacingImage);
        textView.setText(currentQuestion.getQuestion());
        option1.setText(currentQuestion.getFirstAnswer());
        option2.setText(currentQuestion.getSecondAnswer());
        option3.setText(currentQuestion.getThirdAnswer());
        option4.setText(currentQuestion.getFourthAnswer());
    }

    public void resetRadioButtons(){
        option1.setClickable(true);
        option2.setClickable(true);
        option3.setClickable(true);
        option4.setClickable(true);
        
        option1.setBackground(null);
        option1.setTypeface(null, Typeface.NORMAL);
        option2.setBackground(null);
        option2.setTypeface(null, Typeface.NORMAL);
        option3.setBackground(null);
        option3.setTypeface(null, Typeface.NORMAL);
        option4.setBackground(null);
        option4.setTypeface(null, Typeface.NORMAL);

    }
    public void withBorderOpt1(){

        option1.setBackground(right);
        option1.setTypeface(null, Typeface.BOLD);
        option2.setBackground(wrong);
        option3.setBackground(wrong);
        option4.setBackground(wrong);
    }

    public void withBorderOpt2(){

        option1.setBackground(wrong);
        option2.setBackground(right);
        option2.setTypeface(null, Typeface.BOLD);
        option3.setBackground(wrong);
        option4.setBackground(wrong);
    }

    public void withBorderOpt3(){

        option1.setBackground(wrong);
        option2.setBackground(wrong);
        option3.setBackground(right);
        option3.setTypeface(null, Typeface.BOLD);
        option4.setBackground(wrong);
    }

    public void withBorderOpt4(){

        option1.setBackground(wrong);
        option2.setBackground(wrong);
        option3.setBackground(wrong);
        option4.setBackground(right);
        option4.setTypeface(null, Typeface.BOLD);
    }
}