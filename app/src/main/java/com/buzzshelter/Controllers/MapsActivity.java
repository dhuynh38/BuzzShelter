package com.buzzshelter.Controllers;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Maps Activity
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        list = intent.getStringArrayExtra("list");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(0, 0);
        for (String name : list) {
            Shelter shelter = Model.getInstance().getShelterList(getApplicationContext()).get(name);
            double latitude = Double.parseDouble(shelter.getLatitude());
            double longitude = Double.parseDouble(shelter.getLongitude());
            location = new LatLng(latitude, longitude);
            String snippet = shelter.getPhoneNumber();
            googleMap.addMarker(new MarkerOptions()
                    .position(location).title(name).snippet(snippet));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
    }
}
