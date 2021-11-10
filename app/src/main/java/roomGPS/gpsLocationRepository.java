package roomGPS;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import roomTest.Person;
import roomTest.PersonRoomDatabase;

public class gpsLocationRepository {
    private gpsLocationDao gpsDao;
    private LiveData<List<gpsLocation>> allGpsLocation;

    public gpsLocationRepository(Application application){
        gpsLocationDatabase db = gpsLocationDatabase.getDatabase(application);
        gpsDao = db.gpsDao();
        allGpsLocation = gpsDao.getAll();
    }

    public LiveData<List<gpsLocation>> getAllGpsLocation(){
        return allGpsLocation;
    }

    public void insert(gpsLocation gpsLocal){
        gpsLocationDatabase.databaseWriteExecutor.execute(() -> {
            gpsDao.insert(gpsLocal);
        });
    }

    public void clear(){
        gpsLocationDatabase.databaseWriteExecutor.execute(() -> {
            gpsDao.deleteAll();
        });
    }

}
