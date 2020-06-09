package com.example.pmpcryptograph.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;
    private LiveData<List<Word>> allWords;


    public WordViewModel(@NonNull Application application) {
        super(application);
        repository=new WordRepository(application);
        allWords=repository.getLiveDataWords();
    }

    public void insert(Word word)
    {
        repository.insert(word);
    }

    public int size () throws ExecutionException, InterruptedException {
        return  repository.size();
    }

    public List<Word> getListWords() throws ExecutionException, InterruptedException {
        return  repository.getListWords();
    }

    public Word getWord(int id) throws ExecutionException, InterruptedException {
        return repository.getWord(id);
    }
}
