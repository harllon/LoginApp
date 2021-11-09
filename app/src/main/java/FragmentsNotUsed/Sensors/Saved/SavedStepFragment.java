package FragmentsNotUsed.Sensors.Saved;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSavedStepBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.stepRecyclerAdapter;
import ViewModel.Motion.stepViewModel;
import roomSensors.entities.stepCounter;


public class SavedStepFragment extends Fragment {
    private FragmentSavedStepBinding savedStepBinding;
    protected stepRecyclerAdapter stepAdapter;
    private stepViewModel stViewModel;
    private List<stepCounter> allStep;

    public SavedStepFragment() {
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
        savedStepBinding = FragmentSavedStepBinding.inflate(inflater, container, false);
        return savedStepBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stViewModel = new ViewModelProvider(requireActivity()).get(stepViewModel.class);
        stepAdapter = new stepRecyclerAdapter(stViewModel.getStep());
        savedStepBinding.stepRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        savedStepBinding.stepRecycler.setAdapter(stepAdapter);
        stViewModel.getAllStep().observe(getViewLifecycleOwner(), new Observer<List<stepCounter>>() {
            @Override
            public void onChanged(List<stepCounter> stepCounters) {
                allStep = Objects.requireNonNull(stepCounters);
                stepAdapter.setAllStep(allStep);
            }
        });
        clear();
    }

    public void clear(){
        savedStepBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stViewModel.clear();
                stepAdapter.setAllStep(stViewModel.getStep());
            }
        });
    }
}