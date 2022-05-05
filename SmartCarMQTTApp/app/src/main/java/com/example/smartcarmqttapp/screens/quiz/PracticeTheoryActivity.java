package com.example.smartcarmqttapp.screens.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.database.CrushersDataBase;
import com.example.smartcarmqttapp.model.Question;

import java.util.List;

public class PracticeTheoryActivity extends AppCompatActivity {

    private Dialog settingsDialog;
    private ImageView settingsButton;

    private int questionCount = 0;

    //Countdown timer
    public static final int TEN_MIN_IN_MILLIS = 600000;
    public static final int FIFTEEN_MIN_IN_MILLIS = 900000;
    public static final int TWENTY_MIN_IN_MILLIS = 1200000;
    private TextView timer;
    private Switch enableTimer;
    private Dialog timerDialog;
    private Button tenMin, fifteenMin, twentyMin;

    private static int MILLIS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_theory);
        Navigation.initializeNavigation(this, R.id.practiceTheory);

        goToQuiz();
        CrushersDataBase db = new CrushersDataBase(this);
        List<Question> questions = db.getAllQuestions();

        //Countdown timer
        enableTimer = findViewById(R.id.enableTimer);
        tenMin = findViewById(R.id.tenMin);
        fifteenMin = findViewById(R.id.fifteenMin);
        twentyMin = findViewById(R.id.twentyMin);
        timer = findViewById(R.id.timer);
        timerDialog = new Dialog(this);

        enableTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(enableTimer.isChecked()) {
                    timerDialog.setContentView(R.layout.timer_dialog);
                    timerDialog.show();
                }
                else {
                    timerDialog.cancel();
                    MILLIS = 0;
                }

            }
        });

        settingsDialog = new Dialog(this);
        settingsButton = findViewById(R.id.settingsImage);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.setContentView(R.layout.settings_dialog);
                settingsDialog.show();

                Button button = settingsDialog.findViewById(R.id.confirmBtn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText enterQnumber = settingsDialog.findViewById(R.id.editTextNumber);

                        questionCount = Integer.parseInt(enterQnumber.getText().toString());
                        if (questionCount > 10 || questionCount < 1){
                            TextView t = settingsDialog.findViewById(R.id.warningForNums);
                            t.setText("Enter a number ranging between 1 - 10");
                            t.setTextColor(Color.RED);
                        }
                        else {
                            settingsDialog.cancel();
                            Toast.makeText(getBaseContext(),
                                    "Setting successfully updated!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void goToQuiz(){
        Button button = findViewById(R.id.practiceQuiz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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