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

import com.example.myapplication.databinding.FragmentGyroscopeBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ViewModel.Motion.gyroscopeViewModel;
import roomSensors.entities.gyroscope;

public class GyroscopeFragment extends Fragment implements SensorEventListener {
    private FragmentGyroscopeBinding gyroscopeBinding;
    private gyroscopeViewModel gyroViewModel;
    private Sensor sensor;
    private SensorManager sensorManager;
    private int rate;
    private List<gyroscope> allGyroscope;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Uri gyroLog;
    public GyroscopeFragment() {
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
        gyroscopeBinding = FragmentGyroscopeBinding.inflate(inflater, container, false);
        return gyroscopeBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        gyroViewModel = new ViewModelProvider(requireActivity()).get(gyroscopeViewModel.class);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }else{
            Toast.makeText(requireContext(), "This cellphone doesn't have this hardware", Toast.LENGTH_LONG).show();
        }
        gyroViewModel.getAllGyroscope().observe(getViewLifecycleOwner(), new Observer<List<gyroscope>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<gyroscope> gyroscopes) {
                allGyroscope = Objects.requireNonNull(gyroscopes);
                gyroViewModel.updateList(allGyroscope);
            }
        });
        gyroscopeBinding.stopGyroscopeButton.setVisibility(View.INVISIBLE);
        gyroscopeBinding.startGyroscopeButton.setVisibility(View.INVISIBLE);
        gyroscopeBinding.saveGyroscopeButton.setVisibility(View.INVISIBLE);
        gyroscopeBinding.shareButton.setVisibility(View.INVISIBLE);
        startTracking(this);
        stopTracking(this);
        save();
        updateRate();
        share();
    }


    public void startTracking(GyroscopeFragment t){
        gyroscopeBinding.startGyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyroscopeBinding.saveGyroscopeButton.setVisibility(View.INVISIBLE);
                gyroscopeBinding.shareButton.setVisibility(View.INVISIBLE);
                sensorManager.registerListener((SensorEventListener) t, sensor, rate);  //possivel causa de problemas
            }
        });
    }

    public void stopTracking(GyroscopeFragment t){
        gyroscopeBinding.stopGyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyroscopeBinding.saveGyroscopeButton.setVisibility(View.VISIBLE);
                sensorManager.unregisterListener((SensorEventListener) t);
            }
        });
    }
    public void share(){
        gyroscopeBinding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Gyroscope Log file");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, gyroLog);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }
    public void save(){
        gyroscopeBinding.saveGyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyroscopeBinding.shareButton.setVisibility(View.VISIBLE);
                String texto = "";
                for (int i = 0; i < gyroViewModel.getGyroscope().size(); i++) {
                    String wx = String.valueOf(gyroViewModel.getGyroscope().get(i).getWx());
                    String wy = String.valueOf(gyroViewModel.getGyroscope().get(i).getWy());
                    String wz = String.valueOf(gyroViewModel.getGyroscope().get(i).getWz());
                    String dataTime = gyroViewModel.getGyroscope().get(i).getDate();
                    texto = texto + "Wx: " + wx + "; Wy: " + wy + "; Wz: " + wz + "; Data and Time: " + dataTime + "\n";
                }
                Log.d("Textao: ", texto);
                File file = new File(requireActivity().getExternalFilesDir(null), "gyroLog.txt");
                gyroLog = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
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
        gyroscopeBinding.setGyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyroscopeBinding.startGyroscopeButton.setVisibility(View.VISIBLE);
                gyroscopeBinding.stopGyroscopeButton.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(gyroscopeBinding.updateGyroscope.getText().toString())){
                    Toast.makeText(requireContext(), "Please, write a rate", Toast.LENGTH_LONG).show();
                }else{
                    rate = Integer.parseInt(gyroscopeBinding.updateGyroscope.getText().toString());
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
        float wx = event.values[0];
        float wy = event.values[1];
        float wz = event.values[2];
        gyroscope gyro = new gyroscope(wx, wy, wz, dateTime, dateTime);
        gyroViewModel.insert(gyro);
        //Log.d("X: ", String.valueOf(x));
        //Log.d("Y: ", String.valueOf(y));
        //Log.d("Z: ", String.valueOf(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}