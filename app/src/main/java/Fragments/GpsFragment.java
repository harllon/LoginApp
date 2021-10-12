package Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentGpsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ViewModel.Motion.gpsLocationViewModel;
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
    private Uri gpsLog;
    private int rate;

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
        gpsBinding.shareButton.setVisibility(View.INVISIBLE);
        gpsBinding.saveButton.setVisibility(View.INVISIBLE);
        gpsBinding.startButton.setVisibility(View.INVISIBLE);
        gpsBinding.stopButton.setVisibility(View.INVISIBLE);
        getLastLocation();
        endTracking();
        save();
        share();
        getRate();
    }


    public void share(){
        gpsBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "GPS Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, gpsLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }

    public void save(){
        gpsBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsBinding.shareButton.setVisibility(View.VISIBLE);
                String texto = "";
                for (int i = 0; i < gpsViewModel.getGps().size(); i++) {
                    String longitude = String.valueOf(gpsViewModel.getGps().get(i).getLongitude());
                    String latitude = String.valueOf(gpsViewModel.getGps().get(i).getLatitude());
                    String dataTime = gpsViewModel.getGps().get(i).getDate();
                    texto = texto + "Latitude: " + latitude + "; Longitude: " + longitude + "; Data and Time: " + dataTime + "\n";
                }
                Log.d("Textao: ", texto);
                File file = new File(requireActivity().getExternalFilesDir(null), "gpsLog.txt");
                gpsLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(texto.getBytes(StandardCharsets.UTF_8));
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void getRate(){
        gpsBinding.setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(gpsBinding.rateEditText.getText().toString())){
                    Toast.makeText(requireContext(), "Please fill the rate field", Toast.LENGTH_LONG);
                }else{
                    rate = Integer.parseInt(gpsBinding.rateEditText.getText().toString());
                    gpsBinding.stopButton.setVisibility(View.VISIBLE);
                    gpsBinding.startButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void endTracking(){
        gpsBinding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsBinding.saveButton.setVisibility(View.VISIBLE);
                fusedLocationClient.removeLocationUpdates(locationCallback);
                id = id + 1;
               //Log.d("vendo", gpsViewModel.getGps().get(0).getLatitude()+"");

            }
        });
    }

    void getLastLocation(){
        gpsBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    if (isLocationEnabled()) {
                        gpsBinding.saveButton.setVisibility(View.INVISIBLE);
                        gpsBinding.shareButton.setVisibility(View.INVISIBLE);
                        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location newLocation = task.getResult();
                                if (newLocation != null){
                                    String latitude = "Latitude: " + newLocation.getLatitude();
                                    String longitude = "longitude: " + newLocation.getLongitude();
                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
                                    dateTime = simpleDateFormat.format(calendar.getTime());
                                    gpsLocation gps = new gpsLocation(id, newLocation.getLatitude(), newLocation.getLongitude(), newLocation.getSpeed(), newLocation.getAltitude(), dateTime, dateTime);
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
        requestLocation.setInterval(rate);
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
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
            dateTime = simpleDateFormat.format(calendar.getTime());
            gpsLocation gps = new gpsLocation(id, lastLocation.getLatitude(), lastLocation.getLongitude(), lastLocation.getSpeed(), lastLocation.getAltitude(), dateTime, dateTime);
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