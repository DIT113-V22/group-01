package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
    }

    public void openGitHub(View view){
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DIT113-V22/group-01"));
        startActivity(openBrowser);
    }
}