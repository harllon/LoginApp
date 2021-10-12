package roomSensors;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

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

public class sensorRepository {
    private static gravityDao gravDao;
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

    private static ambtempDao ambDao;
    private LiveData<List<ambientTemperature>> allAmbTemp;

    private static pressureDao pressDao;
    private LiveData<List<pressure>> allPressure;

    private static humidityDao humDao;
    private LiveData<List<humidity>> allHumidity;

    private static illuminanceDao lightDao;
    private LiveData<List<illuminance>> allIlluminance;

    private static roomSensors.daos.gameDao gameDao;
    private LiveData<List<gameRotation>> allGame;

    private static magneticDao magDao;
    private LiveData<List<magneticField>> allMagnetic;

    private static proximityDao proxDao;
    private LiveData<List<proximity>> allProximity;


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

        ambDao = db.ambDao();
        allAmbTemp = ambDao.getAll();

        pressDao = db.pressDao();
        allPressure = pressDao.getAll();

        lightDao = db.lightDao();
        allIlluminance = lightDao.getAll();

        humDao = db.humDao();;
        allHumidity = humDao.getAll();

        proxDao = db.proxDao();
        allProximity = proxDao.getAll();

        magDao = db.magDao();
        allMagnetic = magDao.getAll();

        gameDao = db.gameDao();
        allGame = gameDao.getAll();
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

    public LiveData<List<ambientTemperature>> getAllAmbTemp(){ return allAmbTemp;}

    public LiveData<List<humidity>> getAllHumidity() {
        return allHumidity;
    }

    public LiveData<List<illuminance>> getAllIlluminance() {
        return allIlluminance;
    }

    public LiveData<List<pressure>> getAllPressure() {
        return allPressure;
    }

    public LiveData<List<gameRotation>> getAllGame(){
        return allGame;
    }

    public LiveData<List<magneticField>> getAllMagnetic(){
        return allMagnetic;
    }

    public LiveData<List<proximity>> getAllProximity(){
        return allProximity;
    }

    public static void insertGame(gameRotation game){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            gameDao.insert(game);
        });
    }

    public static void insertMagnetic(magneticField magnetic){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            magDao.insert(magnetic);
        });
    }

    public static void insertProximity(proximity prox){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            proxDao.insert(prox);
        });
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

    public static void insertAmbTemp(ambientTemperature temperature){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            ambDao.insert(temperature);
        });
    }

    public static void insertIlluminance(illuminance light){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            lightDao.insert(light);
        });
    }

    public static void insertHumidity(humidity hum){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            humDao.insert(hum);
        });
    }

    public static void insertPressure(pressure press){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            pressDao.insert(press);
        });
    }

    public static void clearGame(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            gameDao.deleteAll();
        });
    }

    public static void clearMagnetic(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            magDao.deleteAll();
        });
    }

    public static void clearProximity(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            proxDao.deleteAll();
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
    public static void clearAmbTemp(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            ambDao.deleteAll();
        });
    }

    public static void clearIlluminance(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            lightDao.deleteAll();
        });
    }

    public static void clearPressure(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            pressDao.deleteAll();
        });
    }

    public static void clearHumidity(){
        sensorDatabase.databaseWriteExecutor.execute(() -> {
            humDao.deleteAll();
        });
    }
}
