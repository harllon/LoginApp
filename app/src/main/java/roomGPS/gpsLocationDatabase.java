package roomGPS;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Utils.passwordHash;
import roomTest.Person;
import roomTest.PersonDAO;
import roomTest.PersonRoomDatabase;


@Database(entities = {gpsLocation.class}, version = 1, exportSchema = false)
public abstract class gpsLocationDatabase extends RoomDatabase {
    public abstract gpsLocationDao gpsDao();

    private static volatile gpsLocationDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback cleanDb = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                gpsLocationDao gpsDao = INSTANCE.gpsDao();
                gpsDao.deleteAll();
            });
        }
    };

    static gpsLocationDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (gpsLocationDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), gpsLocationDatabase.class, "gps_database").addCallback(cleanDb).build();
                }
            }
        }
        return INSTANCE;
    }

}
