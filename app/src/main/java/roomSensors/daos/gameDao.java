package roomSensors.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roomSensors.entities.gameRotation;

@Dao
public interface gameDao {
    @Insert
    void insert(gameRotation game);

    @Query("DELETE FROM gameTable")
    void deleteAll();

    @Query("SELECT * FROM gameTable")
    LiveData<List<gameRotation>> getAll();
}
