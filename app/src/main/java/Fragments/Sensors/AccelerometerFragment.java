package Fragments.Sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentAccelerometerBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ViewModel.Motion.accelerometerViewModel;
import ViewModel.sensorViewModel;
import roomSensors.entities.accelerometer;


public class AccelerometerFragment extends Fragment implements SensorEventListener {
    private FragmentAccelerometerBinding accelerometerBinding;
    private accelerometerViewModel accViewModel;
    private Sensor sensor;
    private SensorManager sensorManager;
    private int rate;
    private List<accelerometer> allAcc;
    private sensorViewModel svViewModel;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Uri accLog;
    public AccelerometerFragment() {
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
        accelerometerBinding = FragmentAccelerometerBinding.inflate(inflater, container, false);
        return accelerometerBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        accViewModel = new ViewModelProvider(requireActivity()).get(accelerometerViewModel.class);
        svViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }else{
            Toast.makeText(requireContext(), "This cellphone doesn't have this hardware", Toast.LENGTH_LONG).show();
        }
        accViewModel.getAllAccelerometer().observe(getViewLifecycleOwner(), new Observer<List<accelerometer>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<accelerometer> accelerometers) {
                allAcc = Objects.requireNonNull(accelerometers);
                accViewModel.updateList(allAcc);
            }
        });
        accelerometerBinding.stopAccelerometerButton.setVisibility(View.INVISIBLE);
        accelerometerBinding.saveAccelerometerButton.setVisibility(View.INVISIBLE);
        accelerometerBinding.startAccelerometerButton.setVisibility(View.INVISIBLE);
        accelerometerBinding.shareButton.setVisibility(View.INVISIBLE);
        startTracking(this);
        stopTracking(this);
        save();
        updateRate();
        share();
    }

    public void startTracking(AccelerometerFragment t){
        accelerometerBinding.startAccelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometerBinding.saveAccelerometerButton.setVisibility(View.INVISIBLE);
                accelerometerBinding.shareButton.setVisibility(View.INVISIBLE);
                sensorManager.registerListener((SensorEventListener) t, sensor, rate);  //possivel causa de problemas
            }
        });
    }

    public void stopTracking(AccelerometerFragment t){
        accelerometerBinding.stopAccelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometerBinding.saveAccelerometerButton.setVisibility(View.VISIBLE);
                sensorManager.unregisterListener((SensorEventListener) t);
            }
        });
    }
    public void share(){
        accelerometerBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Accelerometer Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, accLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }
    public void save() {
        accelerometerBinding.saveAccelerometerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                accelerometerBinding.shareButton.setVisibility(View.VISIBLE);
                String texto = "";
                for (int i = 0; i < accViewModel.getAccelerometer().size(); i++) {
                    String ax = String.valueOf(accViewModel.getAccelerometer().get(i).getAx());
                    String ay = String.valueOf(accViewModel.getAccelerometer().get(i).getAy());
                    String az = String.valueOf(accViewModel.getAccelerometer().get(i).getAz());
                    String dataTime = accViewModel.getAccelerometer().get(i).getDate();
                    texto = texto + "Ax: " + ax + "; Ay: " + ay + "; Az: " + az + "; Data and Time: " + dataTime + "\n";
                }
                Log.d("Textao: ", texto);
                File file = new File(requireActivity().getExternalFilesDir(null), "accLog.txt");
                accLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(texto.getBytes(StandardCharsets.UTF_8));
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateRate(){
        accelerometerBinding.setAccelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometerBinding.startAccelerometerButton.setVisibility(View.VISIBLE);
                accelerometerBinding.stopAccelerometerButton.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(accelerometerBinding.updateAccelerometer.getText().toString())){
                    Toast.makeText(requireContext(), "Please, write a rate", Toast.LENGTH_LONG).show();
                }else{
                    rate = Integer.parseInt(accelerometerBinding.updateAccelerometer.getText().toString());
                    Log.d("valor da rate: ", String.valueOf(rate));
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime());
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        accelerometer acc = new accelerometer(ax, ay, az, dateTime, dateTime);
        accViewModel.insert(acc);
        //Log.d("X: ", String.valueOf(x));
        //Log.d("Y: ", String.valueOf(y));
        //Log.d("Z: ", String.valueOf(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}