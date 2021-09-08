package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.stepCounter;

@Dao
public interface stepCounterDao {
    @Insert
    void insert(stepCounter hardware);

    @Query("DELETE FROM stepTable")
    void deleteAll();

    @Query("SELECT * FROM stepTable")
    LiveData<List<stepCounter>> getAll();
}
