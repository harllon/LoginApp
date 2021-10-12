package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivitySensorsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Adapters.ViewPager.accelerometerAdapter;
import Adapters.ViewPager.gravityAdapter;
import Adapters.ViewPager.gyroscopeAdapter;
import Adapters.ViewPager.motionAdapter;
import Adapters.ViewPager.rotationAdapter;
import Adapters.ViewPager.stepAdapter;
import ViewModel.Motion.gravityViewModel;

public class SensorsActivity extends AppCompatActivity {
    private ActivitySensorsBinding sensorsBinding;
    private gravityViewModel imuViewModel;
    FragmentStateAdapter sensorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorsBinding = ActivitySensorsBinding.inflate(getLayoutInflater());
        setContentView(sensorsBinding.getRoot());
        //imuViewModel = new ViewModelProvider(this).get(sensorViewModel.class);
        int type = getIntent().getIntExtra("type", 0);
        //Log.d("tipo: ", type);
        if(type == 1){
            sensorAdapter = new gravityAdapter(this);
            sensorsBinding.SliderSensor.setAdapter(sensorAdapter);
            TabLayoutMediator tabMediatorGravity = new TabLayoutMediator(sensorsBinding.guia, sensorsBinding.SliderSensor, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    if(position == 0){
                        tab.setText("Gravity");
                    }else{
                        tab.setText("Saved Gravity");
                    }
                }
            });
            tabMediatorGravity.attach();
        }else{
            if(type == 2){
                sensorAdapter = new motionAdapter(this);
                sensorsBinding.SliderSensor.setAdapter(sensorAdapter);
                TabLayoutMediator tabMediatorMotion = new TabLayoutMediator(sensorsBinding.guia, sensorsBinding.SliderSensor, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if(position == 0){
                            tab.setText("Motion");
                        }else{
                            tab.setText("Saved Motion");
                        }
                    }
                });
                tabMediatorMotion.attach();
            }else{
                if(type == 3){
                    sensorAdapter = new gyroscopeAdapter(this);
                    sensorsBinding.SliderSensor.setAdapter(sensorAdapter);
                    TabLayoutMediator tabMediatorGyroscope = new TabLayoutMediator(sensorsBinding.guia, sensorsBinding.SliderSensor, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            if(position == 0){
                                tab.setText("Gyroscope");
                            }else{
                                tab.setText("Saved Gyroscope");
                            }
                        }
                    });
                    tabMediatorGyroscope.attach();
                }else{
                    if(type == 4){
                        sensorAdapter = new rotationAdapter(this);
                        sensorsBinding.SliderSensor.setAdapter(sensorAdapter);
                        TabLayoutMediator tabMediatorRotation = new TabLayoutMediator(sensorsBinding.guia, sensorsBinding.SliderSensor, new TabLayoutMediator.TabConfigurationStrategy() {
                            @Override
                            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                if(position == 0){
                                    tab.setText("Rotation");
                                }else{
                                    tab.setText("Saved Rotation");
                                }
                            }
                        });
                        tabMediatorRotation.attach();
                    }else{
                        if(type == 5){
                            sensorAdapter = new stepAdapter(this);
                            sensorsBinding.SliderSensor.setAdapter(sensorAdapter);
                            TabLayoutMediator tabMediatorStep = new TabLayoutMediator(sensorsBinding.guia, sensorsBinding.SliderSensor, new TabLayoutMediator.TabConfigurationStrategy() {
                                @Override
                                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                    if(position == 0){
                                        tab.setText("Step Counter");
                                    }else{
                                        tab.setText("Saved Steps");
                                    }
                                }
                            });
                            tabMediatorStep.attach();
                        }else{
                            sensorAdapter = new accelerometerAdapter(this);
                            sensorsBinding.SliderSensor.setAdapter(sensorAdapter);
                            TabLayoutMediator tabMediatorAccelerometer = new TabLayoutMediator(sensorsBinding.guia, sensorsBinding.SliderSensor, new TabLayoutMediator.TabConfigurationStrategy() {
                                @Override
                                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                    if(position == 0){
                                        tab.setText("Accelerometer");
                                    }else{
                                        tab.setText("Saved Accelerometer");
                                    }
                                }
                            });
                            tabMediatorAccelerometer.attach();
                        }
                    }
                }
            }
        }
    }
}