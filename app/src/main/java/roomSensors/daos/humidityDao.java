package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.humidity;

@Dao
public interface humidityDao {
    @Insert
    void insert(humidity hum);

    @Query("DELETE FROM humidityTable")
    void deleteAll();

    @Query("SELECT * FROM humidityTable")
    LiveData<List<humidity>> getAll();
}
