package Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.GpsActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.FragmentAdminBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AdminFragment extends Fragment {
    private FragmentAdminBinding adminBinding;
    //private FusedLocationProviderClient fusedLocationClient;
    int PERMISSION_ID = 44;

    public AdminFragment() {
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
        adminBinding = FragmentAdminBinding.inflate(inflater, container, false);
        return adminBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        adminBinding.adminToolbar.inflateMenu(R.menu.navigation_menu);
        adminBinding.adminToolbar.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_logout:
                    Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
                    return true;
                case R.id.nav_register:
                    Boolean admin = true;
                    Intent registerIntent = new Intent(requireContext(), SignupActivity.class);
                    registerIntent.putExtra("admin", admin);
                    startActivity(registerIntent);
                    return true;
                case R.id.nav_GPS:
                    Intent gpsIntent = new Intent(requireContext(), GpsActivity.class);
                    startActivity(gpsIntent);
                default:
                    return false;
            }
        });
        //returnMain();
        //registerAdmin();
    }

    /*void returnMain(){
        adminBinding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent mainIntent = new Intent(requireContext(), MainActivity.class);
                //startActivity(mainIntent);
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
            }
        });
    }*/

    /*void registerAdmin(){
        adminBinding.adminregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean admin = true;
                Intent registerIntent = new Intent(requireContext(), SignupActivity.class);
                registerIntent.putExtra("admin", admin);
                startActivity(registerIntent);
            }
        });
    }*/

}