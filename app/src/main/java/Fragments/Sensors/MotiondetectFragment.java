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

import com.example.myapplication.databinding.FragmentMotionBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ViewModel.Motion.motionViewModel;
import roomSensors.entities.motion;

public class MotiondetectFragment extends Fragment implements SensorEventListener {
    private FragmentMotionBinding motionBinding;
    private motionViewModel motViewModel;
    private Sensor sensor;
    private SensorManager sensorManager;
    private int rate;
    private List<motion> allMotion;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Uri motionLog;
    public MotiondetectFragment() {
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
        motionBinding = FragmentMotionBinding.inflate(inflater, container, false);
        return motionBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        motViewModel = new ViewModelProvider(requireActivity()).get(motionViewModel.class);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
        }else{
            Toast.makeText(requireContext(), "This cellphone doesn't have this hardware", Toast.LENGTH_LONG).show();
        }
        motViewModel.getAllMotion().observe(getViewLifecycleOwner(), new Observer<List<motion>>() {
            @Override
            public void onChanged(List<motion> motions) {
                allMotion = Objects.requireNonNull(motions);
                motViewModel.updateList(allMotion);
            }
        });
        motionBinding.stopMotionButton.setVisibility(View.INVISIBLE);
        motionBinding.saveMotionButton.setVisibility(View.INVISIBLE);
        motionBinding.startMotionButton.setVisibility(View.INVISIBLE);
        motionBinding.shareButton.setVisibility(View.INVISIBLE);
        startTracking(this);
        stopTracking(this);
        save();
        updateRate();
        share();
    }


    public void startTracking(MotiondetectFragment t){
        motionBinding.startMotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motionBinding.saveMotionButton.setVisibility(View.INVISIBLE);
                motionBinding.shareButton.setVisibility(View.INVISIBLE);
                sensorManager.registerListener((SensorEventListener) t, sensor, rate);  //possivel causa de problemas
            }
        });
    }

    public void stopTracking(MotiondetectFragment t){
        motionBinding.stopMotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motionBinding.saveMotionButton.setVisibility(View.VISIBLE);
                sensorManager.unregisterListener((SensorEventListener) t);
            }
        });
    }
    public void share(){
        motionBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Motion Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, motionLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }
    public void save(){
        motionBinding.saveMotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motionBinding.shareButton.setVisibility(View.VISIBLE);
                String texto = "";
                for (int i = 0; i < motViewModel.getMotion().size(); i++) {
                    String mot = String.valueOf(motViewModel.getMotion().get(i).getMotion());
                    String dataTime = motViewModel.getMotion().get(i).getDate();
                    texto = texto + "Motion: " + mot + "; Data and Time: " + dataTime + "\n";
                }
                Log.d("Textao: ", texto);
                File file = new File(requireActivity().getExternalFilesDir(null), "motionLog.txt");
                motionLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
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
        motionBinding.setMotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motionBinding.startMotionButton.setVisibility(View.VISIBLE);
                motionBinding.stopMotionButton.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(motionBinding.updateMotion.getText().toString())){
                    Toast.makeText(requireContext(), "Please, write a rate", Toast.LENGTH_LONG).show();
                }else{
                    rate = Integer.parseInt(motionBinding.updateMotion.getText().toString());
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
        float moti = event.values[0];
        motion mot = new motion(moti, dateTime, dateTime);
        motViewModel.insert(mot);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}