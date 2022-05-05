package com.example.smartcarmqttapp.screens.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smartcarmqttapp.R;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    public static final int TEN_MIN_IN_MILLIS = 600000;
    public static final int FIFTEEN_MIN_IN_MILLIS = 900000;
    public static final int TWENTY_MIN_IN_MILLIS = 1200000;
    private static int MILLIS;

    private TextView timerView;
    private Switch enableTimer;
    private Button startBtn;
    private Dialog timerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerView = findViewById(R.id.timerView);
        enableTimer = findViewById(R.id.enableTimer);
        startBtn = findViewById(R.id.start);
        timerDialog = new Dialog(this);

        enableTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enableTimer.isChecked()) {
                    timerDialog.setContentView(R.layout.timer_dialog);
                    timerDialog.show();
                }
                else {
                    timerDialog.cancel();
                    timerView.setText("00:00");
                }
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCountDown();
            }
        });
    }

    private void startCountDown() {
        new CountDownTimer(MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                MILLIS = (int) l;
                formatTimeView();
            }

            @Override
            public void onFinish() {
                timerView.setText("done");
            }
        }.start();
    }



    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.tenMin:
                timerView.setText("10:00");
                MILLIS = TEN_MIN_IN_MILLIS;
                break;
            case R.id.fifteenMin:
                timerView.setText("15:00");
                MILLIS = FIFTEEN_MIN_IN_MILLIS;
                break;
            case R.id.twentyMin:
                timerView.setText("20:00");
                MILLIS = TWENTY_MIN_IN_MILLIS;
                break;
            default:
                break;
        }

    }

    private void formatTimeView() {
        int minutes = (MILLIS / 1000) / 60;
        int seconds = (MILLIS / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerView.setText(timeLeftFormatted);
    }
}