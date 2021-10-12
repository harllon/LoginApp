package ViewModel.Position;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roomSensors.entities.gameRotation;
import roomSensors.sensorRepository;

public class gameViewModel extends AndroidViewModel {
    private sensorRepository hardwareRepository;
    private final LiveData<List<gameRotation>> allGameSensor;
    private List<gameRotation> allGame;
    public gameViewModel(@NonNull Application application) {
        super(application);
        hardwareRepository = new sensorRepository(application);
        allGameSensor = hardwareRepository.getAllGame();
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

    public void updateList(List<gameRotation> allgame){
        allGame = allgame;
    }
    public List<gameRotation> getGame(){
        return allGame;
    }
    public LiveData<List<gameRotation>> getAllGameSensor(){
        return allGameSensor;
    }

    public void insert(gameRotation game){
        sensorRepository.insertGame(game);
    }
    public void clear(){
        sensorRepository.clearGame();
        allGame.removeAll(allGame);
    }
}
