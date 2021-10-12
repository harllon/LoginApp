package ViewModel.Environment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.humidity;
import roomSensors.sensorRepository;

public class humidityViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private boolean isOn;
    private LiveData<List<humidity>> allHumiditySensor;
    private List<humidity> allHumidity;
    public humidityViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allHumiditySensor = hardwareRepository.getAllHumidity();
    }
    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public void update(List<humidity> allHumidity) {
        this.allHumidity = allHumidity;
    }

    public LiveData<List<humidity>> getAllHumiditySensor() {
        return allHumiditySensor;
    }

    public List<humidity> getAllHumidity() {
        return allHumidity;
    }

    public void insert(humidity hum){
        sensorRepository.insertHumidity(hum);
    }

    public void clear(){
        sensorRepository.clearHumidity();
        allHumidity.removeAll(allHumidity);
    }
}
