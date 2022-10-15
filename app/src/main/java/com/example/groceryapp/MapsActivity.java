package com.example.groceryapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.groceryapp.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 44;
    ArrayList<Location> locations = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener(view -> closestStore());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
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
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);*/

        Location coopBergvikLoc=new Location("Coop Bergvik");
        coopBergvikLoc.setLatitude(59.37722325348227);
        coopBergvikLoc.setLongitude(13.426254883058016);

        Location coopValsvikenLoc=new Location("Coop Välsviken");
        coopValsvikenLoc.setLatitude(59.3962833222748);
        coopValsvikenLoc.setLongitude(13.575773483058837);

        Location icaValsvikenLoc=new Location("Ica Välsviken");
        icaValsvikenLoc.setLatitude(59.396717882843035);
        icaValsvikenLoc.setLongitude(13.580274721690687);

        Location icaBergvikLoc=new Location("Ica Bergvik");
        icaBergvikLoc.setLatitude(59.37659741178295);
        icaBergvikLoc.setLongitude(13.428619283057982);

        Location willysCityLoc=new Location("Willys City");
        willysCityLoc.setLatitude(59.377544112785145);
        willysCityLoc.setLongitude(13.508946613742117);

        Location willysVaxnasLoc=new Location("Willys Våxnäs");
        willysVaxnasLoc.setLatitude(59.38797373919289);
        willysVaxnasLoc.setLongitude(13.481932913742554);

        Location lidlVaxnasLoc=new Location("Lidl Våxnäs");
        lidlVaxnasLoc.setLatitude(59.38697513352154);
        lidlVaxnasLoc.setLongitude(13.475642783058444);

        Location lidlKroppkarrLoc=new Location("Lidl Kroppkärr");
        lidlKroppkarrLoc.setLatitude(59.398146416269896);
        lidlKroppkarrLoc.setLongitude(13.535086398400976);



        locations.add(coopBergvikLoc);
        locations.add(coopValsvikenLoc);
        locations.add(icaValsvikenLoc);
        locations.add(icaBergvikLoc);
        locations.add(willysCityLoc);
        locations.add(willysVaxnasLoc);
        locations.add(lidlVaxnasLoc);
        locations.add(lidlKroppkarrLoc);





        LatLng karlstad = new LatLng(59.404390283954065, 13.512529182246308);
        LatLng coopBergvik = new LatLng(59.37722325348227, 13.426254883058016);
        LatLng coopValsviken = new LatLng(59.3962833222748, 13.575773483058837);
        LatLng icaValsviken = new LatLng(59.396717882843035, 13.580274721690687);
        LatLng icaBergvik = new LatLng(59.37659741178295, 13.428619283057982);
        LatLng willysCity = new LatLng(59.377544112785145, 13.508946613742117);
        LatLng willysVaxnas = new LatLng(59.38797373919289, 13.481932913742554);
        LatLng lidlVaxnas = new LatLng(59.38697513352154, 13.475642783058444);
        LatLng lidlKroppkarr = new LatLng(59.398146416269896, 13.535086398400976);

        //LatLng icaSuperMarketWallinder = new LatLng(59.39483129478293, 13.517329998415354);
        //LatLng icaSuperMarketHaga = new LatLng(59.38535399115744, 13.515270971750242);
        //LatLng icaViken = new LatLng(59.37790211919998, 13.490859116915285);
        //LatLng icaKarnan = new LatLng(59.387091362830496, 13.46403259886529);
        //LatLng icaSuperMarketFanfar = new LatLng(59.3851325779657, 13.48540154747029);


        mMap.addMarker(new MarkerOptions().position(coopBergvik).title("Stora Coop Bergvik"));
        mMap.addMarker(new MarkerOptions().position(coopValsviken).title("Stora Coop Välsviken"));
        mMap.addMarker(new MarkerOptions().position(icaValsviken).title("Ica-Maxi Välsviken"));
        mMap.addMarker(new MarkerOptions().position(willysCity).title("Willys City"));
        mMap.addMarker(new MarkerOptions().position(willysVaxnas).title("Willys Våxnäs Industriområde"));
        mMap.addMarker(new MarkerOptions().position(icaBergvik).title("Ica-Maxi Bergvik"));
        mMap.addMarker(new MarkerOptions().position(lidlVaxnas).title("Lidl Våxnäs"));
        mMap.addMarker(new MarkerOptions().position(lidlKroppkarr).title("Lidl Kroppkärr"));
        /*mMap.addMarker(new MarkerOptions().position(icaSuperMarketWallinder).title("ICA Supermarket Wallinders"));
        mMap.addMarker(new MarkerOptions().position(icaSuperMarketHaga).title("Ica Supermarket Hagahallen"));
        mMap.addMarker(new MarkerOptions().position(icaViken).title("Ica Nära Viken"));
        mMap.addMarker(new MarkerOptions().position(icaKarnan).title("Ica Nära Kärnan"));
        mMap.addMarker(new MarkerOptions().position(icaSuperMarketFanfar).title("Ica Supermarket Fanfaren"));*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karlstad));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karlstad, 11));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
            return;
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions current = new MarkerOptions().position(latLng).title("Current location");
                current.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.navigation)));
                mMap.addMarker(current);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });

    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (Request_Code) {
            case Request_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
        }
    }

    public void closestStore(){
        if(currentLocation != null){
            Location location = null;
            double distance = Double.POSITIVE_INFINITY;
            for(Location Loc: locations){
                double x = currentLocation.distanceTo(Loc);
                if(x < distance){
                    location = Loc;
                    distance = x;
                }
            }
            LatLng latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));
            Toast.makeText(getApplicationContext(), "Closest Store is: "+ location.getProvider(), Toast.LENGTH_SHORT).show();
        }else{
            getCurrentLocation();
        }


    }
}