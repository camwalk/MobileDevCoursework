package org.me.gcu.equakestartercode;

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

public class MainActivity extends AppCompatActivity
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
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        startButton = findViewById(R.id.mapButton);
        filterButton = findViewById(R.id.filterButton);
        recyclerView = findViewById(R.id.earthquakeList);
        startButton.setOnClickListener(this::onClickMap);
        filterButton.setOnClickListener(this::onClickFilter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        startProgress();
        Log.e("MyTag","after startProgress");
        // More Code goes here
    }

    public void parseData(String result) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(result));
            int eventType = xpp.getEventType();

            Earthquake earthquake = new Earthquake();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Started");
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("item")) {
                        earthquake = new Earthquake();
                    }
                    if (xpp.getName().equals("title")) {
                        earthquake.setTitle(xpp.nextText());
                    } else if (xpp.getName().equals("description")) {
                        earthquake.setDescription(xpp.nextText());
                        String fullDesc = earthquake.getDescription();
                        String[] splitDesc = fullDesc.split(";");
                        earthquake.setMagntitude(fullDesc.substring(fullDesc.length()-3));
                    } else if (xpp.getName().equals("link")) {
                        earthquake.setLink(xpp.nextText());
                    } else if (xpp.getName().equals("pubDate")) {
                        earthquake.setDate(xpp.nextText());
                    } else if (xpp.getName().equals("category")) {
                        earthquake.setCategory(xpp.nextText());
                    } else if (xpp.getName().equals("lat")) {
                        earthquake.setLatitude(xpp.nextText());
                    } else if (xpp.getName().equals("long")) {
                        earthquake.setLongitude(xpp.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals("item")) {
                        String fullDesc = earthquake.getDescription();
                        String[] splitDesc = fullDesc.split(";");
                        String depth = splitDesc[3].substring(8, splitDesc[3].length()-4);
                        earthquake.setDepth(depth);
                        earthquakeList.add(earthquake);
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //display earthquakes
        ListAdapter adapter = new ListAdapter(MainActivity.this, earthquakeList);
        recyclerView.setAdapter(adapter);
    }

    public void onClickMap(View aview)
    {
        Intent intent = new Intent(MainActivity.this,
                MapActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("EARTHQUAKELIST", (Serializable) earthquakeList);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
        setContentView(R.layout.earthquake_map);
    }

    public void onClickFilter(View aview)
    {
        Intent intent = new Intent(MainActivity.this,
                FilterActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("EARTHQUAKELIST", (Serializable) earthquakeList);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
        setContentView(R.layout.activity_filter);
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    }

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);
                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    parseData(result);
                }
            });
        }
    }
}