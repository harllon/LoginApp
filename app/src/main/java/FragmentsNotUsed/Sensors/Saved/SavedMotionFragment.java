package FragmentsNotUsed.Sensors.Saved;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSavedMotionBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.motionRecyclerAdapter;
import ViewModel.Motion.motionViewModel;
import roomSensors.entities.motion;


public class SavedMotionFragment extends Fragment {
    private FragmentSavedMotionBinding savedMotionBinding;
    protected motionRecyclerAdapter motionAdapter;
    private motionViewModel motViewModel;
    private List<motion> allMotion;

    public SavedMotionFragment() {
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
        savedMotionBinding = FragmentSavedMotionBinding.inflate(inflater, container, false);
        return savedMotionBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        motViewModel = new ViewModelProvider(requireActivity()).get(motionViewModel.class);
        motionAdapter = new motionRecyclerAdapter(motViewModel.getMotion());
        savedMotionBinding.motionRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        savedMotionBinding.motionRecycler.setAdapter(motionAdapter);
        motViewModel.getAllMotion().observe(getViewLifecycleOwner(), new Observer<List<motion>>() {
            @Override
            public void onChanged(List<motion> motions) {
                allMotion = Objects.requireNonNull(motions);
                motionAdapter.setAllMotion(allMotion);
            }
        });
        clear();
    }

    public void clear(){
        savedMotionBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motViewModel.clear();
                motionAdapter.setAllMotion(motViewModel.getMotion());
            }
        });
    }
}