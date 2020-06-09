package com.example.pmpcryptograph.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query ("SELECT COUNT(*) from table_word")
    int size();

    @Query("SELECT * FROM table_word")
    List<Word> getListWords();

    @Query("SELECT * FROM table_word")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM table_word WHERE id=:id")
    Word getWord(int id);


}
