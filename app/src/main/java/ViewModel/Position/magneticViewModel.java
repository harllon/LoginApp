package ViewModel.Position;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.magneticField;
import roomSensors.sensorRepository;

public class magneticViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<magneticField>> allMagneticSensor;
    private List<magneticField> allMagnetic;
    public magneticViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allMagneticSensor = hardwareRepository.getAllMagnetic();
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

    public void updateList(List<magneticField> allmagnetic){
        allMagnetic = allmagnetic;
    }
    public List<magneticField> getAllMagnetic(){
        return allMagnetic;
    }
    public LiveData<List<magneticField>> getAllMagneticSensor(){
        return allMagneticSensor;
    }
    public void insert(magneticField magnetic){
        sensorRepository.insertMagnetic(magnetic);
    }
    public void clear(){
        sensorRepository.clearMagnetic();
        allMagnetic.removeAll(allMagnetic);
    }
}
