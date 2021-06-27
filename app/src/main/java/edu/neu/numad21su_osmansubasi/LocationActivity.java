package edu.neu.numad21su_osmansubasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity implements LocationListener {


    LocationManager locationManager;
    private TextView latitudeValue;
    private TextView longitudeValue;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Location location;
    Double dLatitude; // latitude
    Double dLongitude; // longitude
    long minTime = 1000 * 60; // min time to request update (1 minute)
    long minDistance = 10; // min distance (10 meters)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        latitudeValue = (TextView) findViewById(R.id.latitude);
        longitudeValue = (TextView) findViewById(R.id.longitude);



        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            getNetworkLocation();
            getGpsLocation();

            // Interestingly in a physical device GPS does not work but network works
            // and in emulator gps works but network does not work so this is the solution that
            // I have found.
            if (dLatitude == null || dLongitude == null){
                getNetworkLocation();
            } else {
                getGpsLocation();
            }
        } catch (Exception error){
            error.printStackTrace();
        }

    }

    private void getGpsLocation(){
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled){
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, minTime, minDistance, this);

            }
            Log.d("GPS Enabled", "GPS Enabled");
            if(locationManager != null){
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null){
                    dLatitude = location.getLatitude();
                    dLongitude = location.getLongitude();
                }
            }
        }
        catch (Exception error){
            error.printStackTrace();
        }
    }
    private void getNetworkLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            //check the network permission
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, minTime, minDistance, this);

            Log.d("Network", "Network");
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    dLatitude = location.getLatitude();
                    dLongitude = location.getLongitude();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        latitudeValue.setText(String.valueOf(dLatitude));
        longitudeValue.setText(String.valueOf(dLongitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}