package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import roomSensors.entities.rotation;

@Dao
public interface rotationDao {
    @Insert
    void insert(rotation hardware);

    @Query("DELETE FROM rotationTable")
    void deleteAll();

    @Query("SELECT * FROM rotationTable")
    LiveData<List<rotation>> getAll();
}
