package ViewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomSensors.entities.gyroscope;
import roomSensors.sensorRepository;

public class gyroscopeViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<gyroscope>> allGyroscopeSensor;
    private List<gyroscope> allGyroscope;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public gyroscopeViewModel(@NonNull Application application){
        super(application);
        hardwareRepository = new sensorRepository(application);
        allGyroscopeSensor = hardwareRepository.getAllGyroscope();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<gyroscope> allGyro){
        allGyroscope = allGyro;
    }
    public List<gyroscope> getGyroscope(){
        return allGyroscope;
    }

    public void clear(){
        sensorRepository.clearGyroscope();
        allGyroscope.removeAll(allGyroscope);
    }
    public LiveData<List<gyroscope>> getAllGyroscope(){
        return allGyroscopeSensor;
    }

    public void insert(gyroscope gyro){ sensorRepository.insertGyroscope(gyro); }
}
