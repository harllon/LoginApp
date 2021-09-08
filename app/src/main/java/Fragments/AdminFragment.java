package Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.GpsActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.FragmentAdminBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import Utils.sensorName;
import ViewModel.gpsLocationViewModel;
import ViewModel.sensorViewModel;
import roomGPS.gpsLocation;

public class AdminFragment extends Fragment {
    private FragmentAdminBinding adminBinding;
    private SensorManager accManager;
    private SensorManager gyroManager;
    private SensorManager motionManager;
    private SensorManager rotationManager;
    private SensorManager stepcounterManager;
    private SensorManager gravManager;
    private sensorViewModel ssViewModel;
    private sensorName name;
    //private FusedLocationProviderClient fusedLocationClient;

    public AdminFragment() {
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
        adminBinding = FragmentAdminBinding.inflate(inflater, container, false);
        return adminBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        adminBinding.adminToolbar.inflateMenu(R.menu.navigation_menu);
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        adminBinding.adminToolbar.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_logout:
                    Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
                    return true;
                case R.id.nav_register:
                    Boolean admin = true;
                    Intent registerIntent = new Intent(requireContext(), SignupActivity.class);
                    registerIntent.putExtra("admin", admin);
                    startActivity(registerIntent);
                    return true;
                default:
                    return false;
            }
        });
        adminBinding.sensorButton.setBackgroundColor(Color.GRAY);
        adminBinding.sensorButton.setClickable(false);
        //returnMain();
        //registerAdmin();
        //Log.d("acc: ", String.valueOf(sensorName.ACCELEROMETER));
        sensorStart();
        buttonTrack();
        verifySensors();
        gpsStart();
        imuStart();

    }

    public void buttonTrack(){
        adminBinding.accBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        adminBinding.gravBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        adminBinding.gyroBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        adminBinding.gpsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        adminBinding.motionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        adminBinding.rotationBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        adminBinding.stepcounterBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
    }
    public void onCheckBoxClicked(){
        if(adminBinding.accBox.isChecked() || adminBinding.gpsBox.isChecked() || adminBinding.gyroBox.isChecked() || adminBinding.gravBox.isChecked() || adminBinding.motionBox.isChecked() || adminBinding.rotationBox.isChecked() || adminBinding.stepcounterBox.isChecked()){
            adminBinding.sensorButton.setBackgroundColor(Color.GREEN);
            adminBinding.sensorButton.setClickable(true);
        }else{
            adminBinding.sensorButton.setBackgroundColor(Color.GRAY);
            adminBinding.sensorButton.setClickable(false);
        }
    }
    public void sensorStart(){

        adminBinding.sensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adminBinding.accBox.isChecked()){
                    name = sensorName.ACCELEROMETER;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.stepcounterBox.isChecked()){
                    name = sensorName.STEP_COUNTER;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.rotationBox.isChecked()){
                    name = sensorName.ROTATION_VECTOR;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.motionBox.isChecked()){
                    name = sensorName.MOTION_DETECT;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.gravBox.isChecked()){
                    name = sensorName.GRAVITY;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.gyroBox.isChecked()){
                    name = sensorName.GYROSCOPE;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.gpsBox.isChecked()){
                    name = sensorName.GPS;
                    ssViewModel.setSensor(name);
                }
                if(adminBinding.sensorButton.isClickable()){
                    ssViewModel.setAdmin(true);
                    Navigation.findNavController(requireView()).navigate(R.id.sensorFragment);
                }

            }
        });
    }
    public void verifySensors(){
        accManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gravManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        motionManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        rotationManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepcounterManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(accManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null){
            adminBinding.accBox.setClickable(false);
            adminBinding.accBox.setTextColor(Color.GRAY);
        }else{
            adminBinding.accBox.setTextColor(Color.GREEN);
        }

        if(gyroManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
            adminBinding.gyroBox.setClickable(false);
            adminBinding.gyroBox.setTextColor(Color.GRAY);
        }else{
            adminBinding.gyroBox.setTextColor(Color.GREEN);
        }

        if(gravManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null){
            adminBinding.gravBox.setClickable(false);
            adminBinding.gravBox.setTextColor(Color.GRAY);
        }else{
            adminBinding.gravBox.setTextColor(Color.GREEN);
        }

        if(motionManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT) == null){
            adminBinding.motionBox.setClickable(false);
            adminBinding.motionBox.setTextColor(Color.GRAY);
        }else{
            adminBinding.motionBox.setTextColor(Color.GREEN);
        }

        if(rotationManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null){
            adminBinding.rotationBox.setClickable(false);
            adminBinding.rotationBox.setTextColor(Color.GRAY);
        }else{
            adminBinding.rotationBox.setTextColor(Color.GREEN);
        }

        if(stepcounterManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) == null){
            adminBinding.stepcounterBox.setClickable(false);
            adminBinding.stepcounterBox.setTextColor(Color.GRAY);
        }else{
            adminBinding.stepcounterBox.setTextColor(Color.GREEN);
        }
        adminBinding.gpsBox.setTextColor(Color.GREEN);
    }

    public void imuStart(){
        adminBinding.imuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.imuFragment);
            }
        });
    }

    public void gpsStart(){
        adminBinding.gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(requireView()).navigate(R.id.gpsFragment);
                Intent gpsIntent = new Intent(requireContext(), GpsActivity.class);
                startActivity(gpsIntent);
            }
        });
    }
    /*void returnMain(){
        adminBinding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent mainIntent = new Intent(requireContext(), MainActivity.class);
                //startActivity(mainIntent);
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
            }
        });
    }*/

    /*void registerAdmin(){
        adminBinding.adminregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean admin = true;
                Intent registerIntent = new Intent(requireContext(), SignupActivity.class);
                registerIntent.putExtra("admin", admin);
                startActivity(registerIntent);
            }
        });
    }*/

}