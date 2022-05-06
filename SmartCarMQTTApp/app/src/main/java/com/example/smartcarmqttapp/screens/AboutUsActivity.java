package com.example.smartcarmqttapp.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Navigation.initializeNavigation(this, R.id.aboutUs);
    }

    public void openGitHub(View view){
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DIT113-V22/group-01"));
        startActivity(openBrowser);
    }
}