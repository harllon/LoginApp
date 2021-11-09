package FragmentsNotUsed.Sensors.Saved;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSavedAccelerometerBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.accelerometerRecyclerAdapter;
import ViewModel.Motion.accelerometerViewModel;
import roomSensors.entities.accelerometer;


public class SavedAccelerometerFragment extends Fragment {
    private FragmentSavedAccelerometerBinding savedAccelerometerBinding;
    private accelerometerViewModel accViewModel;
    private List<accelerometer> allAcc;
    protected accelerometerRecyclerAdapter accelerometerAdapter;
    public SavedAccelerometerFragment() {
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
        savedAccelerometerBinding = FragmentSavedAccelerometerBinding.inflate(inflater, container, false);
        return savedAccelerometerBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        accViewModel = new ViewModelProvider(requireActivity()).get(accelerometerViewModel.class);
        accelerometerAdapter = new accelerometerRecyclerAdapter(accViewModel.getAccelerometer());
        savedAccelerometerBinding.accelerometerRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        savedAccelerometerBinding.accelerometerRecycler.setAdapter(accelerometerAdapter);
        accViewModel.getAllAccelerometer().observe(getViewLifecycleOwner(), new Observer<List<accelerometer>>() {
            @Override
            public void onChanged(List<accelerometer> accelerometers) {
                allAcc = Objects.requireNonNull(accelerometers);
                accelerometerAdapter.setAllAccelerometer(allAcc);
            }
        });
        clear();
    }

    public void clear(){
        savedAccelerometerBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accViewModel.clear();
                accelerometerAdapter.setAllAccelerometer(accViewModel.getAccelerometer());
            }
        });
    }
}