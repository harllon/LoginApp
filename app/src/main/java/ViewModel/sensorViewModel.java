package ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class sensorViewModel extends AndroidViewModel {
    private boolean isAdmin;
    private MutableLiveData<Boolean> clicked = new MutableLiveData<>();
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
}
