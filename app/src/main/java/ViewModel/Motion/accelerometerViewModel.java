package ViewModel.Motion;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.accelerometer;
import roomSensors.entities.gravity;
import roomSensors.sensorRepository;

public class accelerometerViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<accelerometer>> allAccelerometerSensor;
    private List<accelerometer> allAccelerometer;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public accelerometerViewModel(@NonNull Application application){
        super(application);
        hardwareRepository = new sensorRepository(application);
        allAccelerometerSensor = hardwareRepository.getAllAccelerometer();
    }
    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<accelerometer> allAcc){
        allAccelerometer = allAcc;
    }
    public List<accelerometer> getAccelerometer(){
        return allAccelerometer;
    }

    public void clear(){
        sensorRepository.clearAccelerometer();
        allAccelerometer.removeAll(allAccelerometer);
    }
    public LiveData<List<accelerometer>> getAllAccelerometer(){
        return allAccelerometerSensor;
    }

    public void insert(accelerometer acc){ sensorRepository.insertAccelerometer(acc); }
}
