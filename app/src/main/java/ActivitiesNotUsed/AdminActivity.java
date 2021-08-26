package ActivitiesNotUsed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.ActivityAdminBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//Theme.MaterialComponents.DayNight.DarkActionBar
public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding adminBinding;
    private FusedLocationProviderClient fusedLocationClient;
    int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(adminBinding.getRoot());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        returnMain();
        registerAdmin();
        getLastLocation();
        endTracking();
    }
    void endTracking(){
        adminBinding.finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocationClient.removeLocationUpdates(locationCallback);
            }
        });
    }
    void getLastLocation(){
        adminBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    if (isLocationEnabled()) {
                        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location newLocation = task.getResult();
                                if (newLocation != null){
                                    Log.d("myCord1", "Lat: " + newLocation.getLatitude() + " ,Lng: " + newLocation.getLongitude());
                                    Log.d("myCordSpeed", "Time: " + newLocation.getTime());
                                }
                                    requestNewLocationData();
                            }
                        });
                    } else {
                        Toast.makeText(AdminActivity.this, "Please turn on your location...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                } else {
                    requestPermissions();
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    void requestNewLocationData(){
        LocationRequest requestLocation = LocationRequest.create();
        requestLocation.setInterval(1000);
        //requestLocation.setFastestInterval(50);
        requestLocation.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(requestLocation, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            Log.d("myCord2", "Lat: " + lastLocation.getLatitude() + " ,Lng: " + lastLocation.getLongitude());
            Log.d("myCordSpeed2", "Time: " + lastLocation.getTime());
        }
    };

    void returnMain(){
        adminBinding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    void registerAdmin(){
        adminBinding.adminregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean admin = true;
                Intent registerIntent = new Intent(AdminActivity.this, SignupActivity.class);
                registerIntent.putExtra("admin", admin);
                startActivity(registerIntent);
            }
        });
    }
    void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    Boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    Boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_ID){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if(checkPermission()){
            getLastLocation();
        }
    }
}