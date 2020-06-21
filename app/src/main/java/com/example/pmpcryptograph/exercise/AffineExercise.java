package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

public class AffineExercise extends Exercise {

    private AffineCipher affineCipher;


    @Override
    public void generateExercise(Context con,WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {
        this.con=con;
        Resources res=con.getResources();
        this.title=AFFINE_CIPHER;
        this.affineCipher=generateCipher(vm,sentanceEnabled);
        this.cipher=this.affineCipher;
        this.type=generateType();
        this.answer=generateAnswer(this.affineCipher);
        this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.affine_cipher),
                this.affineCipher.getPlainText(),
                this.affineCipher.getCipherText(),
                this.keyStr);
    }




   /* @Override
    public String generateBody()
    {
        String body;

        if(this.type==ENCRYPT)
        {
            body="Use the Affine cipher to encrypt the word "+this.affineCipher.getPlainText()+" with the key A "+this.affineCipher.getKeyA()+" and the key B "+this.affineCipher.getKeyB();
        }
        else
        {
            body="Use the Affine mehtod to decrypt the word "+this.affineCipher.getCipherText()+" with the key A "+this.affineCipher.getKeyA()+" and the key B "+this.affineCipher.getKeyB();
        }

        return body;
    }*/

    @Override
    public String generateKeyStr() {
        String key="A = "+this.affineCipher.getKeyA()+" B = "+this.affineCipher.getKeyB();
        return key;
    }

    @Override
    public AffineCipher generateCipher(WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {
        String plainText= generateText(vm,sentanceEnabled);
        int keyA=4;
        while(!AffineCipher.isKeyAValid(keyA))
        {
            keyA= Randoms.generateRandomNumber(1,100);
        }
        int keyB=Randoms.generateRandomNumber(1,100);

        return new AffineCipher(plainText,keyA,keyB);
    }

}
