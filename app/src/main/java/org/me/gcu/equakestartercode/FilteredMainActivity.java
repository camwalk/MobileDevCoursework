package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class FilteredMainActivity extends AppCompatActivity
{
    private Button filterReturnButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private RecyclerView recyclerView;
    public ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_main);

        //Get earthquakeList from previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquakeList = (ArrayList<Earthquake>) args.getSerializable("EARTHQUAKELIST");

        // Set up the raw links to the graphical components
        recyclerView = findViewById(R.id.earthquakeList);
        System.out.println(recyclerView);
        filterReturnButton = findViewById(R.id.filterReturnButton);
        filterReturnButton.setOnClickListener(this::onClickReturn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // More Code goes here

        // Display Earthquakes
        FilterAdapter adapter = new FilterAdapter(FilteredMainActivity.this, earthquakeList);
        recyclerView.setAdapter(adapter);
    }

    private void onClickReturn(View view) {
        Intent intent = new Intent(FilteredMainActivity.this,
                MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
}

