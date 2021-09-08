package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.accelerometer;

@Dao
public interface accelerometerDao {
    @Insert
    void insert(accelerometer hardware);

    @Query("DELETE FROM accelerometerTable")
    void deleteAll();

    @Query("SELECT * FROM accelerometerTable")
    LiveData<List<accelerometer>> getAll();
}
