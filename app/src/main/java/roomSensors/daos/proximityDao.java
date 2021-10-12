package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.proximity;

@Dao
public interface proximityDao {
    @Insert
    void insert(proximity prox);

    @Query("DELETE FROM proximityTable")
    void deleteAll();

    @Query("SELECT * FROM proximityTable")
    LiveData<List<proximity>> getAll();
}
