package roomGPS;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface gpsLocationDao {
    @Insert
    void insert(gpsLocation local);

    @Query("DELETE FROM location")
    void deleteAll();

    @Query("SELECT * FROM location")
    LiveData<List<gpsLocation>> getAll();

}
