package roomSensors;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

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

public class sensorRepository {
    private static gravityDao gravDao; //pode gerar problemas
    private LiveData<List<gravity>> allGravity;

    private static accelerometerDao accDao;
    private LiveData<List<accelerometer>> allAccelerometer;

    private static gyroscopeDao gyroDao;
    private LiveData<List<gyroscope>> allGyroscope;

    private static motionDao motDao;
    private LiveData<List<motion>> allMotion;

    private static rotationDao rotDao;
    private LiveData<List<rotation>> allRotation;

    private static stepCounterDao stepDao;
    private LiveData<List<stepCounter>> allStep;

    public sensorRepository(Application application){
        sensorDatabase db = sensorDatabase.getDatabase(application);
        gravDao = db.gravDao();
        allGravity = gravDao.getAll();

        accDao = db.accDao();
        allAccelerometer = accDao.getAll();

        gyroDao = db.gyroDao();
        allGyroscope = gyroDao.getAll();

        motDao = db.motDao();
        allMotion = motDao.getAll();

        rotDao = db.rotDao();
        allRotation = rotDao.getAll();

        stepDao = db.stepDao();
        allStep = stepDao.getAll();
    }

    public LiveData<List<gravity>> getAllGravity(){
        return allGravity;
    }

    public LiveData<List<accelerometer>> getAllAccelerometer() {
        return allAccelerometer;
    }

    public LiveData<List<gyroscope>> getAllGyroscope() {
        return allGyroscope;
    }

    public LiveData<List<motion>> getAllMotion() {
        return allMotion;
    }

    public LiveData<List<rotation>> getAllRotation() {
        return allRotation;
    }

    public LiveData<List<stepCounter>> getAllStep() {
        return allStep;
    }

    public static void insertGravity(gravity grav){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            gravDao.insert(grav);
        });
    }

    public static void insertAccelerometer(accelerometer acc){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            accDao.insert(acc);
        });
    }
    public static void insertGyroscope(gyroscope gyro){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            gyroDao.insert(gyro);
        });
    }
    public static void insertMotion(motion mot){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            motDao.insert(mot);
        });
    }
    public static void insertRotation(rotation rot){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            rotDao.insert(rot);
        });
    }
    public static void insertStep(stepCounter step){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            stepDao.insert(step);
        });
    }

    public static void clearGravity(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            gravDao.deleteAll();
        });
    }

    public static void clearAccelerometer(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            accDao.deleteAll();
        });
    }
    public static void clearGyroscope(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            gyroDao.deleteAll();
        });
    }
    public static void clearMotion(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            motDao.deleteAll();
        });
    }
    public static void clearRotation(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            rotDao.deleteAll();
        });
    }
    public static void clearStep(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            stepDao.deleteAll();
        });
    }
}
