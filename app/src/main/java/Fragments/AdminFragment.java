package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.OptionsActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.FragmentAdminBinding;

import ViewModel.sensorViewModel;

public class AdminFragment extends Fragment {
    private FragmentAdminBinding adminBinding;

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

        adminBinding.adminToolbar.inflateMenu(R.menu.navigation_menu);

        //This section controls the menu bar
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
                default:
                    return false;
            }
        });

       /* adminBinding.compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.compassFragment);
            }
        });*/

        //Control the transition made by the sensor selection button
        adminBinding.sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent track = new Intent(requireContext(), OptionsActivity.class);
                boolean admin = true;
                track.putExtra("isAdmin", admin);
                startActivity(track);
            }
        });
    }
}