package ViewModel.Position;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.proximity;
import roomSensors.sensorRepository;

public class proximityViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<proximity>> allProximitySensor;
    private List<proximity> allProximity;
    public proximityViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allProximitySensor = hardwareRepository.getAllProximity();
    }

    private boolean isOn;
    public void setOn(boolean on) {
        isOn = on;
    }
    public boolean isOn() {
        return isOn;
    }

    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }
    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    public void updateList(List<proximity> allproximity){
        allProximity = allproximity;
    }
    public List<proximity> getAllProximity(){
        return allProximity;
    }
    public LiveData<List<proximity>> getAllProximitySensor(){
        return allProximitySensor;
    }
    public void insert(proximity prox){
        sensorRepository.insertProximity(prox);
    }
    public void clear(){
        sensorRepository.clearProximity();
        allProximity.removeAll(allProximity);
    }
}
