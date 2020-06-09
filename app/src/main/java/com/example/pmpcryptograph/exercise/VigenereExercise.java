package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

import rita.RiTa;

public class VigenereExercise extends Exercise {

    private VigenereCiphere vigenereCiphere;


    @Override
    public void generateExercise(Context con,WordViewModel vm) throws ExecutionException, InterruptedException {
        this.con=con;
        Resources res=con.getResources();
        this.title=VIGNERE_CIPHER;
       this.vigenereCiphere=generateCipher(vm);
       this.cipher=this.vigenereCiphere;
       this.type=generateType();
       this.answer=generateAnswer(this.vigenereCiphere);
       this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.vignere_cipher),
                this.vigenereCiphere.getPlainText(),
                this.vigenereCiphere.getCipherText(),
                this.keyStr);
    }


    /*@Override
    public String generateBody() {

        String body;
        VigenereCiphere vc=this.vigenereCiphere;
        if(this.type==ENCRYPT)
        {
            body="Use the Vigenere cipher to encrypt the plain text "+vc.getPlainText()+" with the key  "+vc.getKey();
        }
        else
        {
            body="Use the Vigenere cipher to decrypt the cipher text "+vc.getCipherText()+" with the key  "+vc.getKey();
        }

        return body;
    }*/

    @Override
    public String generateKeyStr() {
        return vigenereCiphere.getKey();
    }

    @Override
    public VigenereCiphere generateCipher(WordViewModel vm) throws ExecutionException, InterruptedException {
        String plainText= generateText(vm);
        String key=generateText(vm);
        VigenereCiphere vigenereCiphere=new VigenereCiphere(plainText,key);
        return vigenereCiphere;
    }
}
