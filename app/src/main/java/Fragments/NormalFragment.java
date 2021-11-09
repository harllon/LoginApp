package Fragments;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.OptionsActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNormalBinding;

import Utils.sensorName;
import ViewModel.sensorViewModel;

public class NormalFragment extends Fragment {
    private FragmentNormalBinding normalBinding;

    public NormalFragment() {
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
        normalBinding = FragmentNormalBinding.inflate(inflater, container, false);
        return normalBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        normalBinding.normalToolbar.inflateMenu(R.menu.normal_menu);
        normalBinding.normalToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
                return true;
            }
            return false;
        });

        normalBinding.selectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent track = new Intent(requireContext(), OptionsActivity.class);
                boolean admin = false;
                track.putExtra("isAdmin", admin);
                startActivity(track);
            }
        });
    }


}