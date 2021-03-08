package org.me.gcu.equakestartercode;// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button returnButton;
    ArrayList<Earthquake> earthquakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        earthquakeList = (ArrayList<Earthquake>) args.getSerializable("EARTHQUAKELIST");
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(this::onClick);
    }

    public void onClick(View aview)
    {
        Intent intent = new Intent(MapActivity.this,
                MapActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //add earthquake pin, with color based on magnitude
        earthquakeList.forEach((e) -> {
            LatLng position = new LatLng(Double.parseDouble(e.getLatitude()),Double.parseDouble(e.getLongitude()));
            String fullDesc = e.getDescription();
            String[] splitDesc = fullDesc.split(";");
            float markerColor;
            if(Double.parseDouble(e.getMagntitude()) > 2){
                markerColor = BitmapDescriptorFactory.HUE_RED;
            }
            else if (Double.parseDouble(e.getMagntitude()) > 1) {
                markerColor = BitmapDescriptorFactory.HUE_ORANGE;
            }
            else {
                markerColor = BitmapDescriptorFactory.HUE_YELLOW;
            }
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(splitDesc[1])
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        });
    }
}
