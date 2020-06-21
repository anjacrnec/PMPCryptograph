package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.DiagonalCipher;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

public class DiagonalExercise extends Exercise {

    private DiagonalCipher diagonalCipher;

    @Override
    public void generateExercise(Context con,WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {
        this.con=con;
        Resources res=con.getResources();
        this.title=DIAGONAL_CPIHER;
        this.diagonalCipher=generateCipher(vm,sentanceEnabled);
        this.cipher=this.diagonalCipher;
        this.type=generateType();
        this.answer=generateAnswer(this.diagonalCipher);
        this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.trans_diagonal_cipher),
                this.diagonalCipher.getPlainText(),
                this.diagonalCipher.getCipherText(),
                this.keyStr);
    }

    /*@Override
    public String generateBody() {
        String body;
        DiagonalCipher dc=this.diagonalCipher;
        if(this.type==ENCRYPT)
        {
            body="Use the Transpositional diagonal cipher to encrypt the plain text "+dc.getPlainText()+" with the key  "+dc.getKey();
        }
        else
        {
            body="Use the Transpositional diagonal cipher to decrypt the cipher text "+dc.getCipherText()+" with the key  "+dc.getKey();
        }

        return body;
    }*/

    @Override
    public String generateKeyStr() {
        return diagonalCipher.getKey();
    }

    @Override
    public DiagonalCipher generateCipher(WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {

        boolean isValid=false;
        String plainText=generateText(vm,sentanceEnabled);
        int max;
        if(plainText.length()-1<10)
            max=plainText.length()-1;
        else
            max=9;
        int num = Randoms.generateRandomNumber(2,max);
        Integer [] array=Randoms.generateArray(num);
        String key=Randoms.shuffleArray(array);
        DiagonalCipher diagonalCipher=new DiagonalCipher(plainText,key);
        return diagonalCipher;
    }
}
