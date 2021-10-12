package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.ambientTemperature;

@Dao
public interface ambtempDao {
    @Insert
    void insert(ambientTemperature ambtemperature);

    @Query("DELETE FROM temperatureTable")
    void deleteAll();

    @Query("SELECT * FROM temperatureTable")
    LiveData<List<ambientTemperature>> getAll();
}
