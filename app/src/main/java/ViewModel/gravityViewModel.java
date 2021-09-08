package ViewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import roomSensors.entities.gravity;
import roomSensors.sensorRepository;

public class gravityViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<gravity>> allGravitySensor;
    private List<gravity> allGravity;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }
    public gravityViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allGravitySensor = hardwareRepository.getAllGravity();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<gravity> allGravities){
        allGravity = allGravities;
    }
    public List<gravity> getGravity(){
        return allGravity;
    }

    public void clear(){
        sensorRepository.clearGravity();
        allGravity.removeAll(allGravity);
    }
    public LiveData<List<gravity>> getAllGravity(){
        return allGravitySensor;
    }

    public void insert(gravity grav){ sensorRepository.insertGravity(grav); }
}
