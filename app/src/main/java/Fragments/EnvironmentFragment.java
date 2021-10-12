package Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.TrackActivity;
import com.example.myapplication.databinding.FragmentEnvironmentBinding;

import ViewModel.Environment.ambtempViewModel;
import ViewModel.Environment.humidityViewModel;
import ViewModel.Environment.lightViewModel;
import ViewModel.Environment.pressureViewModel;
import ViewModel.sensorViewModel;

public class EnvironmentFragment extends Fragment {

    private FragmentEnvironmentBinding envBinding;

    private ambtempViewModel ambViewModel;
    private humidityViewModel humViewModel;
    private lightViewModel illuViewModel;
    private pressureViewModel pressViewModel;
    private sensorViewModel ssViewModel;

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
        trackAvailability();
        //sensorStart();
        verifySensors();
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
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        ambViewModel = new ViewModelProvider(requireActivity()).get(ambtempViewModel.class);
        pressViewModel = new ViewModelProvider(requireActivity()).get(pressureViewModel.class);
        humViewModel = new ViewModelProvider(requireActivity()).get(humidityViewModel.class);
        illuViewModel = new ViewModelProvider(requireActivity()).get(lightViewModel.class);
    }
    public void trackAvailability(){
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
               // startIsAvailable();
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
                //startIsAvailable();
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
               // startIsAvailable();
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
               // startIsAvailable();
            }
        });
    }

   /* public void startIsAvailable(){
        if(envBinding.tempBox.isChecked() || envBinding.humidityBox.isChecked() || envBinding.lightBox.isChecked() || envBinding.pressureBox.isChecked()){
            envBinding.envTrackButton.setClickable(true);
            envBinding.envTrackButton.setBackgroundColor(Color.GREEN);
        }else{
            envBinding.envTrackButton.setClickable(false);
            envBinding.envTrackButton.setBackgroundColor(Color.GRAY);
        }
    } */

    /*public void sensorStart(){
        envBinding.envTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(envBinding.envTrackButton.isClickable()){
                    //Navigation.findNavController(requireView()).navigate(R.id.sensorFragment);
                    //Intent track = new Intent(requireContext(), TrackActivity.class);
                    //track.putExtra("ok", requireActivity());
                    //startActivity(track);
                    ssViewModel.setClicked(true);
                }
            }
        });
    }*/

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
        }

        if(lightManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null){
            envBinding.lightBox.setTextColor(Color.GRAY);
            envBinding.lightBox.setClickable(false);
        }else{
            envBinding.lightBox.setTextColor(Color.GREEN);
        }

        if(pressureManager.getDefaultSensor(Sensor.TYPE_PRESSURE) == null){
            envBinding.pressureBox.setTextColor(Color.GRAY);
            envBinding.pressureBox.setClickable(false);
        }else{
            envBinding.pressureBox.setTextColor(Color.GREEN);
        }

        if(humidityManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) == null){
            envBinding.humidityBox.setTextColor(Color.GRAY);
            envBinding.humidityBox.setClickable(false);
        }else{
            envBinding.humidityBox.setTextColor(Color.GREEN);
        }
    }
}