package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityOptionsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Adapters.ViewPager.SensorOptionAdapter;
import ViewModel.Environment.lightViewModel;
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

    private Boolean magbool = true;
    private Boolean gyrobool = true;
    private Boolean accbool = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        optionsBinding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(optionsBinding.getRoot());

        //Setup of the ViewPager -> It's the responsible for the tab layout

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
        initialSetup();
        observeFunction();
        setBool();
    }

    //This function is responsible to initialize the screen with the all the methods "on"
    public void initialSetup(){
        ssViewModel.setAltitudeBool(true);
        ssViewModel.setCompassBool(true);
        ssViewModel.setSunLightBool(true);
        ssViewModel.setSpeedBool(true);
        ssViewModel.setNoMotionBool(true);

        optionsBinding.altitudeBox.setChecked(true);
        optionsBinding.compassBox.setChecked(true);
        optionsBinding.nomotionBox.setChecked(true);
        optionsBinding.speedBox.setChecked(true);
        optionsBinding.sunlightBox.setChecked(true);

        optionsBinding.sunlightBox.setTextColor(Color.GREEN);
        optionsBinding.compassBox.setTextColor(Color.GREEN);
        optionsBinding.speedBox.setTextColor(Color.GREEN);
        optionsBinding.nomotionBox.setTextColor(Color.GREEN);
        optionsBinding.altitudeBox.setTextColor(Color.GREEN);
    }

    //This function observes the sensors that are necessary for the methods. If the user turn off some sensors, the methods that need these sensors will turn off too.
    public void observeFunction(){
        illuViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    optionsBinding.sunlightBox.setClickable(true);
                    optionsBinding.sunlightBox.setTextColor(Color.GREEN);
                    ssViewModel.setSunLightBool(true);
                }else{
                    optionsBinding.sunlightBox.setClickable(false);
                    optionsBinding.sunlightBox.setChecked(false);
                    optionsBinding.sunlightBox.setTextColor(Color.GRAY);
                    ssViewModel.setSunLightBool(false);
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
                    ssViewModel.setSpeedBool(true);
                    ssViewModel.setAltitudeBool(true);
                }else{
                    optionsBinding.altitudeBox.setClickable(false);
                    optionsBinding.altitudeBox.setChecked(false);
                    optionsBinding.altitudeBox.setTextColor(Color.GRAY);
                    optionsBinding.speedBox.setClickable(false);
                    optionsBinding.speedBox.setChecked(false);
                    optionsBinding.speedBox.setTextColor(Color.GRAY);
                    ssViewModel.setSpeedBool(false);
                    ssViewModel.setAltitudeBool(false);
                }
            }
        });

        accViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                accbool = aBoolean;
                noMovementFunction();
                compassFunction();
            }
        });

        magViewModel.getIsCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                magbool = aBoolean;
                noMovementFunction();
                compassFunction();
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

    //This function is responsible to report if the method is on or off using the ssViewModel(This information will be available in others sections of the code)
    public void setBool(){
        optionsBinding.nomotionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.nomotionBox.isChecked()){
                    ssViewModel.setNoMotionBool(true);
                }else{
                    ssViewModel.setNoMotionBool(false);
                }
            }
        });
        optionsBinding.compassBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.compassBox.isChecked()){
                    ssViewModel.setCompassBool(true);
                }else{
                    ssViewModel.setCompassBool(false);
                }
            }
        });

        optionsBinding.speedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.speedBox.isChecked()){
                    ssViewModel.setSpeedBool(true);
                }else{
                    ssViewModel.setSpeedBool(false);
                }
            }
        });
        optionsBinding.sunlightBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.sunlightBox.isChecked()){
                    ssViewModel.setSunLightBool(true);
                }else{
                    ssViewModel.setSunLightBool(false);
                }
            }
        });
        optionsBinding.altitudeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsBinding.altitudeBox.isChecked()){
                    ssViewModel.setAltitudeBool(true);
                }else{
                    ssViewModel.setAltitudeBool(false);
                }
            }
        });
    }

    //The next 2 functions make the same thing of the 2 above, but, it do this for the compass method and noMovement Method because they need more than 1 sensor on.
    public void compassFunction(){
        if(magbool && accbool){
            optionsBinding.compassBox.setClickable(true);
            optionsBinding.compassBox.setTextColor(Color.GREEN);
            ssViewModel.setCompassBool(true);
        }else{
            optionsBinding.compassBox.setChecked(false);
            optionsBinding.compassBox.setTextColor(Color.GRAY);
            optionsBinding.compassBox.setClickable(false);
            ssViewModel.setCompassBool(false);
        }
    }

    public void noMovementFunction(){
        if(magbool && gyrobool && accbool){
            optionsBinding.nomotionBox.setClickable(true);
            optionsBinding.nomotionBox.setTextColor(Color.GREEN);
            ssViewModel.setNoMotionBool(true);
        }else{
            optionsBinding.nomotionBox.setChecked(false);
            optionsBinding.nomotionBox.setTextColor(Color.GRAY);
            optionsBinding.nomotionBox.setClickable(false);
            ssViewModel.setNoMotionBool(false);
        }
    }

    //This function initialize the viewModels.
    public void startViewModel(){
        magViewModel = new ViewModelProvider(this).get(magneticViewModel.class);
        accViewModel = new ViewModelProvider(this).get(accelerometerViewModel.class);
        gyroViewModel = new ViewModelProvider(this).get(gyroscopeViewModel.class);
        gpsViewModel = new ViewModelProvider(this).get(gpsLocationViewModel.class);
        illuViewModel = new ViewModelProvider(this).get(lightViewModel.class);
        ssViewModel = new ViewModelProvider(this).get(sensorViewModel.class);
    }

}