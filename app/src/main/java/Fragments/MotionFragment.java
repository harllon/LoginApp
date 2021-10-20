package Fragments;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentMotion2Binding;

import ViewModel.Motion.accelerometerViewModel;
import ViewModel.Motion.gpsLocationViewModel;
import ViewModel.Motion.gravityViewModel;
import ViewModel.Motion.gyroscopeViewModel;
import ViewModel.Motion.motionViewModel;
import ViewModel.Motion.rotationViewModel;
import ViewModel.Motion.stepViewModel;

public class MotionFragment extends Fragment {
    private FragmentMotion2Binding motionBinding;

    private accelerometerViewModel accViewModel;
    private gravityViewModel gravViewModel;
    private gyroscopeViewModel gyroViewModel;
    private motionViewModel motViewModel;
    private rotationViewModel rotViewModel;
    private stepViewModel stViewModel;
    private gpsLocationViewModel gpsViewModel;

    private SensorManager accManager;
    private SensorManager gyroManager;
    private SensorManager motionManager;
    private SensorManager rotationManager;
    private SensorManager stepcounterManager;
    private SensorManager gravManager;

    public MotionFragment() {
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
        motionBinding = FragmentMotion2Binding.inflate(inflater, container, false);
        return motionBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        startViewModel();
        verifySensors();
        verifyBox();
        accViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.accBox.setChecked(false);
                }
            }
        });
        gravViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.gravBox.setChecked(false);
                }
            }
        });
        gyroViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.gyroBox.setChecked(false);
                }
            }
        });
        motViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.motionBox.setChecked(false);
                }
            }
        });
        rotViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.rotationBox.setChecked(false);
                }
            }
        });
        stViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.stepcounterBox.setChecked(false);
                }
            }
        });
        gpsViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    motionBinding.gpsBox.setChecked(false);
                }
            }
        });
    }

    public void startViewModel(){
        accViewModel = new ViewModelProvider(requireActivity()).get(accelerometerViewModel.class);
        gravViewModel = new ViewModelProvider(requireActivity()).get(gravityViewModel.class);
        gyroViewModel = new ViewModelProvider(requireActivity()).get(gyroscopeViewModel.class);
        motViewModel = new ViewModelProvider(requireActivity()).get(motionViewModel.class);
        rotViewModel = new ViewModelProvider(requireActivity()).get(rotationViewModel.class);
        stViewModel = new ViewModelProvider(requireActivity()).get(stepViewModel.class);
        gpsViewModel = new ViewModelProvider(requireActivity()).get(gpsLocationViewModel.class);
    }

    public void verifyBox(){
        motionBinding.accBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.accBox.isChecked()){
                    accViewModel.setOn(true);
                    accViewModel.setIsCheck(true);
                }else{
                    accViewModel.setOn(false);
                    accViewModel.setIsCheck(false);
                }

            }
        });
        motionBinding.gravBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.gravBox.isChecked()){
                    gravViewModel.setOn(true);
                    gravViewModel.setIsCheck(true);
                }else{
                    gravViewModel.setOn(false);
                    gravViewModel.setIsCheck(false);
                }

            }
        });
        motionBinding.gyroBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.gyroBox.isChecked()){
                    gyroViewModel.setOn(true);
                    gyroViewModel.setIsCheck(true);
                }else{
                    gyroViewModel.setOn(false);
                    gyroViewModel.setIsCheck(false);
                }

            }
        });
        motionBinding.gpsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.gpsBox.isChecked()){
                    gpsViewModel.setOn(true);
                    gpsViewModel.setIsCheck(true);
                }else{
                    gpsViewModel.setOn(false);
                    gpsViewModel.setIsCheck(false);
                }

            }
        });
        motionBinding.motionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.motionBox.isChecked()){
                    motViewModel.setOn(true);
                    motViewModel.setIsCheck(true);
                }else{
                    motViewModel.setOn(false);
                    motViewModel.setIsCheck(false);
                }

            }
        });
        motionBinding.rotationBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.rotationBox.isChecked()){
                    rotViewModel.setOn(true);
                    rotViewModel.setIsCheck(true);
                }else{
                    rotViewModel.setOn(false);
                    rotViewModel.setIsCheck(false);
                }

            }
        });
        motionBinding.stepcounterBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motionBinding.stepcounterBox.isChecked()){
                    stViewModel.setOn(true);
                    stViewModel.setIsCheck(true);
                }else{
                    stViewModel.setOn(false);
                    stViewModel.setIsCheck(false);
                }
            }
        });
    }

    public void verifySensors(){
        accManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        motionManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        rotationManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepcounterManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        gravManager =  (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        if(accManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            motionBinding.accBox.setClickable(false);
            motionBinding.accBox.setTextColor(Color.GRAY);
        }else{
            motionBinding.accBox.setTextColor(Color.GREEN);
            motionBinding.accBox.setChecked(true);
            accViewModel.setIsCheck(true);
            accViewModel.setOn(true);
        }

        if(gyroManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
            motionBinding.gyroBox.setClickable(false);
            motionBinding.gyroBox.setTextColor(Color.GRAY);
        }else{
            motionBinding.gyroBox.setTextColor(Color.GREEN);
            motionBinding.gyroBox.setChecked(true);
            gyroViewModel.setIsCheck(true);
            gyroViewModel.setOn(true);
        }

        if(gravManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null){
            motionBinding.gravBox.setTextColor(Color.GRAY);
            motionBinding.gravBox.setClickable(false);
        }else{
            motionBinding.gravBox.setTextColor(Color.GREEN);
            motionBinding.gravBox.setChecked(true);
            gravViewModel.setIsCheck(true);
            gravViewModel.setOn(true);
        }

        if(motionManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT) == null){
            motionBinding.motionBox.setClickable(false);
            motionBinding.motionBox.setTextColor(Color.GRAY);
        }else{
            motionBinding.motionBox.setTextColor(Color.GREEN);
            motionBinding.motionBox.setChecked(true);
            motViewModel.setIsCheck(true);
            motViewModel.setOn(true);
        }

        if(rotationManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null){
            motionBinding.rotationBox.setClickable(false);
            motionBinding.rotationBox.setTextColor(Color.GRAY);
        }else{
            motionBinding.rotationBox.setTextColor(Color.GREEN);
            motionBinding.rotationBox.setChecked(true);
            rotViewModel.setIsCheck(true);
            rotViewModel.setOn(true);
        }

        if(stepcounterManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) == null){
            motionBinding.stepcounterBox.setClickable(false);
            motionBinding.stepcounterBox.setTextColor(Color.GRAY);
        }else{
            motionBinding.stepcounterBox.setTextColor(Color.GREEN);
            motionBinding.stepcounterBox.setChecked(true);
            stViewModel.setIsCheck(true);
            stViewModel.setOn(true);
        }
        motionBinding.gpsBox.setTextColor(Color.GREEN);
        motionBinding.gpsBox.setChecked(true);
        gpsViewModel.setIsCheck(true);
        gpsViewModel.setOn(true);
    }
}