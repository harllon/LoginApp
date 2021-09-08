package ViewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomSensors.entities.rotation;
import roomSensors.entities.stepCounter;
import roomSensors.sensorRepository;

public class stepViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<stepCounter>> allStepSensor;
    private List<stepCounter> allStep;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public stepViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allStepSensor = hardwareRepository.getAllStep();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<stepCounter> allst){
        allStep = allst;
    }
    public List<stepCounter> getStep(){
        return allStep;
    }

    public void clear(){
        sensorRepository.clearStep();
        allStep.removeAll(allStep);
    }
    public LiveData<List<stepCounter>> getAllStep(){
        return allStepSensor;
    }

    public void insert(stepCounter step){ sensorRepository.insertStep(step); }
}
