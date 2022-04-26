package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    private TextView timer;
    private ImageView questionImage;
    private TextView explanationText;

    //Radio buttons
    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    //correct answer choice from radio group (1,2,3, or 4)
    private int correctAnswer;


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        questionCount = findViewById(R.id.questionCount);
        questionsLeft = findViewById(R.id.questionsLeft);
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
                //for testing option 1 is correct
                correctAnswer = option1.getId();
                if (radioGroup.getCheckedRadioButtonId() == correctAnswer) {
                    option1.setTextColor(Color.GREEN);
                    option2.setTextColor(Color.RED);
                    option3.setTextColor(Color.RED);
                    option4.setTextColor(Color.RED);
                    radioGroup.setClickable(false);
                    explanationText.setText("");
                }
                else{
                    option1.setTextColor(Color.GREEN);
                    option2.setTextColor(Color.RED);
                    option3.setTextColor(Color.RED);
                    option4.setTextColor(Color.RED);
                    radioGroup.setClickable(false);
                    //set the explanation
                    explanationText.setText("Explanation why wrong");

                }

                if (questionCount.getText().equals(questionsLeft.getText())) {
                    //when the question count finished, go to the results screen
                    startActivity(new Intent());
                }
                try {
                    Thread.sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //load in another question
                //for debugging below
                startActivity(new Intent(QuizQuestionActivity.this, PracticeTheoryActivity.class));
            }
        });
    }
}