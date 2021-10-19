package com.example.wpws;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.wpws.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private EditText searchInput;


    private float latitude,longitude;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent().hasExtra("editMode"))
        {
            latitude = (float) Float.parseFloat(getIntent().getStringExtra("latitude"));
            longitude = (float) Float.parseFloat(getIntent().getStringExtra("longitude"));
            name = getIntent().getStringExtra("name");
        }
        else
        {
            latitude = 0;
            longitude = 0;
            name = "My new location";
        }
    }

    private void geoLocate()
    {
        String searchLocation = searchInput.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchLocation, 1);
        } catch (IOException e)
        {
            Log.e("MAPS", "Geolocate: " + e.getMessage());
        }
        if(list.size() > 0)
        {
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 10);
        }
    }

    private void setMarker(LatLng position, String title)
    {
        MarkerOptions options = new MarkerOptions()
                .position(position)
                .title(title);
        mMap.addMarker(options);
    }

    private void moveCamera(LatLng position, float zoom)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng myLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myLocation).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

        searchInput = (EditText) findViewById(R.id.map_search_input);
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == KeyEvent.ACTION_DOWN
                        || actionId == KeyEvent.KEYCODE_ENTER)

                    //search location
                    geoLocate();
                return false;
            }
        });
    }
}