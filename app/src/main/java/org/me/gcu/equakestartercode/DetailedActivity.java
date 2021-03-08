package org.me.gcu.equakestartercode;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener {

    private Button detailReturnView;
    private TextView detailTitleView;
    private TextView detailMagnitudeView;
    private TextView detailLinkView;
    private TextView detailDateView;
    private TextView detailCategoryView;
    private TextView detailLatitudeView;
    private TextView detailLongitudeView;
    private Earthquake earthquake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquake = (Earthquake) args.getSerializable("EARTHQUAKE");
        System.out.println(earthquake);

        TextView title = (TextView) findViewById(R.id.detailTitleView);
        TextView magnitude = (TextView) findViewById(R.id.detailMagnitudeText);
        TextView link = (TextView) findViewById(R.id.detailLinkText);
        TextView date = (TextView) findViewById(R.id.detailDateText);
        TextView category = (TextView) findViewById(R.id.detailCategoryText);
        TextView latitude = (TextView) findViewById(R.id.detailLatitudeText);
        TextView longitude = (TextView) findViewById(R.id.detailLongitudeText);

        String fullDesc = earthquake.getDescription();
        String[] splitDesc = fullDesc.split(";");
        splitDesc = splitDesc[1].split(":");

        title.setText(splitDesc[1]);
        magnitude.setText("Magnitude: " + earthquake.getMagntitude());
        link.setText(earthquake.getLink());
        date.setText("Date/Time: " + earthquake.getDate());
        category.setText("Category: " + earthquake.getCategory());
        latitude.setText("Latitude: " + earthquake.getLatitude());
        longitude.setText("Longitude: " + earthquake.getLongitude());

        Button detailReturnButton = findViewById(R.id.detailReturnButton);
        detailReturnButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DetailedActivity.this,
                MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
}