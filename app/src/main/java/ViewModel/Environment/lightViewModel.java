package ViewModel.Environment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.illuminance;
import roomSensors.sensorRepository;

public class lightViewModel extends AndroidViewModel{
    private sensorRepository hardwareRepository;
    private boolean isOn;
    private LiveData<List<illuminance>> allIlluminanceSensor;
    private List<illuminance> allIlluminance;
    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    public lightViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allIlluminanceSensor = hardwareRepository.getAllIlluminance();
    }

    public void setOn(boolean on) {
        isOn = on;
    }
    public boolean isOn() {
        return isOn;
    }

    public List<illuminance> getAllIlluminance() {
        return allIlluminance;
    }

    public LiveData<List<illuminance>> getAllIlluminanceSensor() {
        return allIlluminanceSensor;
    }

    public void update(List<illuminance> allIlluminance) {
        this.allIlluminance = allIlluminance;
    }

    public void insert(illuminance light){
        sensorRepository.insertIlluminance(light);
    }

    public void clear(){
        sensorRepository.clearIlluminance();
        allIlluminance.removeAll(allIlluminance);
    }
}
