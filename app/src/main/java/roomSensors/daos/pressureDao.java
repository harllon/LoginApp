package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.pressure;

@Dao
public interface pressureDao {
    @Insert
    void insert(pressure press);

    @Query("DELETE FROM pressureTable")
    void deleteAll();

    @Query("SELECT * FROM pressureTable")
    LiveData<List<pressure>> getAll();
}
