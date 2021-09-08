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

import com.example.myapplication.databinding.FragmentStepBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ViewModel.gravityViewModel;
import ViewModel.stepViewModel;
import roomSensors.entities.gravity;
import roomSensors.entities.stepCounter;

public class StepFragment extends Fragment implements SensorEventListener {

    private FragmentStepBinding stepBinding;
    private stepViewModel stViewModel;
    private Sensor sensor;
    private SensorManager sensorManager;
    private int rate;
    private List<stepCounter> allStep;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Uri stepLog;

    public StepFragment() {
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
        stepBinding = FragmentStepBinding.inflate(inflater, container, false);
        return stepBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        stViewModel = new ViewModelProvider(requireActivity()).get(stepViewModel.class);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }else{
            Toast.makeText(requireContext(), "This cellphone doesn't have this hardware", Toast.LENGTH_LONG).show();
        }
        stViewModel.getAllStep().observe(getViewLifecycleOwner(), new Observer<List<stepCounter>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<stepCounter> stepCounters) {
                allStep = Objects.requireNonNull(stepCounters);
                stViewModel.updateList(allStep);
            }
        });
        stepBinding.stopStepButton.setVisibility(View.INVISIBLE);
        stepBinding.saveStepButton.setVisibility(View.INVISIBLE);
        stepBinding.startStepButton.setVisibility(View.INVISIBLE);
        stepBinding.shareButton.setVisibility(View.INVISIBLE);
        startTracking(this);
        stopTracking(this);
        save();
        updateRate();
        share();
    }


    public void startTracking(StepFragment t){
        stepBinding.startStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepBinding.saveStepButton.setVisibility(View.INVISIBLE);
                stepBinding.shareButton.setVisibility(View.INVISIBLE);
                sensorManager.registerListener((SensorEventListener) t, sensor, rate);  //possivel causa de problemas
            }
        });
    }

    public void stopTracking(StepFragment t){
        stepBinding.stopStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepBinding.saveStepButton.setVisibility(View.VISIBLE);
                sensorManager.unregisterListener((SensorEventListener) t);
            }
        });
    }

    public void share(){
        stepBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "StepCounter Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, stepLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }

    public void save(){
        stepBinding.saveStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepBinding.shareButton.setVisibility(View.VISIBLE);
                String texto = "";
                for (int i = 0; i < stViewModel.getStep().size(); i++) {
                    String step = String.valueOf(stViewModel.getStep().get(i).getStep());
                    String dataTime = stViewModel.getStep().get(i).getDateTime();
                    texto = texto + "Step: " + step + "; Data and Time: " + dataTime + "\n";
                }
                Log.d("Textao: ", texto);
                File file = new File(requireActivity().getExternalFilesDir(null), "stepLog.txt");
                stepLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
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
        stepBinding.setStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepBinding.startStepButton.setVisibility(View.VISIBLE);
                stepBinding.stopStepButton.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(stepBinding.updateStep.getText().toString())){
                    Toast.makeText(requireContext(), "Please, write a rate", Toast.LENGTH_LONG).show();
                }else{
                    rate = Integer.parseInt(stepBinding.updateStep.getText().toString());
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
        float st = event.values[0];
        stepCounter step = new stepCounter(st, dateTime);
        stViewModel.insert(step);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}