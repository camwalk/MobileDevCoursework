package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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

//Student ID: S1920624
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
        startButton = findViewById(R.id.mapButton);
        filterButton = findViewById(R.id.filterButton);
        recyclerView = findViewById(R.id.earthquakeList);
        startButton.setOnClickListener(this::onClickMap);
        filterButton.setOnClickListener(this::onClickFilter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        startProgress();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        new Task(urlSource).execute();
    }

    private class Task extends  AsyncTask<String, Integer, String>
    {
        int progress_status;
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }

        @Override
        protected String doInBackground(String... params) {

                publishProgress(progress_status);

                URL aurl;
                URLConnection yc;
                BufferedReader in = null;
                String inputLine = "";

                try
                {
                    aurl = new URL(url);
                    yc = aurl.openConnection();
                    in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    //
                    // Throw away the first 2 header lines before parsing
                    //
                    //
                    //
                    while ((inputLine = in.readLine()) != null)
                    {
                        result = result + inputLine;
                    }
                    in.close();
                }
                catch (IOException ae)
                {
                    Toast.makeText(getApplicationContext(),  "IOException encountered", Toast.LENGTH_SHORT).show();
                }

                MainActivity.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        parseData(result);
                    }
                });
            System.out.println(result);
            return result;
            }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            //display earthquakes
            ListAdapter adapter = new ListAdapter(MainActivity.this, earthquakeList);
            recyclerView.setAdapter(adapter);
        }
    }
}