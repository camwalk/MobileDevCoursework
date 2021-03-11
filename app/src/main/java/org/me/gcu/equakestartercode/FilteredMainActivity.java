package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class FilteredMainActivity extends AppCompatActivity
{
    private Button startButton;
    private Button filterButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private RecyclerView recyclerView;
    public ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get earthquakeList from previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquakeList = (ArrayList<Earthquake>) args.getSerializable("EARTHQUAKELIST");

        // Set up the raw links to the graphical components
        startButton = findViewById(R.id.mapButton);
        filterButton = findViewById(R.id.filterButton);
        recyclerView = findViewById(R.id.earthquakeList);
        startButton.setOnClickListener(this::onClickMap);
        filterButton.setOnClickListener(this::onClickFilter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // More Code goes here

        // Display Earthquakes
        ListAdapter adapter = new ListAdapter(FilteredMainActivity.this, earthquakeList);
        recyclerView.setAdapter(adapter);
    }

    public void onClickMap(View aview)
    {
        Intent intent = new Intent(FilteredMainActivity.this,
                MapActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("EARTHQUAKELIST", (Serializable) earthquakeList);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
        setContentView(R.layout.earthquake_map);
    }

    public void onClickFilter(View aview)
    {
        Intent intent = new Intent(FilteredMainActivity.this,
                FilterActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("EARTHQUAKELIST", (Serializable) earthquakeList);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
        setContentView(R.layout.activity_filter);
    }
}

