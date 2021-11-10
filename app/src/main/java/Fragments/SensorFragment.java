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

import android.os.Looper;
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

import ViewModel.Environment.ambtempViewModel;
import ViewModel.Environment.humidityViewModel;
import ViewModel.Environment.lightViewModel;
import ViewModel.Environment.pressureViewModel;
import ViewModel.Motion.accelerometerViewModel;
import ViewModel.Motion.gpsLocationViewModel;
import ViewModel.Motion.gravityViewModel;
import ViewModel.Motion.gyroscopeViewModel;
import ViewModel.Motion.motionViewModel;
import ViewModel.Motion.rotationViewModel;
import ViewModel.Position.gameViewModel;
import ViewModel.Position.magneticViewModel;
import ViewModel.Position.proximityViewModel;
import ViewModel.sensorViewModel;
import ViewModel.Motion.stepViewModel;
import roomGPS.gpsLocation;
import roomSensors.entities.accelerometer;
import roomSensors.entities.ambientTemperature;
import roomSensors.entities.gameRotation;
import roomSensors.entities.gravity;
import roomSensors.entities.gyroscope;
import roomSensors.entities.humidity;
import roomSensors.entities.illuminance;
import roomSensors.entities.magneticField;
import roomSensors.entities.motion;
import roomSensors.entities.pressure;
import roomSensors.entities.proximity;
import roomSensors.entities.rotation;
import roomSensors.entities.stepCounter;


public class SensorFragment extends Fragment implements SensorEventListener {
    private FragmentSensorBinding sensorBinding;
    int PERMISSION_ID = 44;
    int id = 1;

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
    private Sensor ambientTemperature;
    private SensorManager tempManager;
    private Sensor illuminance;
    private SensorManager illuminanceManager;
    private Sensor pressure;
    private SensorManager pressureManager;
    private Sensor humidity;
    private SensorManager humidityManager;
    private Sensor game;
    private SensorManager gameManager;
    private Sensor magnetic;
    private SensorManager magneticManager;
    private Sensor prox;
    private SensorManager proximityManager;
    private FusedLocationProviderClient fusedLocationClient;


    private sensorViewModel ssViewModel;
    private accelerometerViewModel accViewModel;
    private gravityViewModel gravViewModel;
    private gyroscopeViewModel gyroViewModel;
    private motionViewModel motViewModel;
    private rotationViewModel rotViewModel;
    private stepViewModel stViewModel;
    private ambtempViewModel ambViewModel;
    private humidityViewModel humViewModel;
    private lightViewModel illuViewModel;
    private pressureViewModel pressViewModel;
    private proximityViewModel proxViewModel;
    private magneticViewModel magViewModel;
    private gameViewModel gViewModel;
    private gpsLocationViewModel gpsViewModel;

    private String date;
    private String time;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private Calendar calendar;


    ArrayList<Uri> uriList = new ArrayList<>();

    private List<accelerometer> allAcc;
    private List<gyroscope> allGyroscope;
    private List<rotation> allRotation;
    private List<stepCounter> allStep;
    private List<gravity> allGravity;
    private List<motion> allMotion;
    private List<gpsLocation> allGps;
    private List<ambientTemperature> allTemp;
    private List<roomSensors.entities.humidity> allHumidity;
    private List<roomSensors.entities.illuminance> allIlluminance;
    private List<roomSensors.entities.pressure> allPressure;
    private List<magneticField> allMagnetic;
    private List<proximity> allProximity;
    private List<gameRotation> allGame;

    private int rate;
    private int rate2;

    private List<Float> illuValue = new ArrayList<>();
    private List<Float> accValue = new ArrayList<>();
    private List<Float> gyroValue = new ArrayList<>();
    private List<Float> azimuthValue = new ArrayList<>();
    private List<Float> pitchValue = new ArrayList<>();
    private List<Float> rollValue = new ArrayList<>();

    float[] accelerometerReading = new float[3];
    float[] magnetometerReading = new float[3];
    float[] gyroscopeReading = new float[3];
    float[] gravityReading = new float[3];
    float[] rotationVectorReading = new float[3];
    float[] stepCounterReading = new float[1];
    float[] gameReading = new float[3];
    float[] proximityReading = new float[1];
    float[] illuminanceReading = new float[1];
    float[] temperatureReading = new float[1];
    float[] pressureReading = new float[1];
    float[] motionReading = new float[1];
    float[] humidityReading = new float[1];
    final float[] rotationMatrix = new float[9];
    final float[] orientationAngles = new float[3];

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

