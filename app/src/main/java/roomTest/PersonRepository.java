package roomTest;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersonRepository {
    private PersonDAO mPersonDao;
    private LiveData<List<Person>> mAllPerson;

    public PersonRepository(Application application){
        PersonRoomDatabase db = PersonRoomDatabase.getDatabase(application);
        mPersonDao = db.personDao();
        mAllPerson = mPersonDao.getAll();
    }

    public LiveData<List<Person>> getAllPerson(){
        return mAllPerson;
    }

    public void insert(Person pessoa){
        PersonRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.insert(pessoa);
        });
    }

}
