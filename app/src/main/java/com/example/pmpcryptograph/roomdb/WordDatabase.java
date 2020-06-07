package com.example.pmpcryptograph.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import rita.RiTa;

@Database(entities = Word.class,version = 1)
public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase instance;
    private static Context con;
    public abstract WordDao wordDao();

    public static synchronized WordDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"WordsDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        con=context;
        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new initializeWordDBASyncTask(instance).execute();
        }
    };

    private static class initializeWordDBASyncTask extends AsyncTask<Void,Void,Void> {

        private WordDao wordDao;
        private initializeWordDBASyncTask(WordDatabase db)
        {
            wordDao=db.wordDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<200;i++)
            {
                Word w=new Word(RiTa.randomWord());
                wordDao.insert(w);
            }
            return null;
        }
    }
}
