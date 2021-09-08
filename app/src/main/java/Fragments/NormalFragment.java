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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.GpsActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.FragmentNormalBinding;

import Utils.sensorName;
import ViewModel.sensorViewModel;

public class NormalFragment extends Fragment {
    private FragmentNormalBinding normalBinding;
    private SensorManager accManager;
    private SensorManager gyroManager;
    private SensorManager motionManager;
    private SensorManager rotationManager;
    private SensorManager stepcounterManager;
    private SensorManager gravManager;
    private sensorViewModel ssViewModel;
    private sensorName name;
    public NormalFragment() {
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
        normalBinding = FragmentNormalBinding.inflate(inflater, container, false);
        return normalBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        normalBinding.normalToolbar.inflateMenu(R.menu.normal_menu);
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        normalBinding.normalToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
                return true;
            }
            return false;
        });
        normalBinding.sensorButton.setBackgroundColor(Color.GRAY);
        normalBinding.sensorButton.setClickable(false);
        //returnSign();
        sensorStart();
        buttonTrack();
        verifySensors();
        startGps();
        startIMU();
    }

    public void buttonTrack() {
        normalBinding.accBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        normalBinding.gravBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        normalBinding.gyroBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        normalBinding.gpsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        normalBinding.motionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        normalBinding.rotationBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
        normalBinding.stepcounterBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxClicked();
            }
        });
    }
    public void onCheckBoxClicked(){
        if(normalBinding.accBox.isChecked() || normalBinding.gpsBox.isChecked() || normalBinding.gyroBox.isChecked() || normalBinding.gravBox.isChecked() || normalBinding.motionBox.isChecked() || normalBinding.rotationBox.isChecked() || normalBinding.stepcounterBox.isChecked()){
            normalBinding.sensorButton.setBackgroundColor(Color.GREEN);
            normalBinding.sensorButton.setClickable(true);
        }else{
            normalBinding.sensorButton.setBackgroundColor(Color.GRAY);
            normalBinding.sensorButton.setClickable(false);
        }
    }

    public void sensorStart(){

        normalBinding.sensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(normalBinding.accBox.isChecked()){
                    name = sensorName.ACCELEROMETER;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.stepcounterBox.isChecked()){
                    name = sensorName.STEP_COUNTER;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.rotationBox.isChecked()){
                    name = sensorName.ROTATION_VECTOR;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.motionBox.isChecked()){
                    name = sensorName.MOTION_DETECT;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.gravBox.isChecked()){
                    name = sensorName.GRAVITY;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.gyroBox.isChecked()){
                    name = sensorName.GYROSCOPE;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.gpsBox.isChecked()){
                    name = sensorName.GPS;
                    ssViewModel.setSensor(name);
                }
                if(normalBinding.sensorButton.isClickable()){
                    ssViewModel.setAdmin(false);
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
            normalBinding.accBox.setClickable(false);
            normalBinding.accBox.setTextColor(Color.GRAY);
        }else{
            normalBinding.accBox.setTextColor(Color.GREEN);
        }

        if(gyroManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
            normalBinding.gyroBox.setClickable(false);
            normalBinding.gyroBox.setTextColor(Color.GRAY);
        }else{
            normalBinding.gyroBox.setTextColor(Color.GREEN);
        }

        if(gravManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null){
            normalBinding.gravBox.setClickable(false);
            normalBinding.gravBox.setTextColor(Color.GRAY);
        }else{
            normalBinding.gravBox.setTextColor(Color.GREEN);
        }

        if(motionManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT) == null){
            normalBinding.motionBox.setClickable(false);
            normalBinding.motionBox.setTextColor(Color.GRAY);
        }else{
            normalBinding.motionBox.setTextColor(Color.GREEN);
        }

        if(rotationManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null){
            normalBinding.rotationBox.setClickable(false);
            normalBinding.rotationBox.setTextColor(Color.GRAY);
        }else{
            normalBinding.rotationBox.setTextColor(Color.GREEN);
        }

        if(stepcounterManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) == null){
            normalBinding.stepcounterBox.setClickable(false);
            normalBinding.stepcounterBox.setTextColor(Color.GRAY);
        }else{
            normalBinding.stepcounterBox.setTextColor(Color.GREEN);
        }
        normalBinding.gpsBox.setTextColor(Color.GREEN);
    }

    void startGps(){
        normalBinding.normalGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(requireView()).navigate(R.id.gpsFragment);
                Intent gpsIntent = new Intent(requireContext(), GpsActivity.class);
                startActivity(gpsIntent);
            }
        });
    }

    void startIMU(){
        normalBinding.normalIMUButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.imuFragment);
            }
        });
    }
    /*void returnSign(){
        normalBinding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent signinIntent = new Intent(NormalActivity.this, MainActivity2.class);
                //startActivity(signinIntent);
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
            }
        });
    }*/
}