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

import com.example.myapplication.databinding.FragmentGravityBinding;

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
import roomSensors.entities.gravity;

public class GravityFragment extends Fragment implements SensorEventListener {
    private FragmentGravityBinding gravityBinding;
    private gravityViewModel gravViewModel;
    private Sensor sensor;
    private SensorManager sensorManager;
    private int rate;
    private List<gravity> allGravity;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Uri gravLog;
    public GravityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gravityBinding = FragmentGravityBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        gravityBinding.startGravityButton.setVisibility(View.INVISIBLE);
        gravityBinding.stopGravityButton.setVisibility(View.INVISIBLE);
        gravityBinding.saveGravityButton.setVisibility(View.INVISIBLE);
        gravityBinding.shareButton.setVisibility(View.INVISIBLE);
        return gravityBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        gravViewModel = new ViewModelProvider(requireActivity()).get(gravityViewModel.class);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }else{
            Toast.makeText(requireContext(), "This cellphone doesn't have this hardware", Toast.LENGTH_LONG).show();
        }

        gravViewModel.getAllGravity().observe(getViewLifecycleOwner(), new Observer<List<gravity>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<gravity> gravities) {
                try {
                    allGravity = Objects.requireNonNull(gravities);
                    gravViewModel.updateList(allGravity);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        startTracking(this);
        stopTracking(this);
        save();
        updateRate();
        share();
    }


    public void startTracking(GravityFragment t){
        gravityBinding.startGravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravityBinding.saveGravityButton.setVisibility(View.INVISIBLE);
                gravityBinding.shareButton.setVisibility(View.INVISIBLE);
                sensorManager.registerListener((SensorEventListener) t, sensor, rate);

            }
        });
    }

    public void stopTracking(GravityFragment t){
        gravityBinding.stopGravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravityBinding.saveGravityButton.setVisibility(View.VISIBLE);
                sensorManager.unregisterListener((SensorEventListener) t);
            }
        });
    }
    public void share(){
        gravityBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Gravity Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, gravLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }
    public void save(){
        gravityBinding.saveGravityButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                    gravityBinding.shareButton.setVisibility(View.VISIBLE);
                    String texto = "";
                    for (int i = 0; i < gravViewModel.getGravity().size(); i++) {
                        String gx = String.valueOf(gravViewModel.getGravity().get(i).getGx());
                        String gy = String.valueOf(gravViewModel.getGravity().get(i).getGy());
                        String gz = String.valueOf(gravViewModel.getGravity().get(i).getGz());
                        String dataTime = gravViewModel.getGravity().get(i).getDateTime();
                        texto = texto + "Gx: " + gx + "; Gy: " + gy + "; Gz: " + gz + "; Data and Time: " + dataTime + "\n";
                    }
                    Log.d("Textao: ", texto);
                    File file = new File(requireActivity().getExternalFilesDir(null), "gravLog.txt");
                    gravLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
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
        gravityBinding.setGravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravityBinding.startGravityButton.setVisibility(View.VISIBLE);
                gravityBinding.stopGravityButton.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(gravityBinding.updateGravity.getText().toString())){
                    Toast.makeText(requireContext(), "Please, write a rate", Toast.LENGTH_LONG).show();
                }else{
                    rate = Integer.parseInt(gravityBinding.updateGravity.getText().toString());
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
        float gx = event.values[0];
        float gy = event.values[1];
        float gz = event.values[2];
        gravity grav = new gravity(gx, gy, gz, dateTime);
        gravViewModel.insert(grav);
        //Log.d("X: ", String.valueOf(x));
        //Log.d("Y: ", String.valueOf(y));
        //Log.d("Z: ", String.valueOf(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}