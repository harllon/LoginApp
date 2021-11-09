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

import com.example.myapplication.databinding.FragmentEnvironmentBinding;

import ViewModel.Environment.ambtempViewModel;
import ViewModel.Environment.humidityViewModel;
import ViewModel.Environment.lightViewModel;
import ViewModel.Environment.pressureViewModel;

public class EnvironmentFragment extends Fragment {

    private FragmentEnvironmentBinding envBinding;

    private ambtempViewModel ambViewModel;
    private humidityViewModel humViewModel;
    private lightViewModel illuViewModel;
    private pressureViewModel pressViewModel;

    private SensorManager ambTempManager;
    private SensorManager lightManager;
    private SensorManager pressureManager;
    private SensorManager humidityManager;

    public EnvironmentFragment() {
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
        envBinding = FragmentEnvironmentBinding.inflate(inflater, container, false);
        return envBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);



        startViewModel();
        verifyBox();
        verifySensors();
        //observeViewModels();

        illuViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.lightBox.setChecked(false);
                }
            }
        });
        ambViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.tempBox.setChecked(false);
                }
            }
        });
        pressViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.pressureBox.setChecked(false);
                }
            }
        });
        humViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.humidityBox.setChecked(false);
                }
            }
        });
    }

    //Verify if each box of environment sensor is on or off. If the box needs to turn off in a way different of the user action, this function will notice and update the box.
    public void observeViewModels(){
        illuViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.lightBox.setChecked(false);
                }
            }
        });
        ambViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.tempBox.setChecked(false);
                }
            }
        });
        pressViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.pressureBox.setChecked(false);
                }
            }
        });
        humViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    envBinding.humidityBox.setChecked(false);
                }
            }
        });
    }

    public void startViewModel(){
        ambViewModel = new ViewModelProvider(requireActivity()).get(ambtempViewModel.class);
        pressViewModel = new ViewModelProvider(requireActivity()).get(pressureViewModel.class);
        humViewModel = new ViewModelProvider(requireActivity()).get(humidityViewModel.class);
        illuViewModel = new ViewModelProvider(requireActivity()).get(lightViewModel.class);
    }

    //Set the information if the user turned on or turned off the box
    public void verifyBox(){
        envBinding.tempBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(envBinding.tempBox.isChecked()){
                    ambViewModel.setOn(true);
                    ambViewModel.setIsCheck(true);
                }else{
                    ambViewModel.setOn(false);
                    ambViewModel.setIsCheck(false);
                }
            }
        });

        envBinding.humidityBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(envBinding.humidityBox.isChecked()){
                    humViewModel.setOn(true);
                    humViewModel.setIsCheck(true);
                }else{
                    humViewModel.setOn(false);
                    humViewModel.setIsCheck(false);
                }
            }
        });

        envBinding.pressureBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(envBinding.pressureBox.isChecked()){
                    pressViewModel.setOn(true);
                    pressViewModel.setIsCheck(true);
                }else{
                    pressViewModel.setOn(false);
                    pressViewModel.setIsCheck(false);
                }
            }
        });

        envBinding.lightBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(envBinding.lightBox.isChecked()){
                    illuViewModel.setOn(true);
                    illuViewModel.setIsCheck(true);
                }else{
                    illuViewModel.setOn(false);
                    illuViewModel.setIsCheck(false);
                }
            }
        });
    }

    //Verify if the sensors are available on the device and make the initial setup of color and check
    public void verifySensors(){
        ambTempManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        lightManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        pressureManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        humidityManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        if(ambTempManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null){
            envBinding.tempBox.setClickable(false);
            envBinding.tempBox.setTextColor(Color.GRAY);
        }else{
            envBinding.tempBox.setTextColor(Color.GREEN);
            envBinding.tempBox.setChecked(true);
            ambViewModel.setIsCheck(true);
            ambViewModel.setOn(true);
        }

        if(lightManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null){
            envBinding.lightBox.setTextColor(Color.GRAY);
            envBinding.lightBox.setClickable(false);
        }else{
            envBinding.lightBox.setTextColor(Color.GREEN);
            envBinding.lightBox.setChecked(true);
            illuViewModel.setIsCheck(true);
            illuViewModel.setOn(true);
        }

        if(pressureManager.getDefaultSensor(Sensor.TYPE_PRESSURE) == null){
            envBinding.pressureBox.setTextColor(Color.GRAY);
            envBinding.pressureBox.setClickable(false);
        }else{
            envBinding.pressureBox.setTextColor(Color.GREEN);
            envBinding.pressureBox.setChecked(true);
            pressViewModel.setIsCheck(true);
            pressViewModel.setOn(true);
        }

        if(humidityManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) == null){
            envBinding.humidityBox.setTextColor(Color.GRAY);
            envBinding.humidityBox.setClickable(false);
        }else{
            envBinding.humidityBox.setTextColor(Color.GREEN);
            envBinding.humidityBox.setChecked(true);
            humViewModel.setIsCheck(true);
            humViewModel.setOn(true);
        }
    }
}