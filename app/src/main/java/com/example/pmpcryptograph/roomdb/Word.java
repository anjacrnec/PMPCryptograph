package com.example.pmpcryptograph.roomdb;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_word")
public class Word {


    @Ignore
    public Word(String word,String example)
    {
        this.word=word;
        this.example=example;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String example;

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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }



}
