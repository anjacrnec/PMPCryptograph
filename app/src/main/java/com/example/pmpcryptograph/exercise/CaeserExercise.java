package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

import rita.RiTa;

public class CaeserExercise extends Exercise {

    private CaeserCipher caeserCipher;


    public CaeserExercise()
    {

    }

    @Override
    public void generateExercise(Context con,WordViewModel vm) throws ExecutionException, InterruptedException {

        this.con=con;
        Resources res=con.getResources();
        this.title=CAESER_CIPHER;
        this.caeserCipher=generateCipher(vm);
        this.cipher=this.caeserCipher;
        this.type=generateType();
        this.answer=generateAnswer(this.caeserCipher);
        this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.caeser_encryption),
                this.caeserCipher.getPlainText(),
                this.caeserCipher.getCipherText(),
                this.keyStr);
    }

    @Override
    public String generateKeyStr()
    {
        return String.valueOf(this.caeserCipher.getKey());
    }


    @Override
    public CaeserCipher generateCipher(WordViewModel vm) throws ExecutionException, InterruptedException {
        String plainText= generateText(vm);
        int key= Randoms.generateRandomNumber(1,25);
        CaeserCipher caeserCipher=new CaeserCipher(plainText,key);
        return  caeserCipher;
    }

}
