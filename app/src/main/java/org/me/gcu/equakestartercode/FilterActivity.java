package org.me.gcu.equakestartercode;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import static org.me.gcu.equakestartercode.DatePickerClass.FLAG_END_DATE;
import static org.me.gcu.equakestartercode.DatePickerClass.FLAG_START_DATE;

public class FilterActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener{

    private Button returnButton;
    private Button submitButton;
    private EditText mStartTime;
    private EditText mEndTime;
    private DatePickerClass mDatePicker;
    Double largestMagnitude = 0.0;
    Double deepest = 0.0;
    Double shallowest = 1000.0;
    Double mostNorthern = 0.0;
    Double mostEastern = 0.0;
    Double mostSouthern = 180.0;
    Double mostWestern = 90.0;
    Earthquake largestMag = new Earthquake();
    Earthquake deep = new Earthquake();
    Earthquake shallow = new Earthquake();
    Earthquake north = new Earthquake();
    Earthquake east = new Earthquake();
    Earthquake south = new Earthquake();
    Earthquake west = new Earthquake();
    ArrayList<Earthquake> earthquakeList = new ArrayList<>();
    ArrayList<Earthquake> narrowedList = new ArrayList<>();
    ArrayList<Earthquake> newList = new ArrayList<>();

    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquakeList = (ArrayList<Earthquake>) args.getSerializable("EARTHQUAKELIST");

        mStartTime = (EditText) findViewById(R.id.start_date);
        mEndTime = (EditText) findViewById(R.id.end_date);
        mDatePicker = new DatePickerClass();

        mStartTime.setOnClickListener(this::onClickDate);
        mEndTime.setOnClickListener(this::onClickDate);

        returnButton = findViewById(R.id.returnButton2);
        returnButton.setOnClickListener(this::onClickReturn);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this::onClickSubmit);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onClickSubmit(View view) {
        if(mStartTime != null && mEndTime != null) {
            earthquakeList.forEach(e -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate startTime = LocalDate.parse(mStartTime.getText());
                    LocalDate endTime = LocalDate.parse(mEndTime.getText());
                    DateTimeFormatter formatterForEnteredDate = DateTimeFormatter.ofPattern("dd MMM yyyy");
                    String[] splitDate = e.getDate().split(" ");
                    String fullDate = (splitDate[1] + " " + splitDate[2] + " " + splitDate[3]);
                    LocalDate earthquakeTime = LocalDate.parse(fullDate, formatterForEnteredDate);
                    if(startTime.compareTo(earthquakeTime) * earthquakeTime.compareTo(endTime) >= 0) {
                        narrowedList.add(e);
                    }
                }
            });
        }
        if(mStartTime == mEndTime){
            earthquakeList.forEach(e -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate startTime = LocalDate.parse(mStartTime.getText());
                    DateTimeFormatter formatterForEnteredDate = DateTimeFormatter.ofPattern("dd MMM yyyy");
                    String[] splitDate = e.getDate().split(" ");
                    String fullDate = (splitDate[1] + " " + splitDate[2] + " " + splitDate[3]);
                    LocalDate earthquakeTime = LocalDate.parse(fullDate, formatterForEnteredDate);
                    if(startTime == earthquakeTime) {
                        narrowedList.add(e);
                    }
                }
            });
        }

        narrowedList.forEach(e -> {
            if (Double.parseDouble(e.getMagntitude()) > largestMagnitude) {
                largestMagnitude = Double.parseDouble(e.getMagntitude());
                largestMag = e;
            }
            if (Double.parseDouble(e.getDepth()) > deepest) {
                deepest = Double.parseDouble(e.getDepth());
                deep = e;
            }
            if (Double.parseDouble(e.getDepth()) < shallowest) {
                shallowest = Double.parseDouble(e.getDepth());
                shallow = e;
            }
            if (Double.parseDouble(e.getLatitude()) > mostNorthern) {
                mostNorthern = Double.parseDouble(e.getLatitude());
                north = e;
            }
            if (Double.parseDouble(e.getLongitude()) > mostEastern) {
                mostEastern = Double.parseDouble(e.getLongitude());
                east = e;
            }
            if (Double.parseDouble(e.getLatitude()) < mostSouthern) {
                mostSouthern = Double.parseDouble(e.getLatitude());
                south = e;
            }
            if (Double.parseDouble(e.getLongitude()) < mostWestern) {
                mostWestern = Double.parseDouble(e.getLongitude());
                west = e;
            }
        });
        if(largestMagnitude != 0.0)newList.add(largestMag);
        if(deepest != 0.0) newList.add(deep);
        if(shallowest != 1000.0) newList.add(shallow);
        if(mostNorthern != 0.0) newList.add(north);
        if(mostEastern != 0.0) newList.add(east);
        if(mostSouthern != 180.0) newList.add(south);
        if(mostWestern != 90.0) newList.add(west);
        System.out.println(newList);
        if(!newList.isEmpty()){nextPage();}
        else {
            Toast.makeText(getApplicationContext(),  "Unable to filter these dates (no earthquakes found)", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextPage() {
        Intent intent = new Intent(FilterActivity.this,
                FilteredMainActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("EARTHQUAKELIST", (Serializable) newList);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
        setContentView(R.layout.activity_filtered_main);
    }

    private void onClickReturn(View view) {
        Intent intent = new Intent(FilterActivity.this,
                MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
}