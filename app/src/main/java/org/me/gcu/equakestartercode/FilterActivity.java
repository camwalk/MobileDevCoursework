package org.me.gcu.equakestartercode;


import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static org.me.gcu.equakestartercode.DatePickerClass.FLAG_END_DATE;
import static org.me.gcu.equakestartercode.DatePickerClass.FLAG_START_DATE;

public class FilterActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener{

    private Button returnButton;
    private Button submitButton;
    private Button largestMagnitudeButton;
    private Button deepestButton;
    private Button shallowestButton;
    private Button northernButton;
    private Button easternButton;
    private Button southernButton;
    private Button westernButton;
    private EditText mStartTime;
    private EditText mEndTime;
    private DatePickerClass mDatePicker;
    ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mStartTime = (EditText) findViewById(R.id.start_date);
        mEndTime = (EditText) findViewById(R.id.end_date);
        mDatePicker = new DatePickerClass();

        mStartTime.setOnClickListener(this::onClickDate);
        mEndTime.setOnClickListener(this::onClickDate);

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

    public void onClickDate(View v) {
        int id = v.getId();
        if (id == R.id.start_date) {
            mDatePicker.setFlag(FLAG_START_DATE);
            mDatePicker.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.end_date) {
            mDatePicker.setFlag(FLAG_END_DATE);
            mDatePicker.show(getSupportFragmentManager(), "datePicker");
        }
    }

    public void onDateSet(@Nullable DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (mDatePicker.getFlag() == FLAG_START_DATE) {
            mStartTime.setText(format.format(calendar.getTime()));
        } else if (mDatePicker.getFlag() == FLAG_END_DATE) {
            mEndTime.setText(format.format(calendar.getTime()));
        }
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