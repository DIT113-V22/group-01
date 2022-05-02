package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class PracticeTheoryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    //Countdown timer
    public static final int TEN_MIN_IN_MILLIS = 600000;
    public static final int FIFTEEN_MIN_IN_MILLIS = 900000;
    public static final int TWENTY_MIN_IN_MILLIS = 1200000;
    private TextView timer;
    private Switch enableTimer;
    private Dialog timerDialog;
    private Button tenMin, fifteenMin, twentyMin;

    private static int MILLIS;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_theory);
        goToQuiz();
        CrushersDataBase db = new CrushersDataBase(this);
        List<Question> questions = db.getAllQuestions();

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

        //Countdown timer
        enableTimer = findViewById(R.id.enableTimer);

        tenMin = findViewById(R.id.tenMin);

        fifteenMin = findViewById(R.id.fifteenMin);

        twentyMin = findViewById(R.id.twentyMin);

        timerDialog = new Dialog(this);

        timer = findViewById(R.id.timer);

        enableTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enableTimer.isChecked()) {
                    timerDialog.setContentView(R.layout.timer_dialog);
                    timerDialog.show();
                }
                else {
                    timerDialog.cancel();
                    timer.setText("00:00");
                }
            }
        });


    }

    public void goToQuiz(){
        Button button = findViewById(R.id.practiceQuiz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to quiz screen
                //TODO: start the Quiz State
                //QuizState.instance.startQuiz();
                Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
                intent.putExtra("TIMER_VALUE", MILLIS);
                startActivity(intent);

            }
        });
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.tenMin:
                MILLIS = TEN_MIN_IN_MILLIS;
                timerDialog.cancel();
                break;
            case R.id.fifteenMin:
                MILLIS = FIFTEEN_MIN_IN_MILLIS;
                timerDialog.cancel();
                break;
            case R.id.twentyMin:
                MILLIS = TWENTY_MIN_IN_MILLIS;
                timerDialog.cancel();
                break;
            default:
                break;
        }

    }

}