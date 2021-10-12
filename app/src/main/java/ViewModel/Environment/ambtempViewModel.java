package ViewModel.Environment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.ambientTemperature;
import roomSensors.sensorRepository;

public class ambtempViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<ambientTemperature>> allAmbTempSensor;
    private List<ambientTemperature> allAmbTemp;
    private boolean isOn;

    public ambtempViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allAmbTempSensor = hardwareRepository.getAllAmbTemp();
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

    public List<ambientTemperature> getAllAmbTemp() {
        return allAmbTemp;
    }

    public LiveData<List<ambientTemperature>> getAllAmbTempSensor() {
        return allAmbTempSensor;
    }

    public void update(List<ambientTemperature> allTemp){
        allAmbTemp = allTemp;
    }
    public void clear(){
        sensorRepository.clearAmbTemp();
        allAmbTemp.removeAll(allAmbTemp);
    }
    public void insert(ambientTemperature temperature){
        sensorRepository.insertAmbTemp(temperature);
    }
}
