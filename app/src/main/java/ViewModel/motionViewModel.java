package ViewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomSensors.entities.gyroscope;
import roomSensors.entities.motion;
import roomSensors.sensorRepository;

public class motionViewModel extends AndroidViewModel{
    private sensorRepository hardwareRepository;
    private final LiveData<List<motion>> allMotionSensor;
    private List<motion> allMotion;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public motionViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allMotionSensor = hardwareRepository.getAllMotion();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<motion> allMot){
        allMotion = allMot;
    }
    public List<motion> getMotion(){
        return allMotion;
    }

    public void clear(){
        sensorRepository.clearMotion();
        allMotion.removeAll(allMotion);
    }
    public LiveData<List<motion>> getAllMotion(){
        return allMotionSensor;
    }

    public void insert(motion mot){ sensorRepository.insertMotion(mot); }
}
