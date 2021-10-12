package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.illuminance;

@Dao
public interface illuminanceDao {
    @Insert
    void insert(illuminance light);

    @Query("DELETE FROM illuminanceTable")
    void deleteAll();

    @Query("SELECT * FROM illuminanceTable")
    LiveData<List<illuminance>> getAll();

}
