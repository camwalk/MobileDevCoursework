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
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        startButton = findViewById(R.id.mapButton);
        recyclerView = findViewById(R.id.earthquakeList);
        startButton.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        startProgress();
        Log.e("MyTag","after startProgress");
        // More Code goes here
    }

    public void parseData(String result) {
        ArrayList<Earthquake> earthquakeList = new ArrayList<>();
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
                        earthquakeList.add(earthquake);
                    }
                }
                eventType = xpp.next();
            }
            Log.e("MyTag",earthquakeList.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //display earthquakes
        ListAdapter adapter = new ListAdapter(MainActivity.this, earthquakeList);
        recyclerView.setAdapter(adapter);
    }

    public void onClick(View aview)
    {
        Intent intent = new Intent(MainActivity.this,
                MapActivity.class);
        startActivity(intent);
        setContentView(R.layout.earthquake_map);
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