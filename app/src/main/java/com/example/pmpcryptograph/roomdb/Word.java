package com.example.pmpcryptograph.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_word")
public class Word {



    @PrimaryKey(autoGenerate = true)
    private int id;


    private String word;


    public Word(String word)
    {
        this.word=word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }



}
