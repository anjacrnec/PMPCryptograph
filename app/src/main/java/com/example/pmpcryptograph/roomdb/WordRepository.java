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

    public LiveData<List<Word>> getLiveDataWords()
    {
        return allWords;
    }

    public List<Word> getListWords() throws ExecutionException, InterruptedException {
        return new GetListWordsASyncTask(wordDao).execute().get();
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


  /*  private static class UpdateProduktAsyncTask extends AsyncTask<Produkt,Void,Void>{

        private ProduktDao produktDao;

        private UpdateProduktAsyncTask(ProduktDao produktDao)
        {
            this.produktDao=produktDao;
        }
        @Override
        protected Void doInBackground(Produkt... produkts) {
            produktDao.update(produkts[0]);
            return null;
        }
    }

    private static class DeleteProduktAsyncTask extends AsyncTask<Produkt,Void,Void>{

        private ProduktDao produktDao;

        private DeleteProduktAsyncTask(ProduktDao produktDao)
        {
            this.produktDao=produktDao;
        }
        @Override
        protected Void doInBackground(Produkt... produkts) {
            produktDao.delete(produkts[0]);
            return null;
        }
    }*/


}
