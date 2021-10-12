package Fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompassBinding;


public class CompassFragment extends Fragment implements SensorEventListener {
    private FragmentCompassBinding compassBinding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorManager accManager;
    private Sensor magnetic;
    private SensorManager magManager;
    float[] accelerometerReading = new float[3];
    float[] magnetometerReading = new float[3];
    final float[] rotationMatrix = new float[9];
    final float[] orientationAngles = new float[3];
    public CompassFragment() {
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
        compassBinding = FragmentCompassBinding.inflate(inflater, container, false);
        return compassBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        accManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        magManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = accManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = magManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        magManager.registerListener((SensorEventListener) this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.equals(magnetic)){
            magnetometerReading = event.values;
        }else{
            if(event.sensor.equals(accelerometer)){
                accelerometerReading = event.values;
            }
        }
        atualize();
    }

    void atualize(){
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        compassBinding.pointer.setRotation((float) (-orientationAngles[0]*180/3.1415));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause(){
        super.onPause();
        accManager.unregisterListener((SensorEventListener) this);
        magManager.unregisterListener((SensorEventListener) this);
    }
}