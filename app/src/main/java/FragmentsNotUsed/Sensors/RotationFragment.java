package FragmentsNotUsed.Sensors;

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

import com.example.myapplication.databinding.FragmentRotationBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ViewModel.Motion.rotationViewModel;
import roomSensors.entities.rotation;

public class RotationFragment extends Fragment implements SensorEventListener {
    private FragmentRotationBinding rotationBinding;
    private rotationViewModel rotViewModel;
    private Sensor sensor;
    private SensorManager sensorManager;
    private int rate;
    private List<rotation> allRotation;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Uri rotLog;
    public RotationFragment() {
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
        rotationBinding = FragmentRotationBinding.inflate(inflater, container, false);
        return rotationBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rotViewModel = new ViewModelProvider(requireActivity()).get(rotationViewModel.class);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        }else{
            Toast.makeText(requireContext(), "This cellphone doesn't have this hardware", Toast.LENGTH_LONG).show();
        }
        rotViewModel.getAllRotation().observe(getViewLifecycleOwner(), new Observer<List<rotation>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<rotation> rotations) {
                allRotation = Objects.requireNonNull(rotations);
                rotViewModel.updateList(allRotation);
            }
        });
        rotationBinding.stopRotationButton.setVisibility(View.INVISIBLE);
        rotationBinding.startRotationButton.setVisibility(View.INVISIBLE);
        rotationBinding.saveRotationButton.setVisibility(View.INVISIBLE);
        rotationBinding.shareButton.setVisibility(View.INVISIBLE);
        startTracking(this);
        stopTracking(this);
        save();
        updateRate();
        share();
    }


    public void startTracking(RotationFragment t){
        rotationBinding.startRotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationBinding.saveRotationButton.setVisibility(View.INVISIBLE);
                rotationBinding.shareButton.setVisibility(View.INVISIBLE);
                sensorManager.registerListener((SensorEventListener) t, sensor, rate);  //possivel causa de problemas
            }
        });
    }

    public void stopTracking(RotationFragment t){
        rotationBinding.stopRotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationBinding.saveRotationButton.setVisibility(View.VISIBLE);
                sensorManager.unregisterListener((SensorEventListener) t);
            }
        });
    }

    public void share(){
        rotationBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Rotation Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, rotLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }

    public void save(){
        rotationBinding.saveRotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationBinding.shareButton.setVisibility(View.VISIBLE);
                String texto = "";
                for (int i = 0; i < rotViewModel.getRotation().size(); i++) {
                    String xsin = String.valueOf(rotViewModel.getRotation().get(i).getXsin());
                    String ysin = String.valueOf(rotViewModel.getRotation().get(i).getYsin());
                    String zsin = String.valueOf(rotViewModel.getRotation().get(i).getZsin());
                    String cos = String.valueOf(rotViewModel.getRotation().get(i).getCos());
                    String sha = String.valueOf(rotViewModel.getRotation().get(i).getSha());
                    String dataTime = rotViewModel.getRotation().get(i).getDate();
                    texto = texto + "Xsin: " + xsin + "; Ysin: " + ysin + "; Zsin: " + zsin + "; Cos: " + cos + "; Estimated Accuracy: " + sha + "; Data and Time: " + dataTime + "\n";
                }
                Log.d("Textao: ", texto);
                File file = new File(requireActivity().getExternalFilesDir(null), "rotLog.txt");
                rotLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
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
        rotationBinding.setRotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationBinding.startRotationButton.setVisibility(View.VISIBLE);
                rotationBinding.stopRotationButton.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(rotationBinding.updateRotation.getText().toString())){
                    Toast.makeText(requireContext(), "Please, write a rate", Toast.LENGTH_LONG).show();
                }else{
                    rate = Integer.parseInt(rotationBinding.updateRotation.getText().toString());
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
        float xsin = event.values[0];
        float ysin = event.values[1];
        float zsin = event.values[2];
        float cos = event.values[3];
        float estimated = event.values[4];
        rotation rot = new rotation(xsin, ysin, zsin, cos, estimated, dateTime, dateTime);
        rotViewModel.insert(rot);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}