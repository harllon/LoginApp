package Fragments.Sensors.Saved;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSavedGravityBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.gravityRecyclerAdapter;
import ViewModel.gravityViewModel;
import roomSensors.entities.gravity;

public class SavedGravityFragment extends Fragment {
    private FragmentSavedGravityBinding savedGravityBinding;
    private gravityViewModel gravViewModel;
    private List<gravity> allGravities;
    protected gravityRecyclerAdapter gravityAdapter;
    public SavedGravityFragment() {
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
        savedGravityBinding = FragmentSavedGravityBinding.inflate(inflater, container, false);
        return savedGravityBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        gravViewModel = new ViewModelProvider(requireActivity()).get(gravityViewModel.class);
        gravityAdapter = new gravityRecyclerAdapter(gravViewModel.getGravity());
        savedGravityBinding.gravityRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        savedGravityBinding.gravityRecycler.setAdapter(gravityAdapter);
        gravViewModel.getAllGravity().observe(getViewLifecycleOwner(), new Observer<List<gravity>>() {
            @Override
            public void onChanged(List<gravity> gravities) {
                try{
                    allGravities = Objects.requireNonNull(gravities);
                    gravityAdapter.setAllGravity(gravities);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        clear();
    }

    public void clear(){
        savedGravityBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravViewModel.clear();
                gravityAdapter.setAllGravity(gravViewModel.getGravity());
            }
        });
    }
}