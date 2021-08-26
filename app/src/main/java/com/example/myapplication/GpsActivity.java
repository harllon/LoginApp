package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivityGpsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Adapters.DataSliderAdapter;

public class GpsActivity extends AppCompatActivity {
    private ActivityGpsBinding gpsBinding;
    private FragmentStateAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsBinding = ActivityGpsBinding.inflate(getLayoutInflater());
        setContentView(gpsBinding.getRoot());
        pagerAdapter = new DataSliderAdapter(this);
        gpsBinding.SliderGps.setAdapter(pagerAdapter);
        //val pageAdapter = DataSliderAdapter(this, 3)
        TabLayoutMediator tabMediator = new TabLayoutMediator(gpsBinding.guia, gpsBinding.SliderGps, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Gps");
                }else{
                    tab.setText("Saved Gps");
                }
            }
        });
        tabMediator.attach();

    }
}