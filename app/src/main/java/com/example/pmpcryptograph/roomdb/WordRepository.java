package com.example.pmpcryptograph.roomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application app)
    {
        WordDatabase db=WordDatabase.getInstance(app);
        wordDao=db.wordDao();
        allWords=wordDao.getAllWords();
    }

    public LiveData<List<Word>> getLiveDataWords()
    {
        return allWords;
    }

    public void insert(Word word)
    {
        new InsertWordAsyncTask(wordDao).execute(word);
    }

    /*public void update(Word word)
    {
        new UpdateWordAsyncTask(wordDao).execute(word);
    }

    public void delete(Word word)
    {
        new DeleteWordAsyncTask(wordDao).execute(word);
    }*/

    public int size() throws ExecutionException, InterruptedException {
        return new SizeASyncTask(wordDao).execute().get();
    }

    public List<Word> getListWords() throws ExecutionException, InterruptedException {
        return new GetListWordsASyncTask(wordDao).execute().get();
    }

    public Word getWord(int id) throws ExecutionException, InterruptedException {
        return new GetWordASyncTask(wordDao,id).execute().get();
    }

    public static class SizeASyncTask extends AsyncTask<Void,Void,Integer>
    {
        private WordDao wordDao;
        private SizeASyncTask(WordDao wordDao)
        {
            this.wordDao=wordDao;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            return wordDao.size();
        }
    }

    public static class GetListWordsASyncTask extends AsyncTask<Void,Void,List<Word>>
    {
        private WordDao wordDao;
        private List<Word> produkti;
        private GetListWordsASyncTask(WordDao wordDao)
        {
            this.wordDao=wordDao;
        }
        @Override
        protected List<Word> doInBackground(Void... voids) {
            return wordDao.getListWords();
        }
    }

    public static class GetWordASyncTask extends AsyncTask<Void,Void,Word>
    {
        private WordDao wordDao;
        private int id;
        private GetWordASyncTask(WordDao wordDao,int id)
        {
            this.wordDao=wordDao;
            this.id=id;
        }
        @Override
        protected Word doInBackground(Void... voids) {
            return wordDao.getWord(id);
        }
    }

    private static class InsertWordAsyncTask extends AsyncTask<Word,Void,Void> {

        private WordDao wordDao;

        private InsertWordAsyncTask(WordDao wordDao)
        {
            this.wordDao=wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insert(words[0]);
            return null;
        }
    }

    public void update(Word word)
    {
        new UpdateWordAsyncTask(wordDao).execute(word);
    }

    private static class UpdateWordAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao wordDao;

        private UpdateWordAsyncTask(WordDao wordDao)
        {
            this.wordDao=wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.update(words[0]);
            return null;
        }
    }




}
