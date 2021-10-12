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
import roomSensors.daos.ambtempDao;
import roomSensors.daos.gameDao;
import roomSensors.daos.gravityDao;
import roomSensors.daos.gyroscopeDao;
import roomSensors.daos.humidityDao;
import roomSensors.daos.illuminanceDao;
import roomSensors.daos.magneticDao;
import roomSensors.daos.motionDao;
import roomSensors.daos.pressureDao;
import roomSensors.daos.proximityDao;
import roomSensors.daos.rotationDao;
import roomSensors.daos.stepCounterDao;
import roomSensors.entities.accelerometer;
import roomSensors.entities.ambientTemperature;
import roomSensors.entities.gameRotation;
import roomSensors.entities.gravity;
import roomSensors.entities.gyroscope;
import roomSensors.entities.humidity;
import roomSensors.entities.illuminance;
import roomSensors.entities.magneticField;
import roomSensors.entities.motion;
import roomSensors.entities.pressure;
import roomSensors.entities.proximity;
import roomSensors.entities.rotation;
import roomSensors.entities.stepCounter;


@Database(entities = {gravity.class, accelerometer.class, gyroscope.class, motion.class, rotation.class, stepCounter.class, ambientTemperature.class, humidity.class, illuminance.class, pressure.class, magneticField.class, gameRotation.class, proximity.class}, version = 1, exportSchema = false)
public abstract class sensorDatabase extends RoomDatabase {
    public abstract gravityDao gravDao();
    public abstract accelerometerDao accDao();
    public abstract gyroscopeDao gyroDao();
    public abstract motionDao motDao();
    public abstract rotationDao rotDao();
    public abstract stepCounterDao stepDao();
    public abstract ambtempDao ambDao();
    public abstract pressureDao pressDao();
    public abstract humidityDao humDao();
    public abstract illuminanceDao lightDao();
    public abstract magneticDao magDao();
    public abstract proximityDao proxDao();
    public abstract gameDao gameDao();


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

                ambtempDao ambDao = INSTANCE.ambDao();
                ambDao.deleteAll();

                illuminanceDao lightDao = INSTANCE.lightDao();
                lightDao.deleteAll();

                pressureDao pressDao = INSTANCE.pressDao();
                pressDao.deleteAll();

                humidityDao humDao = INSTANCE.humDao();
                humDao.deleteAll();

                gameDao gameDao = INSTANCE.gameDao();
                gameDao.deleteAll();

                magneticDao magDao = INSTANCE.magDao();
                magDao.deleteAll();

                proximityDao proxDao = INSTANCE.proxDao();
                proxDao.deleteAll();
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
