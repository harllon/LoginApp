package ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import Utils.sensorName;

public class sensorViewModel extends AndroidViewModel {
    private List<sensorName> sensor = new ArrayList<sensorName>();
    private boolean isAdmin;
    public sensorViewModel(@NonNull Application application) {
        super(application);
    }

    public void setSensor(sensorName Sensor) {
        sensor.add(Sensor);
    }

    public List<sensorName> getSensor() {
        return sensor;
    }

    public void clear(){
        sensor.removeAll(sensor);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
