package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class PracticeTheoryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Dialog settingsDialog;
    private ImageView settingsButton;

    private int questionCount = 0;
    private String categoryValue = "";

    //Countdown timer
    public static final int TEN_MIN_IN_MILLIS = 600000;
    public static final int FIFTEEN_MIN_IN_MILLIS = 900000;
    public static final int TWENTY_MIN_IN_MILLIS = 1200000;
    private TextView timer;
    private Switch enableTimer;
    private Dialog timerDialog;
    private Button tenMin, fifteenMin, twentyMin;
    private Spinner spin;



    private static int MILLIS = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MILLIS = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_theory);
        goToQuiz();

        //Pass values to next screen for display, db query, and textview display
        //TODO: UI for selecting category, option screen setting questions
        //TODO: add textView to show each questions' category
        Intent intent = this.getIntent();
        intent.putExtra("option_timer", 0);
        intent.putExtra("option_numOfQuestions", 0);
        intent.putExtra("option_category", "categoryName");

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

                String[] category = {"No Category", "Driving Safety and Best Practices", "Environment", "Basic Traffic Rules and Signs"};

                spin = settingsDialog.findViewById(R.id.dropDown);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //
                    }
                });

                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, category);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spin.setAdapter(aa);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText enterQnumber = settingsDialog.findViewById(R.id.editTextNumber);

                        if(!enterQnumber.getText().toString().equals("")){
                            questionCount = Integer.parseInt(enterQnumber.getText().toString());
                            if(questionCount > 10 || questionCount < 1){
                                TextView t = settingsDialog.findViewById(R.id.warningForNums);
                                t.setText("Enter a number ranging between 1 - 10");
                                t.setTextColor(Color.RED);
                            }
                        }
                        categoryValue = spin.getSelectedItem().toString();
                        settingsDialog.cancel();
                        Toast.makeText(getBaseContext(),
                                "Setting successfully updated!",
                                Toast.LENGTH_SHORT).show();
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
                //if the user hasnt entered the settings
                if(questionCount == 0 && categoryValue.equals("")){
                    Toast.makeText(getBaseContext(),
                            "No settings chosen -- Loading Random Quiz",
                            Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
                intent.putExtra("TIMER_VALUE", MILLIS);
                intent.putExtra("OPTION_QUESTIONS", questionCount);
                if(categoryValue.equals("")) intent.putExtra("CATEGORY_SELECTED", "");
                else intent.putExtra("CATEGORY_SELECTED", categoryValue);

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