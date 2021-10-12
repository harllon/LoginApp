package ViewModel.Environment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.pressure;
import roomSensors.sensorRepository;

public class pressureViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private boolean isOn;
    private List<pressure> allPressure;
    private LiveData<List<pressure>> allPressureSensor;
    public pressureViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allPressureSensor = hardwareRepository.getAllPressure();
    }
    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public List<pressure> getAllPressure() {
        return allPressure;
    }

    public LiveData<List<pressure>> getAllPressureSensor() {
        return allPressureSensor;
    }

    public void update(List<pressure> allPressure) {
        this.allPressure = allPressure;
    }

    public void insert(pressure press){
        sensorRepository.insertPressure(press);
    }

    public void clear(){
        sensorRepository.clearPressure();
        allPressure.removeAll(allPressure);
    }
}