        //Setup the spinner with 3 possibles rates for sensors
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sensorBinding.timeSpinner.setAdapter(adapter);
        sensorBinding.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("0.2 seconds")){
                    sensorBinding.startButton.setClickable(true);
                    sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                    rate = SensorManager.SENSOR_DELAY_NORMAL;
                    rate2 = 200;
                    Log.d("0.2", String.valueOf(rate));
                }else{
                    if(parent.getItemAtPosition(position).equals("0.02 seconds")){
                        sensorBinding.startButton.setClickable(true);
                        sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                        rate = SensorManager.SENSOR_DELAY_GAME;
                        rate2 = 20;
                        Log.d("0.02", String.valueOf(rate));
                    }else{
                        if(parent.getItemAtPosition(position).equals("0.06 seconds")){
                            sensorBinding.startButton.setClickable(true);
                            sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                            rate = SensorManager.SENSOR_DELAY_UI;
                            rate2 = 60;
                            Log.d("0.06", String.valueOf(rate));
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startViewModel();
        initializeSensors();
        firstSetup();
        updateList();
        startTracking(this);
        stopTracking(this);
        save();
        share();
        clear(this);
        observeCheckbox();

    }

    //Initial setup of the buttons
    public void firstSetup(){
        sensorBinding.startButton.setClickable(false);
        sensorBinding.startButton.setBackgroundColor(Color.GRAY);
        sensorBinding.shareButton.setBackgroundColor(Color.GRAY);
        sensorBinding.shareButton.setClickable(false);
        sensorBinding.saveButton.setClickable(false);
        sensorBinding.saveButton.setBackgroundColor(Color.GRAY);
        sensorBinding.stopButton.setBackgroundColor(Color.GRAY);
        sensorBinding.stopButton.setClickable(false);
        sensorBinding.clearButton.setBackgroundColor(Color.GREEN);

        sensorBinding.startButton.setVisibility(View.INVISIBLE);
        sensorBinding.saveButton.setVisibility(View.INVISIBLE);
        sensorBinding.stopButton.setVisibility(View.INVISIBLE);
        sensorBinding.shareButton.setVisibility(View.INVISIBLE);
        sensorBinding.timeSpinner.setVisibility(View.INVISIBLE);
        sensorBinding.clearButton.setVisibility(View.INVISIBLE);
        sensorBinding.title.setVisibility(View.VISIBLE);
        sensorBinding.subtitle.setVisibility(View.VISIBLE);
    }

    public void initializeSensors(){
        accManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gravManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        motionManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        rotationManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepcounterManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        tempManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        pressureManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        illuminanceManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        humidityManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gameManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        magneticManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        proximityManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    //observe the changes in the checkbox passing every change for the respective viewModel
    public void observeCheckbox(){
        illuViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                illuViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        ambViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                ambViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        humViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                humViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        pressViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                pressViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        accViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                accViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        gpsViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                gpsViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        gravViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                gravViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        gyroViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                gyroViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        motViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                motViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        rotViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                rotViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        stViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                stViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        proxViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                proxViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        magViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                magViewModel.setOn(aBoolean);
                showButtons();
            }
        });

        gViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                gViewModel.setOn(aBoolean);
                showButtons();
            }
        });
    }

    //This function is responsible to correctly change the status of each button based on the status of the boxes.
    public void showButtons(){
        if(ambViewModel.isOn() || humViewModel.isOn() || illuViewModel.isOn() || pressViewModel.isOn() || accViewModel.isOn() || gpsViewModel.isOn() || gravViewModel.isOn() || gyroViewModel.isOn() || motViewModel.isOn() || rotViewModel.isOn() || stViewModel.isOn() || proxViewModel.isOn() || magViewModel.isOn() || gViewModel.isOn()){
            sensorBinding.startButton.setVisibility(View.VISIBLE);
            sensorBinding.saveButton.setVisibility(View.VISIBLE);
            sensorBinding.stopButton.setVisibility(View.VISIBLE);
            sensorBinding.shareButton.setVisibility(View.VISIBLE);
            sensorBinding.timeSpinner.setVisibility(View.VISIBLE);
            sensorBinding.clearButton.setVisibility(View.VISIBLE);
            sensorBinding.title.setVisibility(View.INVISIBLE);
            sensorBinding.subtitle.setVisibility(View.INVISIBLE);
        }else{
            sensorBinding.startButton.setVisibility(View.INVISIBLE);
            sensorBinding.saveButton.setVisibility(View.INVISIBLE);
            sensorBinding.stopButton.setVisibility(View.INVISIBLE);
            sensorBinding.shareButton.setVisibility(View.INVISIBLE);
            sensorBinding.timeSpinner.setVisibility(View.INVISIBLE);
            sensorBinding.clearButton.setVisibility(View.INVISIBLE);
            sensorBinding.title.setVisibility(View.VISIBLE);
            sensorBinding.subtitle.setVisibility(View.VISIBLE);
        }
    }

    public void startViewModel(){
        accViewModel = new ViewModelProvider(requireActivity()).get(accelerometerViewModel.class);
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        gravViewModel = new ViewModelProvider(requireActivity()).get(gravityViewModel.class);
        gyroViewModel = new ViewModelProvider(requireActivity()).get(gyroscopeViewModel.class);
        motViewModel = new ViewModelProvider(requireActivity()).get(motionViewModel.class);
        rotViewModel = new ViewModelProvider(requireActivity()).get(rotationViewModel.class);
        stViewModel = new ViewModelProvider(requireActivity()).get(stepViewModel.class);
        gpsViewModel = new ViewModelProvider(requireActivity()).get(gpsLocationViewModel.class);
        ambViewModel = new ViewModelProvider(requireActivity()).get(ambtempViewModel.class);
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        ambViewModel = new ViewModelProvider(requireActivity()).get(ambtempViewModel.class);
        pressViewModel = new ViewModelProvider(requireActivity()).get(pressureViewModel.class);
        humViewModel = new ViewModelProvider(requireActivity()).get(humidityViewModel.class);
        illuViewModel = new ViewModelProvider(requireActivity()).get(lightViewModel.class);
        proxViewModel = new ViewModelProvider(requireActivity()).get(proximityViewModel.class);
        gViewModel = new ViewModelProvider(requireActivity()).get(gameViewModel.class);
        magViewModel = new ViewModelProvider(requireActivity()).get(magneticViewModel.class);
    }

    public void updateList(){
        ambViewModel.getAllAmbTempSensor().observe(getViewLifecycleOwner(), new Observer<List<roomSensors.entities.ambientTemperature>>() {
            @Override
            public void onChanged(List<roomSensors.entities.ambientTemperature> ambientTemperatures) {
                allTemp = Objects.requireNonNull(ambientTemperatures);
                ambViewModel.update(allTemp);
            }
        });
        illuViewModel.getAllIlluminanceSensor().observe(getViewLifecycleOwner(), new Observer<List<roomSensors.entities.illuminance>>() {
            @Override
            public void onChanged(List<roomSensors.entities.illuminance> illuminances) {
                allIlluminance = Objects.requireNonNull(illuminances);
                illuViewModel.update(allIlluminance);
            }
        });
        humViewModel.getAllHumiditySensor().observe(getViewLifecycleOwner(), new Observer<List<roomSensors.entities.humidity>>() {
            @Override
            public void onChanged(List<roomSensors.entities.humidity> humidities) {
                allHumidity = Objects.requireNonNull(humidities);
                humViewModel.update(allHumidity);
            }
        });
        pressViewModel.getAllPressureSensor().observe(getViewLifecycleOwner(), new Observer<List<roomSensors.entities.pressure>>() {
            @Override
            public void onChanged(List<roomSensors.entities.pressure> pressures) {
                allPressure = Objects.requireNonNull(pressures);
                pressViewModel.update(allPressure);
            }
        });
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
            @RequiresApi(api = Build.VERSION_CODES.N)
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
        proxViewModel.getAllProximitySensor().observe(getViewLifecycleOwner(), new Observer<List<proximity>>() {
            @Override
            public void onChanged(List<proximity> proximities) {
                allProximity = Objects.requireNonNull(proximities);
                proxViewModel.updateList(allProximity);
            }
        });
        gViewModel.getAllGameSensor().observe(getViewLifecycleOwner(), new Observer<List<gameRotation>>() {
            @Override
            public void onChanged(List<gameRotation> gameRotations) {
                allGame = Objects.requireNonNull(gameRotations);
                gViewModel.updateList(allGame);
            }
        });
        magViewModel.getAllMagneticSensor().observe(getViewLifecycleOwner(), new Observer<List<magneticField>>() {
            @Override
            public void onChanged(List<magneticField> magneticFields) {
                allMagnetic = Objects.requireNonNull(magneticFields);
                magViewModel.updateList(allMagnetic);
            }
        });
    }

    //unregister all sensors
    public void removeAll(SensorFragment view){
        illuminanceManager.unregisterListener((SensorEventListener) view);
        tempManager.unregisterListener((SensorEventListener) view);
        pressureManager.unregisterListener((SensorEventListener) view);
        humidityManager.unregisterListener((SensorEventListener) view);
        accManager.unregisterListener((SensorEventListener) view);
        gravManager.unregisterListener((SensorEventListener) view);
        gyroManager.unregisterListener((SensorEventListener) view);
        motionManager.unregisterListener((SensorEventListener) view);
        rotationManager.unregisterListener((SensorEventListener) view);
        stepcounterManager.unregisterListener((SensorEventListener) view);
        proximityManager.unregisterListener((SensorEventListener) view);
        magneticManager.unregisterListener((SensorEventListener) view);
        gameManager.unregisterListener((SensorEventListener) view);
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    //Remove the cache data of others tracking
    public void clear(SensorFragment view){
        sensorBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll(view);
                if(ambViewModel.isOn()){
                    ambViewModel.clear();
                    ambViewModel.setOn(false);
                    ambViewModel.setIsCheck(false);
                }
                if(illuViewModel.isOn()){
                    illuViewModel.clear();
                    illuViewModel.setOn(false);
                    illuViewModel.setIsCheck(false);
                }
                if(pressViewModel.isOn()){
                    pressViewModel.clear();
                    pressViewModel.setOn(false);
                    pressViewModel.setIsCheck(false);
                }
                if(humViewModel.isOn()){
                    humViewModel.clear();
                    humViewModel.setOn(false);
                    humViewModel.setIsCheck(false);
                }
                if(accViewModel.isOn()){
                    accViewModel.clear();
                    accViewModel.setOn(false);
                    accViewModel.setIsCheck(false);
                }
                if(gravViewModel.isOn()){
                    gravViewModel.clear();
                    gravViewModel.setOn(false);
                    gravViewModel.setIsCheck(false);
                }
                if(gyroViewModel.isOn()){
                    gyroViewModel.clear();
                    gyroViewModel.setOn(false);
                    gyroViewModel.setIsCheck(false);
                }
                if(motViewModel.isOn()){
                    motViewModel.clear();
                    motViewModel.setOn(false);
                    motViewModel.setIsCheck(false);
                }
                if(rotViewModel.isOn()){
                    rotViewModel.clear();
                    rotViewModel.setOn(false);
                    rotViewModel.setIsCheck(false);
                }
                if(stViewModel.isOn()){
                    stViewModel.clear();
                    stViewModel.setOn(false);
                    stViewModel.setIsCheck(false);
                }
                if(gpsViewModel.isOn()){
                    gpsViewModel.clear();
                    gpsViewModel.setOn(false);
                    gpsViewModel.setIsCheck(false);
                }
                if(proxViewModel.isOn()){
                    proxViewModel.clear();
                    proxViewModel.setOn(false);
                    proxViewModel.setIsCheck(false);
                }
                if(magViewModel.isOn()){
                    magViewModel.clear();
                    magViewModel.setOn(false);
                    magViewModel.setIsCheck(false);
                }
                if(gViewModel.isOn()){
                    gViewModel.clear();
                    gViewModel.setOn(false);
                    gViewModel.setIsCheck(false);
                }
                uriList.removeAll(uriList);
                sensorBinding.shareButton.setClickable(false);
                sensorBinding.shareButton.setBackgroundColor(Color.GRAY);
                sensorBinding.saveButton.setBackgroundColor(Color.GRAY);
                sensorBinding.saveButton.setClickable(false);
                sensorBinding.stopButton.setBackgroundColor(Color.GRAY);
                sensorBinding.stopButton.setClickable(false);
            }
        });
    }

    //Initialize the tracking of all sensors and methods that are on
    public void startTracking(SensorFragment view){
        sensorBinding.startButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(sensorBinding.startButton.isClickable()){
                    if(ambViewModel.isOn()){
                        ambientTemperature = tempManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                        tempManager.registerListener((SensorEventListener) view, ambientTemperature, rate);
                    }
                    if(illuViewModel.isOn()){
                        illuminance = illuminanceManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                        Log.d("Max Delay Light", String.valueOf(illuminance.getMaxDelay()));
                        Log.d("Min Delay Light", String.valueOf(illuminance.getMinDelay()));
                        illuminanceManager.registerListener((SensorEventListener) view, illuminance, rate);
                    }
                    if(pressViewModel.isOn()){
                        pressure = pressureManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                        pressureManager.registerListener((SensorEventListener) view, pressure, rate);
                    }
                    if(humViewModel.isOn()){
                        humidity = humidityManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                        humidityManager.registerListener((SensorEventListener) view, humidity, rate);
                    }
                    if(accViewModel.isOn()){
                        accelerometer = accManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                        Log.d("Max Delay Accelerometer", String.valueOf(accelerometer.getMaxDelay()));
                        Log.d("Min Delay Accelerometer", String.valueOf(accelerometer.getMinDelay()));
                        //Log.d("Reporting Mode", String.valueOf(accelerometer.getReportingMode()));
                        //Log.d("Fifo Max Event Count", String.valueOf(accelerometer.getFifoMaxEventCount()));
                        accManager.registerListener((SensorEventListener) view, accelerometer, 100000);
                    }
                    if(gravViewModel.isOn()){
                        gravity = gravManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                        Log.d("Max Delay Gravity", String.valueOf(gravity.getMaxDelay()));
                        Log.d("Min Delay Gravity", String.valueOf(gravity.getMinDelay()));
                        gravManager.registerListener((SensorEventListener) view, gravity, rate);
                    }
                    if(gyroViewModel.isOn()){
                        gyroscope = gyroManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                        Log.d("Max Delay Gyro", String.valueOf(gyroscope.getMaxDelay()));
                        Log.d("Min Delay Gyro", String.valueOf(gyroscope.getMinDelay()));
                        gyroManager.registerListener((SensorEventListener) view, gyroscope, rate);
                    }
                    if(motViewModel.isOn()){
                        motion = motionManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
                        motionManager.registerListener((SensorEventListener) view, motion, rate);
                    }
                    if(rotViewModel.isOn()){
                        rotation = rotationManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                        Log.d("Max Delay Rotation", String.valueOf(rotation.getMaxDelay()));
                        Log.d("Min Delay Rotation", String.valueOf(rotation.getMinDelay()));
                        rotationManager.registerListener((SensorEventListener) view, rotation, rate);
                    }
                    if(stViewModel.isOn()){
                        stepCounter = stepcounterManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                        stepcounterManager.registerListener((SensorEventListener) view, stepCounter, rate);
                    }
                    if(proxViewModel.isOn()){
                        prox = proximityManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                        Log.d("Max Delay Proximity", String.valueOf(prox.getMaxDelay()));
                        Log.d("Min Delay Proximity", String.valueOf(prox.getMinDelay()));
                        proximityManager.registerListener((SensorEventListener) view, prox, rate);
                    }
                    if(gViewModel.isOn()){
                        game = gameManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                        Log.d("Max Delay Game", String.valueOf(game.getMaxDelay()));
                        Log.d("Min Delay Game", String.valueOf(game.getMinDelay()));
                        gameManager.registerListener((SensorEventListener) view, game, rate);
                    }
                    if(magViewModel.isOn()){
                        magnetic = magneticManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                        Log.d("Max Delay Magnetic", String.valueOf(magnetic.getMaxDelay()));
                        Log.d("Min Delay Magnetic", String.valueOf(magnetic.getMinDelay()));
                        magneticManager.registerListener((SensorEventListener) view, magnetic, rate);
                    }
                    if(gpsViewModel.isOn()){
                        if (checkPermission()) {
                            if (isLocationEnabled()) {
                                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Location> task) {
                                        Location newLocation = task.getResult();
                                        if (newLocation != null){
                                            getDateTime();
                                            gpsLocation gps = new gpsLocation(id, newLocation.getLatitude(), newLocation.getLongitude(), newLocation.getSpeed(), newLocation.getAltitude(), date, time);
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
                    sensorBinding.startButton.setBackgroundColor(Color.GRAY);
                    sensorBinding.startButton.setClickable(false);
                }
            }
        });
    }

    //Share the data collected
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
                    uriList.removeAll(uriList);
                }
            }
        });
    }

    //stop the tracking of all sensors and methods that were on
    public void stopTracking(SensorFragment view){
        sensorBinding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorBinding.stopButton.isClickable()){
                    if(ambViewModel.isOn()){
                        tempManager.unregisterListener((SensorEventListener) view);
                    }
                    if(illuViewModel.isOn()){
                        illuminanceManager.unregisterListener((SensorEventListener) view);
                    }
                    if(pressViewModel.isOn()){
                        pressureManager.unregisterListener((SensorEventListener) view);
                    }
                    if(humViewModel.isOn()){
                        humidityManager.unregisterListener((SensorEventListener) view);
                    }
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
                    if(proxViewModel.isOn()){
                        proximityManager.unregisterListener((SensorEventListener) view);
                    }
                    if(magViewModel.isOn()){
                        magneticManager.unregisterListener((SensorEventListener) view);
                    }
                    if(gViewModel.isOn()){
                        gameManager.unregisterListener((SensorEventListener) view);
                    }
                    if(gpsViewModel.isOn()){
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                    writeMethods();
                    sensorBinding.stopButton.setBackgroundColor(Color.GRAY);
                    sensorBinding.stopButton.setClickable(false);
                    sensorBinding.saveButton.setClickable(true);
                    sensorBinding.saveButton.setBackgroundColor(Color.GREEN);
                    sensorBinding.startButton.setClickable(true);
                    sensorBinding.startButton.setBackgroundColor(Color.GREEN);
                }
            }
        });
    }

    //Write the csv files with the informations of each method on
    public void writeMethods(){
        if(ssViewModel.isSunLightBool()){
            Boolean sunExposure;
            List<Float> slidingList;
            int windowSize = 5;
            slidingList = movingAverage(illuValue, windowSize);
            String text = "SunLight Exposure" + "," + "rate: " + rate + "\n" + " Time" + "," + "Sunlight" + "," + "Date" + "\n";
            for(int i = 0; i < slidingList.size(); i++){
                String date = String.valueOf(illuViewModel.getAllIlluminance().get(i).getDate());
                String time = String.valueOf(illuViewModel.getAllIlluminance().get(i).getTime());
                if(slidingList.get(i) > 10000){
                    sunExposure = true;
                }else{
                    sunExposure = false;
                }
                text = text + time + "," + sunExposure + "," + date + "\n";
            }
            String filename = "sunlightMethod.csv";
            writeFile(text, filename);
        }
        if(ssViewModel.isSpeedBool()){
            String text = "Speed Method" + "," + "rate: " + (float)rate2/1000 + "\n" + "Time" + "," + "Speed" + "," + "Date" + "\n";
            for (int i = 0; i < gpsViewModel.getGps().size(); i++) {
                double speed = gpsViewModel.getGps().get(i).getSpeed();
                String date = gpsViewModel.getGps().get(i).getDate();
                String time = gpsViewModel.getGps().get(i).getTime();
                text = text + time + "," + speed + "," + date + "\n";
            }
            String fileName = "speedMethod.csv";
            writeFile(text, fileName);
        }
        if(ssViewModel.isAltitudeBool()){
            String text = "Altitude Method" + "," + "rate: " + (float)rate2/1000 + "\n" + "Time" + "," + "Altitude" + "," + "Date" + "\n";
            for (int i = 0; i < gpsViewModel.getGps().size(); i++) {
                double altitude = gpsViewModel.getGps().get(i).getAltitude();
                String date = gpsViewModel.getGps().get(i).getDate();
                String time = gpsViewModel.getGps().get(i).getTime();
                text = text + time + "," + altitude + "," + date + "\n";
            }
            String fileName = "altitudeMethod.csv";
            writeFile(text, fileName);
        }
        if(ssViewModel.isNoMotionBool()){
            String text = "No Motion Method" + "," + "rate: " + (float)rate2/1000 + "\n" + "Time" + "," + "Motion" + "," + "Date" + "\n";
            Boolean motion;
            List<Float> slidingAccList;
            List<Float> slidingGyroList;
            int windowSize = 5;
            slidingAccList = movingAverage(accValue, windowSize);
            slidingGyroList = movingAverage(gyroValue, windowSize);
            for(int i = 0; i<slidingGyroList.size(); i++){
                String date = accViewModel.getAccelerometer().get(i).getDate();
                String time = accViewModel.getAccelerometer().get(i).getTime();
                if(slidingAccList.get(i) < 5 && slidingAccList.get(i) > -5 && slidingGyroList.get(i) > -5 && slidingGyroList.get(i) < 5){
                    motion = false;
                }else{
                    motion = true;
                }
                text = text + time + "," + motion + "," + date + "\n";
            }
            String fileName = "noMotionMethod.csv";
            writeFile(text, fileName);
        }
        if(ssViewModel.isCompassBool()){
            String text = "Compass Method" + "," + "rate: " + (float)rate2/1000 + "\n" + "Time" + "," + "Azimuth" + "," + "Pitch" + "," + "Roll" + "," + "Date" + "\n";
            for(int i=0; i<accViewModel.getAccelerometer().size(); i++){
                String date = accViewModel.getAccelerometer().get(i).getDate();
                String time = accViewModel.getAccelerometer().get(i).getTime();
                text = text + time + "," + azimuthValue.get(i) + "," + pitchValue.get(i) + "," + rollValue.get(i) + "," + date + "\n";
            }

            String fileName = "CompassMethod.csv";
            writeFile(text, fileName);
        }
    }

    //write the csv files of each sensor tracked
    public void writeFile(String text, String fileName){
        File file = new File(requireActivity().getExternalFilesDir(null), fileName);
        Uri log = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
        uriList.add(log);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Save the data collected
    public void save(){
        sensorBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorBinding.saveButton.isClickable()){
                    if(ambViewModel.isOn()){
                        String text = "Ambient Temperature" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Temperature" + "," + "Date" + "\n";
                        for(int i = 0; i< ambViewModel.getAllAmbTemp().size(); i++){
                            String temperature = String.valueOf(ambViewModel.getAllAmbTemp().get(i).getTemperature());
                            String date = String.valueOf(ambViewModel.getAllAmbTemp().get(i).getDate());
                            String time = String.valueOf(ambViewModel.getAllAmbTemp().get(i).getTime());
                            text = text + time + "," + temperature + "," + date + "\n";
                        }
                        String fileName = "temperatureLog.csv";
                        writeFile(text, fileName);
                    }
                    if(illuViewModel.isOn()){
                        String text = "Sensor: Light" + "," + "rate: " + rate + "\n" + "Time" + "," + "Illuminance" + "," + "Date" + "\n";
                        for(int i = 0; i<illuViewModel.getAllIlluminance().size(); i++){
                            String illuminance = String.valueOf(illuViewModel.getAllIlluminance().get(i).getIlluminance());
                            String date = illuViewModel.getAllIlluminance().get(i).getDate();
                            String time = illuViewModel.getAllIlluminance().get(i).getTime();
                            text = text + time + "," + illuminance + "," + date + "\n";
                        }
                        String fileName = "illuminanceLog.csv";
                        writeFile(text, fileName);
                    }
                    if(pressViewModel.isOn()){
                        String text = "Sensor: Pressure" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Pressure" + "," + "Date" + "\n";
                        for(int i = 0; i<pressViewModel.getAllPressure().size(); i++){
                            String pressure = String.valueOf(pressViewModel.getAllPressure().get(i).getPressure());
                            String date = pressViewModel.getAllPressure().get(i).getDate();
                            String time = pressViewModel.getAllPressure().get(i).getTime();
                            text = text + time + "," + pressure + "," + date + "\n";
                        }
                        String fileName = "pressureLog.csv";
                        writeFile(text, fileName);
                    }
                    if(humViewModel.isOn()){
                        String text = "Sensor: Relative Humidity" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Relative Humidity" + "," + "Date" + "\n";
                        for(int i =0; i<humViewModel.getAllHumidity().size(); i++){
                            String relHumidity = String.valueOf(humViewModel.getAllHumidity().get(i).getHumidity());
                            String date = humViewModel.getAllHumidity().get(i).getDate();
                            String time = humViewModel.getAllHumidity().get(i).getTime();
                            text = text + time + "," + relHumidity + "," + date + "\n";
                        }
                        String fileName = "humidityLog.csv";
                        writeFile(text, fileName);
                    }
                    if(accViewModel.isOn()){
                        String text = "Sensor: Accelerometer" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Ax" + "," + "Ay" + "," + "Az" + "," + "Date" + "\n";
                        for (int i = 0; i < accViewModel.getAccelerometer().size(); i++) {
                            String ax = String.valueOf(accViewModel.getAccelerometer().get(i).getAx());
                            String ay = String.valueOf(accViewModel.getAccelerometer().get(i).getAy());
                            String az = String.valueOf(accViewModel.getAccelerometer().get(i).getAz());
                            String date = accViewModel.getAccelerometer().get(i).getDate();
                            String time = accViewModel.getAccelerometer().get(i).getTime();
                            text = text + time + "," + ax + "," + ay + "," + az + "," + date + "\n";
                        }
                        String fileName = "accLog.csv";
                        writeFile(text, fileName);
                    }
                    if(gravViewModel.isOn()){
                        String text = "Sensor: Gravity" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Gx" + "," + "Gy" + "," + "Gz" + "," + "Date" + "\n";
                        for (int i = 0; i < gravViewModel.getGravity().size(); i++) {
                            String gx = String.valueOf(gravViewModel.getGravity().get(i).getGx());
                            String gy = String.valueOf(gravViewModel.getGravity().get(i).getGy());
                            String gz = String.valueOf(gravViewModel.getGravity().get(i).getGz());
                            String date = gravViewModel.getGravity().get(i).getDate();
                            String time = gravViewModel.getGravity().get(i).getTime();
                            text = text + time + "," + gx + "," + gy + "," + gz + "," + date + "\n";
                        }
                        String fileName = "gravLog.csv";
                        writeFile(text, fileName);
                    }
                    if(gyroViewModel.isOn()){
                        String text = "Sensor: Gyroscope" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Wx" + "," + "Wy" + "," + "Wz" + "," + "Date" + "\n";
                        for (int i = 0; i < gyroViewModel.getGyroscope().size(); i++) {
                            String wx = String.valueOf(gyroViewModel.getGyroscope().get(i).getWx());
                            String wy = String.valueOf(gyroViewModel.getGyroscope().get(i).getWy());
                            String wz = String.valueOf(gyroViewModel.getGyroscope().get(i).getWz());
                            String date = gyroViewModel.getGyroscope().get(i).getDate();
                            String time = gyroViewModel.getGyroscope().get(i).getTime();
                            text = text + time + "," + wx + "," + wy + "," + wz + "," + date + "\n";
                        }
                        String fileName = "gyroLog.csv";
                        writeFile(text, fileName);
                    }
                    if(motViewModel.isOn()){
                        String text = "Sensor: Motion Detection" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Motion" + "," + "Date" + "\n";
                        for (int i = 0; i < motViewModel.getMotion().size(); i++) {
                            String mot = String.valueOf(motViewModel.getMotion().get(i).getMotion());
                            String date = motViewModel.getMotion().get(i).getDate();
                            String time = motViewModel.getMotion().get(i).getTime();
                            text = text + time + "," + mot + "," + date + "\n";
                        }
                        String fileName = "motionLog.csv";
                        writeFile(text, fileName);
                    }
                    if(rotViewModel.isOn()){
                        String text = "Sensor: Rotation Vector" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Xsin" + "," + "Ysin" + "," + "Zsin" + "," + "Cos" + "," + "Estimated Heading Accuracy" + "," + "Date" + "\n";
                        for (int i = 0; i < rotViewModel.getRotation().size(); i++) {
                            String xsin = String.valueOf(rotViewModel.getRotation().get(i).getXsin());
                            String ysin = String.valueOf(rotViewModel.getRotation().get(i).getYsin());
                            String zsin = String.valueOf(rotViewModel.getRotation().get(i).getZsin());
                            String cos = String.valueOf(rotViewModel.getRotation().get(i).getCos());
                            String sha = String.valueOf(rotViewModel.getRotation().get(i).getSha());
                            String date = rotViewModel.getRotation().get(i).getDate();
                            String time = rotViewModel.getRotation().get(i).getTime();
                            text = text + time + "," + xsin + "," + ysin + "," + zsin + "," + cos + "," + sha + "," + date + "\n";
                        }
                        String fileName = "rotLog.csv";
                        writeFile(text, fileName);
                    }
                    if(stViewModel.isOn()){
                        String text = "Sensor: Step Counter" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "StepCounter" + "," + "Date" + "\n";
                        for (int i = 0; i < stViewModel.getStep().size(); i++) {
                            String step = String.valueOf(stViewModel.getStep().get(i).getStep());
                            String date = stViewModel.getStep().get(i).getDate();
                            String time = stViewModel.getStep().get(i).getTime();
                            text = text + time + "," + step + "," + date + "\n";
                        }
                        String fileName = "stepLog.csv";
                        writeFile(text, fileName);
                    }
                    if(gViewModel.isOn()){
                        String text = "Sensor: Game Rotation Vector" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "gameX" + "," + "gameY" + "," + "gameZ" + "," + "Date" + "\n";
                        for(int i = 0; i<gViewModel.getGame().size(); i++){
                            String date = gViewModel.getGame().get(i).getDate();
                            String time = gViewModel.getGame().get(i).getTime();
                            float gameX = gViewModel.getGame().get(i).getGameX();
                            float gameY = gViewModel.getGame().get(i).getGameY();
                            float gameZ = gViewModel.getGame().get(i).getGameZ();
                            text = text + time + "," + gameX + "," + gameY + "," + gameZ + "," + date + "\n";
                        }
                        String fileName = "gameLog.csv";
                        writeFile(text, fileName);
                    }
                    if(magViewModel.isOn()){
                        String text = "Sensor: Geomagnetic Field" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Xvalue" + "," + "Yvalue" + "," + "Zvalue" + "," + "Date" + "\n";
                        for(int i = 0; i<magViewModel.getAllMagnetic().size(); i++){
                            String date = magViewModel.getAllMagnetic().get(i).getDate();
                            String time = magViewModel.getAllMagnetic().get(i).getTime();
                            float xValue = magViewModel.getAllMagnetic().get(i).getValueX();
                            float yValue = magViewModel.getAllMagnetic().get(i).getValueY();
                            float zValue = magViewModel.getAllMagnetic().get(i).getValueZ();
                            text = text + time + "," + xValue + "," + yValue + "," + zValue + "," + date + "\n";
                        }
                        String fileName = "magneticLog.csv";
                        writeFile(text, fileName);
                    }
                    if(proxViewModel.isOn()){
                        String text = "Sensor: Proximity" + "," + "rate: " + (float)rate/1000000 + "\n" + "Time" + "," + "Distance" + "," + "Date" + "\n";
                        for(int i = 0; i<proxViewModel.getAllProximity().size(); i++){
                            String date = proxViewModel.getAllProximity().get(i).getDate();
                            String time = proxViewModel.getAllProximity().get(i).getTime();
                            float distance = proxViewModel.getAllProximity().get(i).getDistance();
                            text = text + time + "," + distance + ","  + date + "\n";
                        }
                        String fileName = "proximityLog.csv";
                        writeFile(text, fileName);
                    }
                    if(gpsViewModel.isOn()){
                        String text = "Sensor: GPS" + "," + "rate: " + (float)rate2/1000 + "\n" + "Time" + "," + "Latitude" + "," + "Longitude" + "," + "Date" + "\n";
                        for (int i = 0; i < gpsViewModel.getGps().size(); i++) {
                            double longitude = gpsViewModel.getGps().get(i).getLongitude();
                            double latitude = gpsViewModel.getGps().get(i).getLatitude();
                            String date = gpsViewModel.getGps().get(i).getDate();
                            String time = gpsViewModel.getGps().get(i).getTime();
                            text = text + time + "," + latitude + "," + longitude + "," + "," + date + "\n";
                        }
                        String fileName = "gpsLog.csv";
                        writeFile(text, fileName);
                    }
                    sensorBinding.shareButton.setBackgroundColor(Color.GREEN);
                    sensorBinding.shareButton.setClickable(true);
                }

            }
        });
    }

    public void getDateTime(){
        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        time = timeFormat.format(calendar.getTime());
    }

    //Get the sensors values each time that occurs a change.
    @Override
    public void onSensorChanged(SensorEvent event) {
        getDateTime();
        Log.d("Here: ", "something chenged");
        if(event.sensor.equals(accelerometer)){
            accelerometerReading = event.values;
            accValue.add((float) Math.sqrt(accelerometerReading[0]*accelerometerReading[0] + accelerometerReading[1]*accelerometerReading[1] + accelerometerReading[2]*accelerometerReading[2]));
            accelerometer accelerometerValue = new accelerometer(accelerometerReading[0], accelerometerReading[1], accelerometerReading[2], date, time);
            accViewModel.insert(accelerometerValue);
            if(ssViewModel.isCompassBool()){
                getCompassValues();
            }
        }else{
            if(event.sensor.equals(gyroscope)){
                gyroscopeReading = event.values;
                gyroValue.add((float) Math.sqrt(gyroscopeReading[0]*gyroscopeReading[0] + gyroscopeReading[1]*gyroscopeReading[1] + gyroscopeReading[2]*gyroscopeReading[2]));
                gyroscope gyroscopeValue = new gyroscope(gyroscopeReading[0], gyroscopeReading[1], gyroscopeReading[2], date, time);
                gyroViewModel.insert(gyroscopeValue);
            }else{
                if(event.sensor.equals(gravity)){
                    gravityReading = event.values;
                    gravity gravityValue = new gravity(gravityReading[0], gravityReading[1], gravityReading[2], date, time);
                    gravViewModel.insert(gravityValue);
                }else{
                    if(event.sensor.equals(rotation)){
                        rotationVectorReading = event.values;
                        rotation rotationValue = new rotation(rotationVectorReading[0], rotationVectorReading[1], rotationVectorReading[2], rotationVectorReading[3], rotationVectorReading[4], date, time);
                        rotViewModel.insert(rotationValue);
                    }else{
                        if(event.sensor.equals(stepCounter)){
                            stepCounterReading = event.values;
                            stepCounter stepCounterValue = new stepCounter(stepCounterReading[0], date, time);
                            stViewModel.insert(stepCounterValue);
                        }else{
                            if(event.sensor.equals(motion)){
                                motionReading = event.values;
                                motion motionValue = new motion(motionReading[0], date ,time);
                                motViewModel.insert(motionValue);
                            }else{
                                if(event.sensor.equals(ambientTemperature)){
                                    temperatureReading = event.values;
                                    ambientTemperature temperatureValue = new ambientTemperature(temperatureReading[0], date, time);
                                    ambViewModel.insert(temperatureValue);
                                }else{
                                    if(event.sensor.equals(illuminance)){
                                        illuminanceReading = event.values;
                                        illuValue.add(illuminanceReading[0]);
                                        illuminance illuminanceValue = new illuminance(illuminanceReading[0], date, time);
                                        illuViewModel.insert(illuminanceValue);
                                    }else{
                                        if(event.sensor.equals(pressure)){
                                            pressureReading = event.values;
                                            pressure pressureValue = new pressure(pressureReading[0], date, time);
                                            pressViewModel.insert(pressureValue);
                                        }else{
                                            if(event.sensor.equals(humidity)){
                                                humidityReading = event.values;
                                                humidity humidityValue = new humidity(humidityReading[0], date, time);
                                                humViewModel.insert(humidityValue);
                                            }else{
                                                if(event.sensor.equals(prox)){
                                                    proximityReading = event.values;
                                                    proximity proximityValue = new proximity(proximityReading[0], date, time);
                                                    proxViewModel.insert(proximityValue);
                                                }else{
                                                    if(event.sensor.equals(game)){
                                                        gameReading = event.values;
                                                        gameRotation gameValue = new gameRotation(gameReading[0], gameReading[1], gameReading[2], date, time);
                                                        gViewModel.insert(gameValue);
                                                    }else{
                                                        if(event.sensor.equals(magnetic)){
                                                            magnetometerReading = event.values;
                                                            magneticField magneticValue = new magneticField(magnetometerReading[0], magnetometerReading[1], magnetometerReading[2], date, time);
                                                            magViewModel.insert(magneticValue);
                                                            if(ssViewModel.isCompassBool()){
                                                                getCompassValues();
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Function that compute the compass method data
    void getCompassValues(){
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        //Log.d("azimute: ", String.valueOf(orientationAngles[0]));
        azimuthValue.add((float) ( -orientationAngles[0]*180/3.1415));
        pitchValue.add(orientationAngles[1]);
        rollValue.add(orientationAngles[2]);

        //compassBinding.pointer.setRotation((float) (-orientationAngles[0]*180/3.1415));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    //Everything below this point, except the moving average function, is to get the gps information.
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
            getDateTime();
            gpsLocation gps = new gpsLocation(id, lastLocation.getLatitude(), lastLocation.getLongitude(), lastLocation.getSpeed(), lastLocation.getAltitude(), date, time);
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

    List<Float> movingAverage(List<Float> lista, int window){
        float sum = 0;
        float average=0;
        List<Float> result = new ArrayList<>();
        for(int i = 0; i < lista.size() - window + 1; i++ ){
            for(int j = 0; j < window; j++){
                sum = sum + lista.get(i + j);
            }
            average = sum/window;
            sum = 0;
            result.add(average);
        }
        return result;
    }

}