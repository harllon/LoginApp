package Fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import Utils.passwordHash;
import ViewModel.PersonViewModel;
import roomTest.Person;

public class HomeFragment extends Fragment implements SensorEventListener {
    private FragmentHomeBinding homeBinding;
    private PersonViewModel mPersonViewModel;
    private List<Person> listUsers;
    //private NavHostFragment navigation;
    public HomeFragment() {
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
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return homeBinding.getRoot();
    }
    private SensorManager sensorManager;
    private Sensor sensor;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        mPersonViewModel.getAllPerson().observe(getViewLifecycleOwner(), new Observer<List<Person>>() { //change: this -> getView
            @Override
            public void onChanged(List<Person> people) {
                try{
                    listUsers = Objects.requireNonNull(people);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        /*
        File file = new File(requireActivity().getExternalFilesDir(null), "testanto222.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String texto = "Estou tentando escrever nesse raio de arquivo";
            fos.write(texto.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        //FileInputStream fis = null;
        /*
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            String contents = stringBuilder.toString();
            Log.d("peguei", contents);
        }
        */
       /* sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            Log.d("verificando: ", "nao e nulo");
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, sensor, 5000000);
        }*/



        //Uri.fromFile(file);
        //send(file);
        SignIn();
        SignUp();
    }
    @Override
    public final void onSensorChanged(SensorEvent event){
        /*float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.d("axisX: ", String.valueOf(x));
        Log.d("axisY: ", String.valueOf(y));
        Log.d("axisZ: ", String.valueOf(z));*/

    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy){}

    /*void send(File file){
        homeBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri txt = FileProvider.getUriForFile(requireContext(), "com.example.myapplication.MainActivity2", file);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);*/
                //emailIntent.setType("*/*");
                //emailIntent.setData(Uri.parse("mailto:"));
               /* emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Opa legal");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Tentando enviar do app");
                emailIntent.putExtra(Intent.EXTRA_STREAM, txt);
                //startActivity(Intent.createChooser(emailIntent, "Sending..."));
                if(emailIntent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });
    }*/

    void SignUp(){
        homeBinding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean admin = false;
                Intent registerIntent = new Intent(getContext(), SignupActivity.class);
                registerIntent.putExtra("admin", admin);
                startActivity(registerIntent);
            }
        });
    }
    void SignIn(){
        homeBinding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkUsername = false;
                Boolean checkPassword = false;
                Boolean checkAdmin = false;
                if(TextUtils.isEmpty(homeBinding.PasswordEditText.getText().toString()) || TextUtils.isEmpty(homeBinding.UserNameEditText.getText().toString())){
                    Toast.makeText(getContext(), "You must fill in all fields", Toast.LENGTH_LONG).show();
                }else{
                    for (int i = 0; i< listUsers.size(); i++){
                        if(listUsers.get(i).getUserName().equals(homeBinding.UserNameEditText.getText().toString())){
                            checkUsername = true;
                        }
                        if(checkUsername) {
                            String password = passwordHash.getMd5(homeBinding.PasswordEditText.getText().toString());
                            if (listUsers.get(i).getUserPassword().equals(password)){
                                checkPassword = true;
                                checkAdmin = listUsers.get(i).getIsAdmin();
                                break;
                            }
                        }
                    }
                }
                if(checkUsername && checkPassword){
                    //Toast.makeText(MainActivity.this, "The user exist", Toast.LENGTH_LONG).show();
                    if(checkAdmin){
                        //Intent adminIntent = new Intent(getContext(), AdminActivity.class);
                        //startActivity(adminIntent);
                        //navigation.getNavController().navigate(R.id.adminFragment);
                        Navigation.findNavController(requireView()).navigate(R.id.adminFragment);
                    }else{
                        //Intent normalIntent = new Intent(getContext(), NormalActivity.class);
                        //startActivity(normalIntent);
                        Navigation.findNavController(requireView()).navigate(R.id.normalFragment);
                    }
                }else{
                    Toast.makeText(getContext(), "Username or password is wrong.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}