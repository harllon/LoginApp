package roomTest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PersonDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Person pessoa);

    @Query("DELETE FROM login_table")
    void deleteAll();

    @Query("SELECT * FROM login_table")
    LiveData<List<Person>> getAll();
}
