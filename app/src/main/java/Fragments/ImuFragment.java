package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.SensorsActivity;
import com.example.myapplication.databinding.FragmentImuBinding;

import ViewModel.Motion.gravityViewModel;


public class ImuFragment extends Fragment {
    private FragmentImuBinding imuBinding;
    private gravityViewModel imuViewModel;
    public ImuFragment() {
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
        imuBinding = FragmentImuBinding.inflate(inflater, container, false);
        return imuBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        imuViewModel = new ViewModelProvider(requireActivity()).get(gravityViewModel.class);
        gravitySensor();
        gyroscopeSensor();
        motionSensor();
        rotationSensor();
        stepSensor();
        accelerometerSensor();
    }

    public void gravitySensor(){
        imuBinding.gravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imuViewModel.setSensor("gravity");
                Intent sensorIntent = new Intent(requireContext(), SensorsActivity.class);
                sensorIntent.putExtra("type", 1);
                startActivity(sensorIntent);
                //Navigation.findNavController(requireView()).navigate(R.id.gravityFragment);
            }
        });
    }

    public void gyroscopeSensor(){
        imuBinding.gyroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imuViewModel.setSensor("gyroscope");
                Intent sensorIntent = new Intent(requireContext(), SensorsActivity.class);
                sensorIntent.putExtra("type", 3);
                startActivity(sensorIntent);
                //Navigation.findNavController(requireView()).navigate(R.id.gyroscopeFragment);
            }
        });
    }

    public void motionSensor(){
        imuBinding.motionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imuViewModel.setSensor("motion");
                Intent sensorIntent = new Intent(requireContext(), SensorsActivity.class);
                sensorIntent.putExtra("type", 2);
                startActivity(sensorIntent);
                //Navigation.findNavController(requireView()).navigate(R.id.motionFragment);
            }
        });
    }

    public void rotationSensor(){
        imuBinding.rotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imuViewModel.setSensor("rotation");
                Intent sensorIntent = new Intent(requireContext(), SensorsActivity.class);
                sensorIntent.putExtra("type", 4);
                startActivity(sensorIntent);
                //Navigation.findNavController(requireView()).navigate(R.id.rotationFragment);
            }
        });
    }

    public void stepSensor(){
        imuBinding.stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imuViewModel.setSensor("step");
                Intent sensorIntent = new Intent(requireContext(), SensorsActivity.class);
                sensorIntent.putExtra("type", 5);
                startActivity(sensorIntent);
                //Navigation.findNavController(requireView()).navigate(R.id.stepFragment);
            }
        });
    }

    public void accelerometerSensor(){
        imuBinding.AccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imuViewModel.setSensor("accelerometer");
                Intent sensorIntent = new Intent(requireContext(), SensorsActivity.class);
                sensorIntent.putExtra("type", 6);
                startActivity(sensorIntent);
                //Navigation.findNavController(requireView()).navigate(R.id.accelerometerFragment);
            }
        });
    }
}