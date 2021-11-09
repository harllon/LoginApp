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
import ViewModel.sensorViewModel;
import roomTest.Person;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;

    private PersonViewModel mPersonViewModel;
    private sensorViewModel ssViewModel;

    private List<Person> listUsers;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        ssViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);

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

        SignIn();
        SignUp();
    }

    //Function responsible to take the user to the signUp screen
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

    //Function responsible for the login
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
                    if(checkAdmin){
                        ssViewModel.setAdmin(true);
                        Navigation.findNavController(requireView()).navigate(R.id.adminFragment);
                    }else{
                        ssViewModel.setAdmin(false);
                        Navigation.findNavController(requireView()).navigate(R.id.normalFragment);
                    }
                }else{
                    Toast.makeText(getContext(), "Username or password is wrong.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}