package Fragments.Sensors.Saved;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSavedRotationBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.rotationRecyclerAdapter;
import ViewModel.gravityViewModel;
import ViewModel.rotationViewModel;
import roomSensors.entities.gravity;
import roomSensors.entities.rotation;


public class SavedRotationFragment extends Fragment {
    private FragmentSavedRotationBinding savedRotationBinding;
    protected rotationRecyclerAdapter rotationAdapter;
    private rotationViewModel rotViewModel;
    private List<rotation> allRotation;


    public SavedRotationFragment() {
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
        savedRotationBinding = FragmentSavedRotationBinding.inflate(inflater, container, false);
        return savedRotationBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rotViewModel = new ViewModelProvider(requireActivity()).get(rotationViewModel.class);
        rotationAdapter = new rotationRecyclerAdapter(rotViewModel.getRotation());
        savedRotationBinding.rotationRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        savedRotationBinding.rotationRecycler.setAdapter(rotationAdapter);
        rotViewModel.getAllRotation().observe(getViewLifecycleOwner(), new Observer<List<rotation>>() {
            @Override
            public void onChanged(List<rotation> rotations) {
                allRotation = Objects.requireNonNull(rotations);
                rotationAdapter.setAllRotation(allRotation);
            }
        });
        clear();
    }


    public void clear(){
        savedRotationBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotViewModel.clear();
                rotationAdapter.setAllRotation(rotViewModel.getRotation());
            }
        });
    }
}