package com.example.pmpcryptograph.roomdb;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pmpcryptograph.Randoms;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import rita.RiTa;

@Database(entities = Word.class,version = 2)
public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase instance;
    private static Context con;
    public abstract WordDao wordDao();
  //  private static Randoms rand;

    public static synchronized WordDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"WordsDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
          //  rand=new Randoms();
        }

        con=context;
        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            final int[] j = {150};
            WordRequest w=new WordRequest(con);
            for(int i = 0; i< 150; i++) {

                String word=RiTa.randomWord();
                Log.e("Word",word);
                String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
                w.getWord(url, new VolleyCallback() {
                    @Override
                    public void getResponse(JSONArray response) throws JSONException {
                        Log.e("ovde","volley");
                        String example="";
                        example = String.valueOf(response.getJSONObject(0).getJSONArray("meanings").getJSONObject(0).getJSONArray("definitions").getJSONObject(0).get("example"));
                        Log.e("example",example);
                        Word w=new Word(word,example);
                       new initializeWordDBASyncTask(instance).execute(w);
                        //new initializeWordDBASyncTask(instance).execute(new Word(RiTa.randomWord(),"obj"));//
                    }
                });
                new initializeWordDBASyncTask(instance).execute(new Word(RiTa.randomWord(),"obj"));

            }

        }
    };

    private static class initializeWordDBASyncTask extends AsyncTask<Word,Void,Void> {

        private WordDao wordDao;
        private initializeWordDBASyncTask(WordDatabase db)
        {
            wordDao=db.wordDao();


        }
        @Override
        protected Void doInBackground(Word...words) {

          /*  List <Word> list=lists[0];
            for(int i=0;i<list.size();i++)
            {
                wordDao.insert(list.get(i));
               // Word w=new Word(RiTa.randomWord());
                //wordDao.insert(w);
            }*/
            wordDao.insert(words[0]);

            return null;
        }
    }
}
