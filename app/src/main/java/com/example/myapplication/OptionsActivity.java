package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityOptionsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Adapters.ViewPager.SensorOptionAdapter;
import Adapters.ViewPager.gravityAdapter;

public class OptionsActivity extends AppCompatActivity {
    private ActivityOptionsBinding optionsBinding;
    private FragmentStateAdapter adapter;
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
    }
}