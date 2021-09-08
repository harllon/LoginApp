package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSavedgpsBinding;

import java.util.List;
import java.util.Objects;

import Adapters.RecyclerView.gpsRecyclerAdapter;
import ViewModel.gpsLocationViewModel;
import roomGPS.gpsLocation;


public class SavedgpsFragment extends Fragment {
    private FragmentSavedgpsBinding savedgpsBinding;
    private gpsLocationViewModel gpsViewModel;
    private List<gpsLocation> allGps;
    private int control = 0;
    int i = 0;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected gpsRecyclerAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    //protected LayoutManagerType mCurrentLayoutManagerType;
    public SavedgpsFragment() {
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
        savedgpsBinding = FragmentSavedgpsBinding.inflate(inflater, container, false);
        //allGps = gpsViewModel.getAllLocations().getValue();
        //mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        return savedgpsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        gpsViewModel = new ViewModelProvider(requireActivity()).get(gpsLocationViewModel.class);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gpsRecycler);
        mLayoutManager = new LinearLayoutManager(requireActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new gpsRecyclerAdapter(gpsViewModel.getGps());
        Log.d("vendoAdapter", gpsViewModel.getGps().get(0).getLatitude()+"");
        mRecyclerView.setAdapter(mAdapter);

        gpsViewModel.getAllLocations().observe(getViewLifecycleOwner(), new Observer<List<gpsLocation>>() { //change: this -> getView
            @Override
            public void onChanged(List<gpsLocation> gps) {
                try{
                    allGps = Objects.requireNonNull(gps);
                    //Log.d("vendo", allGps.get(0).getId()+"");
                    //Log.d("vendo2", allGps.get(0).getId()+"");
                    mAdapter.setAllGpsLocations(allGps);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        clear();
    }

    void clear(){
        savedgpsBinding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsViewModel.clear();
                mAdapter.setAllGpsLocations(gpsViewModel.getGps());

            }
        });
    }
}