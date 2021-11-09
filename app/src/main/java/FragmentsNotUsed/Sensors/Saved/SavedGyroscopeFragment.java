package FragmentsNotUsed.Sensors.Saved;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSavedGyroscopeBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.gyroscopeRecyclerAdapter;
import ViewModel.Motion.gyroscopeViewModel;
import roomSensors.entities.gyroscope;


public class SavedGyroscopeFragment extends Fragment {
    private FragmentSavedGyroscopeBinding savedGyroscopeBinding;
    private gyroscopeViewModel gyroViewModel;
    private List<gyroscope> allGyroscope;
    private gyroscopeRecyclerAdapter gyroscopeAdapter;
    public SavedGyroscopeFragment() {
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
        savedGyroscopeBinding = FragmentSavedGyroscopeBinding.inflate(inflater, container, false);
        return savedGyroscopeBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        gyroViewModel = new ViewModelProvider(requireActivity()).get(gyroscopeViewModel.class);
        gyroscopeAdapter = new gyroscopeRecyclerAdapter(gyroViewModel.getGyroscope());
        savedGyroscopeBinding.gyroscopeRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        savedGyroscopeBinding.gyroscopeRecycler.setAdapter(gyroscopeAdapter);
        gyroViewModel.getAllGyroscope().observe(getViewLifecycleOwner(), new Observer<List<gyroscope>>() {
            @Override
            public void onChanged(List<gyroscope> gyroscopes) {
                allGyroscope = Objects.requireNonNull(gyroscopes);
                gyroscopeAdapter.setAllGyroscope(allGyroscope);
            }
        });
        clear();
    }

    public void clear(){
        savedGyroscopeBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyroViewModel.clear();
                gyroscopeAdapter.setAllGyroscope(gyroViewModel.getGyroscope());
            }
        });
    }
}