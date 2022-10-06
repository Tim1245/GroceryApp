package com.example.groceryapp;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.groceryapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng karlstad = new LatLng(59.404390283954065, 13.512529182246308);
        LatLng coopBergvik = new LatLng(59.377955, 13.425271);
        LatLng coopValsviken = new LatLng(59.39631609438561, 13.575848584939761);
        LatLng icaValsviken = new LatLng(59.39636019797852, 13.580784956104168);
        LatLng willysCity = new LatLng(59.37873877071142, 13.509179240196127);
        LatLng willysVaxnas = new LatLng(59.39007983348828, 13.481656116945008);
        LatLng icaSuperMarketWallinder = new LatLng(59.39483129478293, 13.517329998415354);
        LatLng icaSuperMarketHaga = new LatLng(59.38535399115744, 13.515270971750242);
        LatLng icaViken = new LatLng(59.37790211919998, 13.490859116915285);
        LatLng icaKarnan = new LatLng(59.387091362830496, 13.46403259886529);
        LatLng icaSuperMarketFanfar = new LatLng(59.3851325779657, 13.48540154747029);
        LatLng icaBergvik = new LatLng(59.37816709297543, 13.429414902125185);
        mMap.addMarker(new MarkerOptions().position(coopBergvik).title("Stora Coop Bergvik"));
        mMap.addMarker(new MarkerOptions().position(coopValsviken).title("Stora Coop Välsviken"));
        mMap.addMarker(new MarkerOptions().position(icaValsviken).title("Ica-Maxi Välsviken"));
        mMap.addMarker(new MarkerOptions().position(willysCity).title("Willys City"));
        mMap.addMarker(new MarkerOptions().position(willysVaxnas).title("Willys Våxnäs Industriområde"));
        mMap.addMarker(new MarkerOptions().position(icaSuperMarketWallinder).title("ICA Supermarket Wallinders"));
        mMap.addMarker(new MarkerOptions().position(icaSuperMarketHaga).title("Ica Supermarket Hagahallen"));
        mMap.addMarker(new MarkerOptions().position(icaViken).title("Ica Nära Viken"));
        mMap.addMarker(new MarkerOptions().position(icaKarnan).title("Ica Nära Kärnan"));
        mMap.addMarker(new MarkerOptions().position(icaSuperMarketFanfar).title("Ica Supermarket Fanfaren"));
        mMap.addMarker(new MarkerOptions().position(icaBergvik).title("Ica-Maxi Bergviken"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karlstad));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karlstad,11));
    }
}

