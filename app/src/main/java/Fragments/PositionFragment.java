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

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPositionBinding;

import ViewModel.Position.gameViewModel;
import ViewModel.Position.magneticViewModel;
import ViewModel.Position.proximityViewModel;


public class PositionFragment extends Fragment {
    private FragmentPositionBinding positionBinding;

    private SensorManager gameManager;
    private SensorManager magneticManager;
    private SensorManager proximityManager;

    private proximityViewModel proxViewModel;
    private magneticViewModel magViewModel;
    private gameViewModel gViewModel;

    public PositionFragment() {
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
        positionBinding = FragmentPositionBinding.inflate(inflater, container, false);
        return positionBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        startViewModel();
        verifySensors();
        verifyBox();
        observeViewModels();
    }

    //Verify if each box of position sensor is on or off. If the box needs to turn off in a way different of the user action, this function will notice and update the box.
    public void observeViewModels(){
        proxViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    positionBinding.proximityBox.setChecked(false);
                }
            }
        });
        gViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    positionBinding.gameBox.setChecked(false);
                }
            }
        });
        magViewModel.getIsCheck().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    positionBinding.magneticBox.setChecked(false);
                }
            }
        });
    }

    public void startViewModel(){
        proxViewModel = new ViewModelProvider(requireActivity()).get(proximityViewModel.class);
        gViewModel = new ViewModelProvider(requireActivity()).get(gameViewModel.class);
        magViewModel = new ViewModelProvider(requireActivity()).get(magneticViewModel.class);
    }

    //Set the information if the user turned on or turned off the box
    public void verifyBox(){
        positionBinding.proximityBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionBinding.proximityBox.isChecked()){
                    proxViewModel.setOn(true);
                    proxViewModel.setIsCheck(true);
                }else{
                    proxViewModel.setOn(false);
                    proxViewModel.setIsCheck(false);
                }
            }
        });
        positionBinding.magneticBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionBinding.magneticBox.isChecked()){
                    magViewModel.setOn(true);
                    magViewModel.setIsCheck(true);
                }else{
                    magViewModel.setOn(false);
                    magViewModel.setIsCheck(false);
                }
            }
        });
        positionBinding.gameBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionBinding.gameBox.isChecked()){
                    gViewModel.setOn(true);
                    gViewModel.setIsCheck(true);
                }else{
                    gViewModel.setOn(false);
                    gViewModel.setIsCheck(false);
                }
            }
        });
    }

    //Verify if the sensors are available on the device and make the initial setup of color and check
    public void verifySensors(){
        gameManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        magneticManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        proximityManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        if(gameManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) == null){
            positionBinding.gameBox.setClickable(false);
            positionBinding.gameBox.setTextColor(Color.GRAY);
        }else{
            positionBinding.gameBox.setTextColor(Color.GREEN);
            positionBinding.gameBox.setChecked(true);
            gViewModel.setIsCheck(true);
            gViewModel.setOn(true);
        }

        if(magneticManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null){
            positionBinding.magneticBox.setTextColor(Color.GRAY);
            positionBinding.magneticBox.setClickable(false);
        }else{
            positionBinding.magneticBox.setTextColor(Color.GREEN);
            positionBinding.magneticBox.setChecked(true);
            magViewModel.setIsCheck(true);
            magViewModel.setOn(true);
        }

        if(proximityManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null){
            positionBinding.proximityBox.setTextColor(Color.GRAY);
            positionBinding.proximityBox.setClickable(false);
        }else{
            positionBinding.proximityBox.setTextColor(Color.GREEN);
            positionBinding.proximityBox.setChecked(true);
            proxViewModel.setIsCheck(true);
            proxViewModel.setOn(true);
        }
    }
}