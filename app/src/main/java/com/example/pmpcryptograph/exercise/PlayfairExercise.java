package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

import rita.RiTa;

public class PlayfairExercise  extends Exercise{

    private PlayfairCipher playfairCipher;

    @Override
    public void generateExercise(Context con,WordViewModel vm) throws ExecutionException, InterruptedException {
        this.con=con;
        Resources res=con.getResources();
        this.title=PLAYFAIR_CIPHER;
        this.playfairCipher=generateCipher(vm);
        this.cipher=this.playfairCipher;
        this.type=generateType();
        this.answer=generateAnswer(this.playfairCipher);
        this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.playfair_cipher),
                this.playfairCipher.getPlainText(),
                this.playfairCipher.getCipherText(),
                this.keyStr);
    }



    /*@Override
    public String generateBody() {
        String body;
        PlayfairCipher pc=this.playfairCipher;
        if(this.type=="Encrypt")
        {
            body="Use the Playfair cipher to encrypt the word"+pc.getPlainText()+" with the key "+pc.getKey();
        }
        else
        {
            body="Use the Caeser mehtod to decrypt the word"+pc.getCipherText()+" with the key "+pc.getKey();
        }

        return body;
    }*/

    @Override
    public String generateKeyStr() {
        return  playfairCipher.getKey();
    }

    @Override
    public PlayfairCipher generateCipher(WordViewModel vm) throws ExecutionException, InterruptedException {
        String plainText= generateText(vm);
        String key=generateText(vm);
        PlayfairCipher playfairCipher=new PlayfairCipher(plainText,key);
        return playfairCipher;
    }
}
