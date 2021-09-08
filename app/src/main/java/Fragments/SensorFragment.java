package Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import androidx.navigation.Navigation;

import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSensorBinding;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import Utils.sensorName;
import ViewModel.accelerometerViewModel;
import ViewModel.gpsLocationViewModel;
import ViewModel.gravityViewModel;
import ViewModel.gyroscopeViewModel;
import ViewModel.motionViewModel;
import ViewModel.rotationViewModel;
import ViewModel.sensorViewModel;
import ViewModel.stepViewModel;
import roomGPS.gpsLocation;
import roomSensors.entities.accelerometer;
import roomSensors.entities.gravity;
import roomSensors.entities.gyroscope;
import roomSensors.entities.motion;
import roomSensors.entities.rotation;
import roomSensors.entities.stepCounter;


public class SensorFragment extends Fragment implements SensorEventListener {
    private FragmentSensorBinding sensorBinding;
    int PERMISSION_ID = 44;
    int id = 1;

    private sensorViewModel ssViewModel;
    private Sensor accelerometer;
    private SensorManager accManager;
    private Sensor gyroscope;
    private SensorManager gyroManager;
    private Sensor motion;
    private SensorManager motionManager;
    private Sensor rotation;
    private SensorManager rotationManager;
    private Sensor stepCounter;
    private SensorManager stepcounterManager;
    private Sensor gravity;
    private SensorManager gravManager;
    private FusedLocationProviderClient fusedLocationClient;


    private accelerometerViewModel accViewModel;
    private gravityViewModel gravViewModel;
    private gyroscopeViewModel gyroViewModel;
    private motionViewModel motViewModel;
    private rotationViewModel rotViewModel;
    private stepViewModel stViewModel;
    private gpsLocationViewModel gpsViewModel;

    private String dateTime;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private Uri motionLog;
    private Uri accLog;
    private Uri gravLog;
    private Uri gyroLog;
    private Uri rotLog;
    private Uri stepLog;
    private Uri gpsLog;
    ArrayList<Uri> uriList = new ArrayList<>();

    private List<accelerometer> allAcc;
    private List<gyroscope> allGyroscope;
    private List<rotation> allRotation;
    private List<stepCounter> allStep;
    private List<gravity> allGravity;
    private List<motion> allMotion;
    private List<gpsLocation> allGps;

    private int rate;
    private int rate2;

