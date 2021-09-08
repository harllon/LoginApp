package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import roomSensors.entities.gyroscope;

@Dao
public interface gyroscopeDao {
    @Insert
    void insert(gyroscope hardware);

    @Query("DELETE FROM gyroscopeTable")
    void deleteAll();

    @Query("SELECT * FROM gyroscopeTable")
    LiveData<List<gyroscope>> getAll();
}
