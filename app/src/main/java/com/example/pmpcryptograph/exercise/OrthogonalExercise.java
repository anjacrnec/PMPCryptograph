package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.OrthogonalCipher;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

public class OrthogonalExercise extends Exercise {

    private OrthogonalCipher orthogonalCipher;

    @Override
    public void generateExercise(Context con,WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {
        this.con=con;
        Resources res=con.getResources();
        this.title=ORTHOGONAL_CIPHER;
        this.orthogonalCipher=generateCipher(vm,sentanceEnabled);
        this.cipher=this.orthogonalCipher;
        this.type=generateType();
        this.answer=generateAnswer(this.orthogonalCipher);
        this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.trans_ortho_cipher),
                this.orthogonalCipher.getPlainText(),
                this.orthogonalCipher.getCipherText(),
                this.keyStr);
    }

    /*@Override
    public String generateBody() {
        String body;
        OrthogonalCipher oc=this.orthogonalCipher;
        if(this.type==ENCRYPT)
        {
            body="Use the Transpositional orthogonal cipher to encrypt the plain text "+oc.getPlainText()+" with the key  "+oc.getKey();
        }
        else
        {
            body="Use the Transpositional orthogonal cipher to decrypt the cipher text "+oc.getCipherText()+" with the key  "+oc.getKey();
        }

        return body;
    }*/

    @Override
    public String generateKeyStr() {
        return orthogonalCipher.getKey();
    }

    @Override
    public OrthogonalCipher generateCipher(WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {

        String plainText= generateText(vm,sentanceEnabled);
        int num=Randoms.generateRandomNumber(2,5);
        Integer [] array=Randoms.generateArray(num);
        String key=Randoms.shuffleArray(array);
        OrthogonalCipher orthogonalCipher=new OrthogonalCipher(plainText,key);
        return orthogonalCipher;
    }
}
