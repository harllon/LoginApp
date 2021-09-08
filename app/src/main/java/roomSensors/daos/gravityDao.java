package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.gravity;


@Dao
public interface gravityDao {
    @Insert
    void insert(gravity hardware);

    @Query("DELETE FROM gravityTable")
    void deleteAll();

    @Query("SELECT * FROM gravityTable")
    LiveData<List<gravity>> getAll();
}
