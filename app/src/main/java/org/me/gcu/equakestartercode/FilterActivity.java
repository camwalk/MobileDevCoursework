package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private Button returnButton;
    private Button submitButton;
    private Button largestMagnitudeButton;
    private Button deepestButton;
    private Button shallowestButton;
    private Button northernButton;
    private Button easternButton;
    private Button southernButton;
    private Button westernButton;
    ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquakeList = (ArrayList<Earthquake>) args.getSerializable("EARTHQUAKELIST");
        returnButton = findViewById(R.id.returnButton2);
        returnButton.setOnClickListener(this::onClickReturn);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this::onClickSubmit);
        largestMagnitudeButton = findViewById(R.id.largestMagnitudeButton);
        largestMagnitudeButton.setOnClickListener(this::onClickMagnitude);
        deepestButton = findViewById(R.id.deepestButton);
        deepestButton.setOnClickListener(this::onClickDeepest);
        shallowestButton = findViewById(R.id.shallowestButton);
        shallowestButton.setOnClickListener(this::onClickShallowest);
        northernButton = findViewById(R.id.northernButton);
        northernButton.setOnClickListener(this::onClickNorthern);
        easternButton = findViewById(R.id.easternButton);
        easternButton.setOnClickListener(this::onClickReturn);
        southernButton = findViewById(R.id.southernButton);
        southernButton.setOnClickListener(this::onClickSouthern);
        westernButton = findViewById(R.id.westernButton);
        westernButton.setOnClickListener(this::onClickWestern);
    }

    private void onClickWestern(View view) {
    }

    private void onClickSouthern(View view) {
    }

    private void onClickNorthern(View view) {
    }

    private void onClickShallowest(View view) {
    }

    private void onClickDeepest(View view) {
    }

    private void onClickMagnitude(View view) {
    }

    private void onClickSubmit(View view) {
    }

    private void onClickReturn(View view) {
        Intent intent = new Intent(FilterActivity.this,
                MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
}