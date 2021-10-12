package ViewModel.Motion;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomGPS.gpsLocation;
import roomGPS.gpsLocationRepository;


public class gpsLocationViewModel extends AndroidViewModel {
    private gpsLocationRepository gpsRepository;
    private final LiveData<List<gpsLocation>> allGpsLocation;
    private List<gpsLocation> allGps;
    private boolean isOn;

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {
        return isOn;
    }

    public gpsLocationViewModel(Application application){
        super(application);
        gpsRepository = new gpsLocationRepository(application);
        allGpsLocation = gpsRepository.getAllGpsLocation();
    }
    private MutableLiveData<Boolean> isCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck.setValue(check);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<gpsLocation> allLocalGps){
        allGps = allLocalGps;
    }
    public List<gpsLocation> getGps(){
        return allGps;
    }
    public void clear(){
        gpsRepository.clear();
        allGps.removeAll(allGps);
    }
    public LiveData<List<gpsLocation>> getAllLocations(){
        return allGpsLocation;
    }

    public void insert(gpsLocation gps){ gpsRepository.insert(gps); }
}
