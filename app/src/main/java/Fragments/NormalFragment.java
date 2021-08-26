package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.GpsActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.FragmentNormalBinding;

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
            switch(item.getItemId()){
                case R.id.nav_logout:
                    Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
                    return true;
                case R.id.nav_GPS:
                    Intent gpsIntent = new Intent(requireContext(), GpsActivity.class);
                    startActivity(gpsIntent);
                    return true;
                default:
                    return false;
            }
        });
        //returnSign();
    }

    /*void returnSign(){
        normalBinding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent signinIntent = new Intent(NormalActivity.this, MainActivity2.class);
                //startActivity(signinIntent);
                Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
            }
        });
    }*/
}