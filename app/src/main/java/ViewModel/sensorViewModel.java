package ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class sensorViewModel extends AndroidViewModel {
    private boolean isAdmin;
    private MutableLiveData<Boolean> clicked = new MutableLiveData<>();
    private boolean ratebool;
    private boolean amplitudebool;
    private boolean speedbool;
    private boolean altitudebool;
    private boolean nomotionbool;
    private boolean sunlightbool;
    public sensorViewModel(@NonNull Application application) {
        super(application);
        //assert false;
        //clicked.setValue(false);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public boolean wasClicked(){
        return clicked.getValue();
    }
    public void setClicked(Boolean click){
        clicked.setValue(click);
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public MutableLiveData<Boolean> getClicked() {
        return clicked;
    }

    public boolean isRatebool() {
        return ratebool;
    }

    public void setRatebool(boolean ratebool) {
        this.ratebool = ratebool;
    }

    public boolean isAmplitudebool() {
        return amplitudebool;
    }

    public void setAmplitudebool(boolean amplitudebool) {
        this.amplitudebool = amplitudebool;
    }

    public boolean isSpeedbool() {
        return speedbool;
    }

    public void setSpeedbool(boolean speedbool) {
        this.speedbool = speedbool;
    }

    public boolean isAltitudebool() {
        return altitudebool;
    }

    public void setAltitudebool(boolean altitudebool) {
        this.altitudebool = altitudebool;
    }

    public boolean isNomotionbool() {
        return nomotionbool;
    }

    public void setNomotionbool(boolean nomotionbool) {
        this.nomotionbool = nomotionbool;
    }

    public boolean isSunlightbool() {
        return sunlightbool;
    }

    public void setSunlightbool(boolean sunlightbool) {
        this.sunlightbool = sunlightbool;
    }
}
