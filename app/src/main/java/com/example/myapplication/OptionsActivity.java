package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.databinding.ActivityOptionsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Adapters.ViewPager.SensorOptionAdapter;
import Adapters.ViewPager.gravityAdapter;
import ViewModel.Environment.ambtempViewModel;
import ViewModel.Environment.humidityViewModel;
import ViewModel.Environment.lightViewModel;
import ViewModel.Environment.pressureViewModel;
import ViewModel.Motion.accelerometerViewModel;
import ViewModel.Motion.gpsLocationViewModel;
import ViewModel.Motion.gyroscopeViewModel;
import ViewModel.Position.magneticViewModel;
import ViewModel.sensorViewModel;

public class OptionsActivity extends AppCompatActivity {
    private ActivityOptionsBinding optionsBinding;
    private FragmentStateAdapter adapter;

    private accelerometerViewModel accViewModel;
    private gyroscopeViewModel gyroViewModel;
    private gpsLocationViewModel gpsViewModel;
    private magneticViewModel magViewModel;
    private lightViewModel illuViewModel;
    private sensorViewModel ssViewModel;

    private Boolean magbool;
    private Boolean gyrobool;
    private Boolean accbool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        optionsBinding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(optionsBinding.getRoot());
        adapter = new SensorOptionAdapter(this);
        optionsBinding.optionViewPager.setAdapter(adapter);
        TabLayoutMediator tabMediatorOption = new TabLayoutMediator(optionsBinding.optionsGuia, optionsBinding.optionViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Motion Sensors");
                }else{
                    if(position == 1){
                        tab.setText("Position Sensors");
                    }else{
                        tab.setText("Environment Sensors");
                    }
                }
            }
        });
        tabMediatorOption.attach();
        startViewModel();
        observeFunction();
        setBool();

        ssViewModel.setAltitudebool(true);
        ssViewModel.setRatebool(true);
        ssViewModel.setSunlightbool(true);
        ssViewModel.setAmplitudebool(true);
        ssViewModel.setSpeedbool(true);
        ssViewModel.setNomotionbool(true);

        optionsBinding.altitudeBox.setChecked(true);
        optionsBinding.amplitudeBox.setChecked(true);
        optionsBinding.nomotionBox.setChecked(true);
        optionsBinding.speedBox.setChecked(true);
        optionsBinding.rateBox.setChecked(true);
        optionsBinding.sunlightBox.setChecked(true);

        optionsBinding.sunlightBox.setTextColor(Color.GREEN);
        optionsBinding.rateBox.setTextColor(Color.GREEN);
        optionsBinding.speedBox.setTextColor(Color.GREEN);
        optionsBinding.nomotionBox.setTextColor(Color.GREEN);
        optionsBinding.amplitudeBox.setTextColor(Color.GREEN);
        optionsBinding.altitudeBox.setTextColor(Color.GREEN);


    }
    public void observeFunction(){
        illuViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //Log.d("oxe", String.valueOf(aBoolean));
                if(aBoolean){
                    optionsBinding.sunlightBox.setClickable(true);
                    optionsBinding.sunlightBox.setTextColor(Color.GREEN);
                }else{
                    optionsBinding.sunlightBox.setClickable(false);
                    optionsBinding.sunlightBox.setChecked(false);
                    optionsBinding.sunlightBox.setTextColor(Color.GRAY);
                }
            }
        });

        gpsViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    optionsBinding.altitudeBox.setClickable(true);
                    optionsBinding.altitudeBox.setTextColor(Color.GREEN);
                    optionsBinding.speedBox.setTextColor(Color.GREEN);
                    optionsBinding.speedBox.setClickable(true);
                }else{
                    optionsBinding.altitudeBox.setClickable(false);
                    optionsBinding.altitudeBox.setChecked(false);
                    optionsBinding.altitudeBox.setTextColor(Color.GRAY);
                    optionsBinding.speedBox.setClickable(false);
                    optionsBinding.speedBox.setChecked(false);
                    optionsBinding.speedBox.setTextColor(Color.GRAY);
                }
            }
        });

        accViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                accbool = aBoolean;
                noMovementFunction();
                if(aBoolean){
                    optionsBinding.rateBox.setClickable(true);
                    optionsBinding.rateBox.setTextColor(Color.GREEN);
                    optionsBinding.amplitudeBox.setClickable(true);
                    optionsBinding.amplitudeBox.setTextColor(Color.GREEN);
                }else{
                    optionsBinding.rateBox.setClickable(false);
                    optionsBinding.rateBox.setChecked(false);
                    optionsBinding.rateBox.setTextColor(Color.GRAY);
                    optionsBinding.amplitudeBox.setClickable(false);
                    optionsBinding.amplitudeBox.setChecked(false);
                    optionsBinding.amplitudeBox.setTextColor(Color.GRAY);
                }
            }
        });

        magViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                magbool = aBoolean;
                noMovementFunction();
            }
        });

        gyroViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                gyrobool = aBoolean;
                noMovementFunction();
            }
        });
    }

    public void setBool(){
        optionsBinding.nomotionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.nomotionBox.isChecked()){
                    ssViewModel.setNomotionbool(true);
                }else{
                    ssViewModel.setNomotionbool(false);
                }
            }
        });
        optionsBinding.amplitudeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.amplitudeBox.isChecked()){
                    ssViewModel.setAmplitudebool(true);
                }else{
                    ssViewModel.setAmplitudebool(false);
                }
            }
        });
        optionsBinding.rateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.rateBox.isChecked()){
                    ssViewModel.setRatebool(true);
                }else{
                    ssViewModel.setRatebool(false);
                }
            }
        });
        optionsBinding.speedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.speedBox.isChecked()){
                    ssViewModel.setSpeedbool(true);
                }else{
                    ssViewModel.setSpeedbool(false);
                }
            }
        });
        optionsBinding.sunlightBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.sunlightBox.isChecked()){
                    ssViewModel.setSunlightbool(true);
                }else{
                    ssViewModel.setSunlightbool(false);
                }
            }
        });
        optionsBinding.altitudeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.altitudeBox.isChecked()){
                    ssViewModel.setAltitudebool(true);
                }else{
                    ssViewModel.setAltitudebool(false);
                }
            }
        });
    }
    public void noMovementFunction(){
        if(magbool && gyrobool && accbool){
            optionsBinding.nomotionBox.setClickable(true);
            optionsBinding.nomotionBox.setTextColor(Color.GREEN);
        }else{
            optionsBinding.nomotionBox.setChecked(false);
            optionsBinding.nomotionBox.setTextColor(Color.GRAY);
            optionsBinding.nomotionBox.setClickable(false);
        }
    }

    public void startViewModel(){
        magViewModel = new ViewModelProvider(this).get(magneticViewModel.class);
        accViewModel = new ViewModelProvider(this).get(accelerometerViewModel.class);
        gyroViewModel = new ViewModelProvider(this).get(gyroscopeViewModel.class);
        gpsViewModel = new ViewModelProvider(this).get(gpsLocationViewModel.class);
        illuViewModel = new ViewModelProvider(this).get(lightViewModel.class);
        ssViewModel = new ViewModelProvider(this).get(sensorViewModel.class);
    }

}