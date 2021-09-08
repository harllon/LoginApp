package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import roomSensors.entities.motion;

@Dao
public interface motionDao {
    @Insert
    void insert(motion hardware);

    @Query("DELETE FROM motionTable")
    void deleteAll();

    @Query("SELECT * FROM motionTable")
    LiveData<List<motion>> getAll();
}