    public SensorFragment() {
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
        sensorBinding = FragmentSensorBinding.inflate(inflater, container, false);
        return sensorBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        accViewModel = new ViewModelProvider(requireActivity()).get(accelerometerViewModel.class);
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        gravViewModel = new ViewModelProvider(requireActivity()).get(gravityViewModel.class);
        gyroViewModel = new ViewModelProvider(requireActivity()).get(gyroscopeViewModel.class);
        motViewModel = new ViewModelProvider(requireActivity()).get(motionViewModel.class);
        rotViewModel = new ViewModelProvider(requireActivity()).get(rotationViewModel.class);
        stViewModel = new ViewModelProvider(requireActivity()).get(stepViewModel.class);
        gpsViewModel = new ViewModelProvider(requireActivity()).get(gpsLocationViewModel.class);

        accManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gravManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        motionManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        rotationManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepcounterManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        sensorBinding.startButton.setClickable(false);
        sensorBinding.startButton.setBackgroundColor(Color.GRAY);
        sensorBinding.shareButton.setBackgroundColor(Color.GRAY);
        sensorBinding.shareButton.setClickable(false);
        sensorBinding.saveButton.setClickable(false);
        sensorBinding.saveButton.setBackgroundColor(Color.GRAY);
        sensorBinding.stopButton.setBackgroundColor(Color.GRAY);
        sensorBinding.stopButton.setClickable(false);
        sensorBinding.retClearButton.setBackgroundColor(Color.GREEN);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sensorBinding.timeSpinner.setAdapter(adapter);
        //sensorBinding.timeSpinner.setTe
        sensorBinding.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("10 seconds")){
                    sensorBinding.startButton.setClickable(true);
                    sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                    rate = 10000000;
                    rate2 = 10000;
                    Log.d("10", "10");
                }else{
                    if(parent.getItemAtPosition(position).equals("1 second")){
                        sensorBinding.startButton.setClickable(true);
                        sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                        rate = 1000000;
                        rate2 = 1000;
                        Log.d("1", "1");
                    }else{
                        if(parent.getItemAtPosition(position).equals("0.2 seconds")){
                            sensorBinding.startButton.setClickable(true);
                            sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                            rate = SensorManager.SENSOR_DELAY_NORMAL;
                            rate2 = 200;
                            Log.d("0.2", "0.2");
                        }else{
                            if(parent.getItemAtPosition(position).equals("0.02 seconds")){
                                sensorBinding.startButton.setClickable(true);
                                sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                                rate = SensorManager.SENSOR_DELAY_GAME;
                                rate2 = 20;
                                Log.d("0.02", "0.02");
                            }else{
                                if(parent.getItemAtPosition(position).equals("0.06 seconds")){
                                    sensorBinding.startButton.setClickable(true);
                                    sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                                    rate = SensorManager.SENSOR_DELAY_UI;
                                    rate2 = 60;
                                    Log.d("0.06", "0.06");
                                }else{
                                    if(parent.getItemAtPosition(position).equals("0 seconds")){
                                        sensorBinding.startButton.setClickable(true);
                                        sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                                        rate = SensorManager.SENSOR_DELAY_FASTEST;
                                        rate2 = 0;
                                        Log.d("0", "0");
                                    }else{
                                        sensorBinding.startButton.setClickable(false);
                                        sensorBinding.startButton.setBackgroundColor(Color.GRAY);
                                        sensorBinding.shareButton.setBackgroundColor(Color.GRAY);
                                        sensorBinding.shareButton.setClickable(false);
                                        sensorBinding.saveButton.setClickable(false);
                                        sensorBinding.saveButton.setBackgroundColor(Color.GRAY);
                                        sensorBinding.stopButton.setBackgroundColor(Color.GRAY);
                                        sensorBinding.stopButton.setClickable(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for(int i = 0; i < ssViewModel.getSensor().size(); i++){
            //Log.d("Valor: ", String.valueOf(ssViewModel.getSensor().get(i)));
            if(ssViewModel.getSensor().get(i) == sensorName.ACCELEROMETER){
                accelerometer = accManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                accViewModel.setOn(true);
                continue;
            }
            if(ssViewModel.getSensor().get(i) == sensorName.GRAVITY){
                gravity = gravManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                gravViewModel.setOn(true);
                continue;
            }
            if(ssViewModel.getSensor().get(i) == sensorName.GYROSCOPE){
                gyroscope = gyroManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                gyroViewModel.setOn(true);
                continue;
            }
            if(ssViewModel.getSensor().get(i) == sensorName.MOTION_DETECT){
                motion = motionManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
                motViewModel.setOn(true);
                continue;
            }
            if(ssViewModel.getSensor().get(i) == sensorName.ROTATION_VECTOR){
                rotation = rotationManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                rotViewModel.setOn(true);
                continue;
            }
            if(ssViewModel.getSensor().get(i) == sensorName.STEP_COUNTER){
                stepCounter = stepcounterManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                stViewModel.setOn(true);
                continue;
            }
            if(ssViewModel.getSensor().get(i) == sensorName.GPS){
                gpsViewModel.setOn(true);
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            }
        }

        accViewModel.getAllAccelerometer().observe(getViewLifecycleOwner(), new Observer<List<accelerometer>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<accelerometer> accelerometers) {
                allAcc = Objects.requireNonNull(accelerometers);
                accViewModel.updateList(allAcc);
            }
        });
        gyroViewModel.getAllGyroscope().observe(getViewLifecycleOwner(), new Observer<List<gyroscope>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<gyroscope> gyroscopes) {
                allGyroscope = Objects.requireNonNull(gyroscopes);
                gyroViewModel.updateList(allGyroscope);
            }
        });
        rotViewModel.getAllRotation().observe(getViewLifecycleOwner(), new Observer<List<rotation>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<rotation> rotations) {
                allRotation = Objects.requireNonNull(rotations);
                rotViewModel.updateList(allRotation);
            }
        });
        stViewModel.getAllStep().observe(getViewLifecycleOwner(), new Observer<List<stepCounter>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<stepCounter> stepCounters) {
                allStep = Objects.requireNonNull(stepCounters);
                stViewModel.updateList(allStep);
            }
        });
        gravViewModel.getAllGravity().observe(getViewLifecycleOwner(), new Observer<List<gravity>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<gravity> gravities) {
                try {
                    allGravity = Objects.requireNonNull(gravities);
                    gravViewModel.updateList(allGravity);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        motViewModel.getAllMotion().observe(getViewLifecycleOwner(), new Observer<List<motion>>() {
            @Override
            public void onChanged(List<motion> motions) {
                allMotion = Objects.requireNonNull(motions);
                motViewModel.updateList(allMotion);
            }
        });
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

        ssViewModel.clear();
        startTracking(this);
        stopTracking(this);
        save();
        share();
        returnClear();
    }

    public void returnClear(){
        sensorBinding.retClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accViewModel.clear();
                gravViewModel.clear();
                gyroViewModel.clear();
                rotViewModel.clear();
                motViewModel.clear();
                stViewModel.clear();
                if(ssViewModel.isAdmin()){
                    Navigation.findNavController(requireView()).navigate(R.id.adminFragment);
                }else{
                    Navigation.findNavController(requireView()).navigate(R.id.normalFragment);
                }

            }
        });
    }

    public void startTracking(SensorFragment view){
        sensorBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorBinding.startButton.isClickable()){
                    if(accViewModel.isOn()){
                        accManager.registerListener((SensorEventListener) view, accelerometer, rate);
                    }
                    if(gravViewModel.isOn()){
                        gravManager.registerListener((SensorEventListener) view, gravity, rate);
                    }
                    if(gyroViewModel.isOn()){
                        gyroManager.registerListener((SensorEventListener) view, gyroscope, rate);
                    }
                    if(motViewModel.isOn()){
                        motionManager.registerListener((SensorEventListener) view, motion, rate);
                    }
                    if(rotViewModel.isOn()){
                        rotationManager.registerListener((SensorEventListener) view, rotation, rate);
                    }
                    if(stViewModel.isOn()){
                        stepcounterManager.registerListener((SensorEventListener) view, stepCounter, rate);
                    }
                    if(gpsViewModel.isOn()){
                        if (checkPermission()) {
                            if (isLocationEnabled()) {
                                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Location> task) {
                                        Location newLocation = task.getResult();
                                        if (newLocation != null){
                                            calendar = Calendar.getInstance();
                                            simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
                                            dateTime = simpleDateFormat.format(calendar.getTime());
                                            gpsLocation gps = new gpsLocation(id, newLocation.getLatitude(), newLocation.getLongitude(), dateTime);
                                            gpsViewModel.insert(gps);
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
                    sensorBinding.stopButton.setBackgroundColor(Color.GREEN);
                    sensorBinding.stopButton.setClickable(true);
                    sensorBinding.saveButton.setClickable(false);
                    sensorBinding.saveButton.setBackgroundColor(Color.GRAY);
                    sensorBinding.shareButton.setBackgroundColor(Color.GRAY);
                    sensorBinding.shareButton.setClickable(false);
                }
            }
        });
    }
    public void share(){
        sensorBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorBinding.shareButton.isClickable()){
                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    emailIntent.setType("*/*");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Log File");
                    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
                    if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                        startActivity(emailIntent);
                    }
                }
            }
        });
    }
    public void stopTracking(SensorFragment view){
        sensorBinding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorBinding.stopButton.isClickable()){
                    if(accViewModel.isOn()){
                        accManager.unregisterListener((SensorEventListener) view);
                    }
                    if(gravViewModel.isOn()){
                        gravManager.unregisterListener((SensorEventListener) view);
                    }
                    if(gyroViewModel.isOn()){
                        gyroManager.unregisterListener((SensorEventListener) view);
                    }
                    if(motViewModel.isOn()){
                        motionManager.unregisterListener((SensorEventListener) view);
                    }
                    if(rotViewModel.isOn()){
                        rotationManager.unregisterListener((SensorEventListener) view);
                    }
                    if(stViewModel.isOn()){
                        stepcounterManager.unregisterListener((SensorEventListener) view);
                    }
                    if(gpsViewModel.isOn()){
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                    sensorBinding.stopButton.setBackgroundColor(Color.GRAY);
                    sensorBinding.stopButton.setClickable(false);
                    sensorBinding.saveButton.setClickable(true);
                    sensorBinding.saveButton.setBackgroundColor(Color.GREEN);
                }
            }
        });
    }

    public void save(){
        sensorBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorBinding.saveButton.isClickable()){
                    if(accViewModel.isOn()){
                        String texto = "Ax" + ";Ay" + ";Az" + ";Date and Time" + "\n";
                        for (int i = 0; i < accViewModel.getAccelerometer().size(); i++) {
                            String ax = String.valueOf(accViewModel.getAccelerometer().get(i).getAx());
                            String ay = String.valueOf(accViewModel.getAccelerometer().get(i).getAy());
                            String az = String.valueOf(accViewModel.getAccelerometer().get(i).getAz());
                            String dataTime = accViewModel.getAccelerometer().get(i).getDateTime();
                            texto = texto + ax + ";" + ay + ";" + az + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "accLog.csv");
                        accLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(accLog);
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
                    if(gravViewModel.isOn()){
                        String texto = "Gx" + ";Gy" + ";Gz" + ";Date and Time" + "\n";
                        for (int i = 0; i < gravViewModel.getGravity().size(); i++) {
                            String gx = String.valueOf(gravViewModel.getGravity().get(i).getGx());
                            String gy = String.valueOf(gravViewModel.getGravity().get(i).getGy());
                            String gz = String.valueOf(gravViewModel.getGravity().get(i).getGz());
                            String dataTime = gravViewModel.getGravity().get(i).getDateTime();
                            texto = texto + gx + ";" + gy + ";" + gz + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "gravLog.csv");
                        gravLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(gravLog);
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
                    if(gyroViewModel.isOn()){
                        String texto = "Wx" + ";Wy" + ";Wz" + ";Date and Time" + "\n";
                        for (int i = 0; i < gyroViewModel.getGyroscope().size(); i++) {
                            String wx = String.valueOf(gyroViewModel.getGyroscope().get(i).getWx());
                            String wy = String.valueOf(gyroViewModel.getGyroscope().get(i).getWy());
                            String wz = String.valueOf(gyroViewModel.getGyroscope().get(i).getWz());
                            String dataTime = gyroViewModel.getGyroscope().get(i).getDateTime();
                            texto = texto + wx + ";" + wy + ";" + wz + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "gyroLog.csv");
                        gyroLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(gyroLog);
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
                    if(motViewModel.isOn()){
                        String texto = "Motion" + ";Date and Time" + "\n";
                        for (int i = 0; i < motViewModel.getMotion().size(); i++) {
                            String mot = String.valueOf(motViewModel.getMotion().get(i).getMotion());
                            String dataTime = motViewModel.getMotion().get(i).getDateTime();
                            texto = texto + mot + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "motionLog.csv");
                        motionLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(motionLog);
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
                    if(rotViewModel.isOn()){
                        String texto = "Xsin" + ";Ysin" + ";Zsin" + ";Cos" + ";Estimated Heading Accuracy" + ";Date and Time" + "\n";
                        for (int i = 0; i < rotViewModel.getRotation().size(); i++) {
                            String xsin = String.valueOf(rotViewModel.getRotation().get(i).getXsin());
                            String ysin = String.valueOf(rotViewModel.getRotation().get(i).getYsin());
                            String zsin = String.valueOf(rotViewModel.getRotation().get(i).getZsin());
                            String cos = String.valueOf(rotViewModel.getRotation().get(i).getCos());
                            String sha = String.valueOf(rotViewModel.getRotation().get(i).getSha());
                            String dataTime = rotViewModel.getRotation().get(i).getDateTime();
                            texto = texto + xsin + ";" + ysin + ";" + zsin + ";" + cos + ";" + sha + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "rotLog.csv");
                        rotLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(rotLog);
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
                    if(stViewModel.isOn()){
                        String texto = "StepCounter" + ";Date and Time" + "\n";
                        for (int i = 0; i < stViewModel.getStep().size(); i++) {
                            String step = String.valueOf(stViewModel.getStep().get(i).getStep());
                            String dataTime = stViewModel.getStep().get(i).getDateTime();
                            texto = texto + step + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "stepLog.csv");
                        stepLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(stepLog);
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
                    if(gpsViewModel.isOn()){
                        String texto = "Latitude" + ";Longitude" + ";Date and TIme" + "\n";
                        for (int i = 0; i < gpsViewModel.getGps().size(); i++) {
                            String longitude = String.valueOf(gpsViewModel.getGps().get(i).getLongitude());
                            String latitude = String.valueOf(gpsViewModel.getGps().get(i).getLatitude());
                            String dataTime = gpsViewModel.getGps().get(i).getDateTime();
                            texto = texto + latitude + ";" + longitude + ";" + dataTime + "\n";
                        }
                        //Log.d("Textao: ", texto);
                        File file = new File(requireActivity().getExternalFilesDir(null), "gpsLog.csv");
                        gpsLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                        uriList.add(gpsLog);
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
                    sensorBinding.shareButton.setBackgroundColor(Color.GREEN);
                    sensorBinding.shareButton.setClickable(true);
                }

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime());
        if(event.sensor.getStringType().equals("android.sensor.linear_acceleration")){
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];
            roomSensors.entities.accelerometer acc = new accelerometer(ax, ay, az, dateTime);
            accViewModel.insert(acc);
        }else{
            if(event.sensor.getStringType().equals("android.sensor.gyroscope")){
                float wx = event.values[0];
                float wy = event.values[1];
                float wz = event.values[2];
                roomSensors.entities.gyroscope gyro = new gyroscope(wx, wy, wz, dateTime);
                gyroViewModel.insert(gyro);
            }else{
                if(event.sensor.getStringType().equals("android.sensor.gravity")){
                    float gx = event.values[0];
                    float gy = event.values[1];
                    float gz = event.values[2];
                    roomSensors.entities.gravity grav = new gravity(gx, gy, gz, dateTime);
                    gravViewModel.insert(grav);
                }else{
                    if(event.sensor.getStringType().equals("android.sensor.rotation_vector")){
                        float xsin = event.values[0];
                        float ysin = event.values[1];
                        float zsin = event.values[2];
                        float cos = event.values[3];
                        float estimated = event.values[4];
                        roomSensors.entities.rotation rot = new rotation(xsin, ysin, zsin, cos, estimated, dateTime);
                        rotViewModel.insert(rot);
                    }else{
                        if(event.sensor.getStringType().equals("android.sensor.step_counter")){
                            float st = event.values[0];
                            roomSensors.entities.stepCounter step = new stepCounter(st, dateTime);
                            stViewModel.insert(step);
                        }else{
                            if(event.sensor.getStringType().equals("android.sensor.motion_detect")){
                                float moti = event.values[0];
                                roomSensors.entities.motion mot = new motion(moti, dateTime);
                                motViewModel.insert(mot);
                            }
                        }
                    }
                }
            }
        }

        //Log.d("opa: ", event.sensor.getStringType());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("MissingPermission")
    void requestNewLocationData(){
        LocationRequest requestLocation = LocationRequest.create();
        requestLocation.setInterval(rate2);
        //requestLocation.setFastestInterval(50);
        requestLocation.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        fusedLocationClient.requestLocationUpdates(requestLocation, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
            dateTime = simpleDateFormat.format(calendar.getTime());
            gpsLocation gps = new gpsLocation(id, lastLocation.getLatitude(), lastLocation.getLongitude(), dateTime);
            gpsViewModel.insert(gps);
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
  //  @Override
  /*  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_ID){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               startTracking();
            }
        }
    }*/
}