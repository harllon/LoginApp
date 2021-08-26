package roomTest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import Utils.passwordHash;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class PersonRoomDatabase extends RoomDatabase {
    public abstract PersonDAO personDao();

    private static volatile PersonRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback prePop = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PersonDAO pessoa = INSTANCE.personDao();
                pessoa.deleteAll();
                String password = passwordHash.getMd5("flamengo");
                Person firstLogin = new Person("harllon", "harllon.paz@gmail.com", password, "Harllon Paz", true);
                pessoa.insert(firstLogin);
            });
        }
    };

    static PersonRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (PersonRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PersonRoomDatabase.class, "login_database").addCallback(prePop).build();
                }
            }
        }
        return INSTANCE;
    }
}
