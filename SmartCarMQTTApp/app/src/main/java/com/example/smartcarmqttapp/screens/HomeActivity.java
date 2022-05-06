package com.example.smartcarmqttapp.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smartcarmqttapp.screens.quiz.PracticeTheoryActivity;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.state.CarState;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public CardView theoryCard, drivingCard, connectCard, supportCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        theoryCard = findViewById(R.id.theoryCard);
        drivingCard = findViewById(R.id.drivingCard);
        connectCard = findViewById(R.id.connectCard);
        supportCard = findViewById(R.id.supportCard);

        theoryCard.setOnClickListener(this);
        drivingCard.setOnClickListener(this);
        connectCard.setOnClickListener(this);
        supportCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.theoryCard:
                i = new Intent(this, PracticeTheoryActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

            case R.id.drivingCard:
                if (CarState.instance.isConnected()) {
                    i = new Intent(this, PracticeDrivingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setMessage("Would you like to connect to it?")
                            .setPositiveButton("Connect my car!", (theDialog, id) -> {
                                Intent intent = new Intent(this, ConnectedCarActivity.class);
                                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            })
                            .setNegativeButton("NO!", (theDialog, id) -> {
                                Intent intent = new Intent(this, PracticeDrivingActivity.class);
                                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            })
                            .create();

                    dialog.setTitle("Car Not Connected 😔");
                    dialog.show();
                }
                break;

            case R.id.connectCard:
                i = new Intent(this, ConnectedCarActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

            case R.id.supportCard:
                i = new Intent(this, AboutUsActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}