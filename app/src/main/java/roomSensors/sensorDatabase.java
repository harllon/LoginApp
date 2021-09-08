package roomSensors;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roomSensors.daos.accelerometerDao;
import roomSensors.daos.gravityDao;
import roomSensors.daos.gyroscopeDao;
import roomSensors.daos.motionDao;
import roomSensors.daos.rotationDao;
import roomSensors.daos.stepCounterDao;
import roomSensors.entities.accelerometer;
import roomSensors.entities.gravity;
import roomSensors.entities.gyroscope;
import roomSensors.entities.motion;
import roomSensors.entities.rotation;
import roomSensors.entities.stepCounter;


@Database(entities = {gravity.class, accelerometer.class, gyroscope.class, motion.class, rotation.class, stepCounter.class}, version = 1, exportSchema = false)
public abstract class sensorDatabase extends RoomDatabase {
    public abstract gravityDao gravDao();
    public abstract accelerometerDao accDao();
    public abstract gyroscopeDao gyroDao();
    public abstract motionDao motDao();
    public abstract rotationDao rotDao();
    public abstract stepCounterDao stepDao();


    private static volatile sensorDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback cleanDb = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                gravityDao gravDao = INSTANCE.gravDao();
                gravDao.deleteAll();

                accelerometerDao accDao = INSTANCE.accDao();
                accDao.deleteAll();

                gyroscopeDao gyroDao = INSTANCE.gyroDao();
                gyroDao.deleteAll();

                motionDao motDao = INSTANCE.motDao();
                motDao.deleteAll();

                rotationDao rotDao = INSTANCE.rotDao();
                rotDao.deleteAll();

                stepCounterDao stepDao = INSTANCE.stepDao();
                stepDao.deleteAll();
            });
        }
    };

    static sensorDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (sensorDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), sensorDatabase.class, "sensor_database").addCallback(cleanDb).build();
                }
            }
        }
        return INSTANCE;
    }

}
