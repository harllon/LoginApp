package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.magneticField;

@Dao
public interface magneticDao {
    @Insert
    void insert(magneticField magnetic);

    @Query("DELETE FROM magneticTable")
    void deleteAll();

    @Query("SELECT * FROM magneticTable")
    LiveData<List<magneticField>> getAll();
}
