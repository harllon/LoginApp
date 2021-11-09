package ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class sensorViewModel extends AndroidViewModel {
    private boolean isAdmin;

    private boolean compassBool;
    private boolean speedBool;
    private boolean altitudeBool;
    private boolean noMotionBool;
    private boolean sunLightBool;
    public sensorViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isCompassBool() {
        return compassBool;
    }

    public void setCompassBool(boolean compassBool) {
        this.compassBool = compassBool;
    }

    public boolean isSpeedBool() {
        return speedBool;
    }

    public void setSpeedBool(boolean speedBool) {
        this.speedBool = speedBool;
    }

    public boolean isAltitudeBool() {
        return altitudeBool;
    }

    public void setAltitudeBool(boolean altitudeBool) {
        this.altitudeBool = altitudeBool;
    }

    public boolean isNoMotionBool() {
        return noMotionBool;
    }

    public void setNoMotionBool(boolean noMotionBool) {
        this.noMotionBool = noMotionBool;
    }

    public boolean isSunLightBool() {
        return sunLightBool;
    }

    public void setSunLightBool(boolean sunLightBool) {
        this.sunLightBool = sunLightBool;
    }
}
