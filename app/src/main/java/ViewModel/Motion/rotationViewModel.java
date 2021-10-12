package ViewModel.Motion;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import roomSensors.entities.rotation;
import roomSensors.sensorRepository;

public class rotationViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<rotation>> allRotationSensor;
    private List<rotation> allRotation;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public rotationViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allRotationSensor = hardwareRepository.getAllRotation();
    }
    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<rotation> allRot){
        allRotation = allRot;
    }
    public List<rotation> getRotation(){
        return allRotation;
    }

    public void clear(){
        sensorRepository.clearRotation();
        allRotation.removeAll(allRotation);
    }
    public LiveData<List<rotation>> getAllRotation(){
        return allRotationSensor;
    }

    public void insert(rotation rot){ sensorRepository.insertRotation(rot); }
}
