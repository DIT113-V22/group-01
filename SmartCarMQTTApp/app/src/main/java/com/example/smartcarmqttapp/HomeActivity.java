package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public MqttCar controller;
    public CardView theoryCard, drivingCard, connectCard, supportCard;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        theoryCard = (CardView) findViewById(R.id.theoryCard);
        drivingCard = (CardView) findViewById(R.id.drivingCard);
        connectCard = (CardView) findViewById(R.id.connectCard);
        supportCard = (CardView) findViewById(R.id.supportCard);

        theoryCard.setOnClickListener(this);
        drivingCard.setOnClickListener(this);
        connectCard.setOnClickListener(this);
        supportCard.setOnClickListener(this);


//        controller = new MqttCar(getApplicationContext(), () -> {
//            try {
//                controller.changeSpeed(0.5);
//            } catch (MqttException ex) {
//                ex.printStackTrace();
//            }
//        });
    }

    @Override
    public void onClick(View view) {
    Intent i;



    switch (view.getId()){
        // TODO: Add activity class to intent. e.g  case R.id.theoryCard:i = new Intent(this,Driving.class);startActivity(i);
        case R.id.theoryCard:i = new Intent(this, PracticeTheoryActivity.class);startActivity(i);
        break;
        case R.id.drivingCard: i = new Intent(this, PracticeDrivingActivity.class);startActivity(i);
        /*
        //If the carstate worked than this would be useful
        if(CarState.instance.isConnected()) {
                i = new Intent(this, PracticeDrivingActivity.class);
                startActivity(i);
            } else {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("Would you like to connect to it?")
                        .setPositiveButton("Connect my car!!!", (theDialog, id) -> {
                            Intent intent = new Intent(this, ConnectedCarActivity.class);
                            startActivity(intent);
                        })
                        .create();

                dialog.setTitle("Car Not Connected 😔");
                dialog.show();
            }
         */
            break;
        case R.id.connectCard:i = new Intent(this, ConnectedCarActivity.class);startActivity(i);
        break;
        case R.id.supportCard:i = new Intent(this, AboutUsActivity.class);startActivity(i);
        break;
        default:break;
    }
    }
}