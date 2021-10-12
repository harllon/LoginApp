package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import ViewModel.Environment.lightViewModel;

public class TrackActivity extends AppCompatActivity {
    private lightViewModel illuViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        //illuViewModel = new ViewModelProvider(Activity).get(lightViewModel.class);
        Log.d("verificando: ", String.valueOf(illuViewModel.isOn()));
    }
}