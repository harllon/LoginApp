package ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roomTest.Person;
import roomTest.PersonRepository;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository mRepository;
    private final LiveData<List<Person>> mAllPerson;

    public PersonViewModel(Application application){
        super(application);
        mRepository = new PersonRepository(application);
        mAllPerson = mRepository.getAllPerson();
    }

    public LiveData<List<Person>> getAllPerson(){
        return mAllPerson;
    }

    public void insert(Person pessoa){
        mRepository.insert(pessoa);
    }
}
