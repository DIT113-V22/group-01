package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizResultActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button home, again;
    private TextView result;
    private TextView score;
    private TextView total_question;
    private TextView quiz_time;

    //Data from quiz
    private int scoreNumber;
    private int totalQuestions;
    private int total_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        result = findViewById(R.id.result);

        score = findViewById(R.id.score);

        total_question = findViewById(R.id.totalQuestions);

        quiz_time = findViewById(R.id.quizTime);

        home = findViewById(R.id.home_btn);
        again = findViewById(R.id.again_btn);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PracticeTheoryActivity.class));
            }
        });

        //Result values from quiz activity
        Intent intent = getIntent();
        scoreNumber = intent.getIntExtra("Score", 0);
        totalQuestions = intent.getIntExtra("Total_questions", 45);
        total_time = intent.getIntExtra("Time_taken", 0);

        score.setText(Integer.toString(scoreNumber));
        total_question.setText(Integer.toString(totalQuestions));
        quiz_time.setText(Integer.toString(total_time));



        /*
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceTheory);

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

         */
    }

    //
}