package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizQuestionActivity extends AppCompatActivity {

    private TextView questionCount;
    private TextView questionsLeft;
    private TextView scoreText;
    private TextView timer;
    private ImageView questionImage;
    private TextView explanationText;
    private static int scoreNumber = 0;

    //Radio buttons
    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    //correct answer choice from radio group (1,2,3, or 4)
    private int correctAnswer;

    private Drawable right;
    private Drawable wrong;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        right = getDrawable(R.drawable.correct_border);
        wrong = getDrawable(R.drawable.wrong_border);

        questionCount = findViewById(R.id.questionCount);
        questionsLeft = findViewById(R.id.questionsLeft);
        scoreText = findViewById(R.id.score);
        scoreText.setText(Integer.toString(scoreNumber));
        timer = findViewById(R.id.timerText);
        questionImage = findViewById(R.id.questionImage);
        explanationText = findViewById(R.id.explanationText);

        //Radio buttons
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        radioGroup = findViewById(R.id.radioGroup);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceTheory);

        onNextQuestionButtonClicked();

        //TODO for @Lancear: Add a listener/if statement that essentially asks the user whether they are sure
        //TODO for @Lancear: Add dialog box with 2 buttons: yes -> driving theory screen, no -> back to current question
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

    public void onNextQuestionButtonClicked() {
        Button finishQuizButton = findViewById(R.id.nextQuestionBTN);
        finishQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option1.setClickable(false);
                option2.setClickable(false);
                option3.setClickable(false);
                option4.setClickable(false);

                Drawable drawable = getDrawable(R.drawable.button_border);
                finishQuizButton.setBackground(drawable);

                //for testing option 1 is correct
                correctAnswer = option1.getId();

                //switch case for setting style of correct answer
                if(radioGroup.getCheckedRadioButtonId() == correctAnswer){
                    scoreNumber++;
                }

                explanationText.setText("Explanation why right/wrong");
                final int option1 = R.id.option1;
                final int option2 = R.id.option2;
                final int option3 = R.id.option3;
                final int option4 = R.id.option4;

                switch(radioGroup.getCheckedRadioButtonId()) {
                    case option1:
                        withBorderOpt1();
                        break;
                    case option2:
                        withBorderOpt2();
                        break;
                    case option3:
                        withBorderOpt3();
                        break;
                    case option4:
                        withBorderOpt4();
                        break;
                }

                //When the amount of questions finish
                if (questionCount.getText().equals(questionsLeft.getText())) {
                    //when the question count finished, go to the results screen
                    startActivity(new Intent());
                    //TODO: call results screen and set the back or exit button to go back to home screen
                }
            }
        });
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