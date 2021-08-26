package Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentGpsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import Adapters.gpsRecyclerAdapter;
import ViewModel.gpsLocationViewModel;
import roomGPS.gpsLocation;


public class GpsFragment extends Fragment {
    private FragmentGpsBinding gpsBinding;
    private FusedLocationProviderClient fusedLocationClient;
    private gpsLocationViewModel gpsViewModel;
    private List<gpsLocation> allGps;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    int PERMISSION_ID = 44;
    int id = 1;

    public GpsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gpsBinding = FragmentGpsBinding.inflate(inflater, container, false);
        return gpsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        gpsViewModel = new ViewModelProvider(requireActivity()).get(gpsLocationViewModel.class);
        gpsViewModel.getAllLocations().observe(getViewLifecycleOwner(), new Observer<List<gpsLocation>>() { //change: this -> getView
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<gpsLocation> gps) {
                try{
                    allGps = Objects.requireNonNull(gps);
                    gpsViewModel.updateList(allGps);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        getLastLocation();
        endTracking();
    }

    void endTracking(){
        gpsBinding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocationClient.removeLocationUpdates(locationCallback);
                id = id + 1;
               Log.d("vendo", gpsViewModel.getGps().get(0).getLatitude()+"");

            }
        });
    }

    void getLastLocation(){
        gpsBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    if (isLocationEnabled()) {
                        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location newLocation = task.getResult();
                                if (newLocation != null){
                                    String latitude = "Latitude: " + newLocation.getLatitude();
                                    String longitude = "longitude: " + newLocation.getLongitude();
                                    gpsBinding.latID.setText(latitude);
                                    gpsBinding.lngID.setText(longitude);
                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
                                    dateTime = simpleDateFormat.format(calendar.getTime());
                                    gpsLocation gps = new gpsLocation(id, newLocation.getLatitude(), newLocation.getLongitude(), dateTime);
                                    gpsViewModel.insert(gps);
                                    Log.d("myCord1", "Lat: " + newLocation.getLatitude() + " ,Lng: " + newLocation.getLongitude());
                                    Log.d("myCordSpeed", "Time: " + newLocation.getTime());
                                }
                                requestNewLocationData();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        fusedLocationClient.requestLocationUpdates(requestLocation, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            String latitude = "Latitude: " + lastLocation.getLatitude();
            String longitude = "longitude: " + lastLocation.getLongitude();
            gpsBinding.latID.setText(latitude);
            gpsBinding.lngID.setText(longitude);
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
            dateTime = simpleDateFormat.format(calendar.getTime());
            gpsLocation gps = new gpsLocation(id, lastLocation.getLatitude(), lastLocation.getLongitude(), dateTime);
            gpsViewModel.insert(gps);
            Log.d("myCord2", "Lat: " + lastLocation.getLatitude() + " ,Lng: " + lastLocation.getLongitude());
            Log.d("myCordSpeed2", "Time: " + lastLocation.getTime());
        }
    };

    void requestPermissions(){
        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    Boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    Boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
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
}