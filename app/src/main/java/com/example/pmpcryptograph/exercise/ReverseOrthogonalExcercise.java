package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;

public class ReverseOrthogonalExcercise extends Exercise {

    private ReverseOrthogonalCipher reverseOrthogonalCipher;

    @Override
    public void generateExercise(Context con,WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {
        this.con=con;
        Resources res=con.getResources();
        this.title=REVERSE_ORTHOGONAL_CIPHER;
        this.reverseOrthogonalCipher=generateCipher(vm,sentanceEnabled);
        this.cipher=this.reverseOrthogonalCipher;
        this.type=generateType();
        this.answer=generateAnswer(this.reverseOrthogonalCipher);
        this.keyStr=generateKeyStr();
        this.body=generateBody(this.con,
                this.type,
                res.getString(R.string.trans_ortho_boustrophedon_cipher),
                this.reverseOrthogonalCipher.getPlainText(),
                this.reverseOrthogonalCipher.getCipherText(),
                this.keyStr);

    }

  /*  @Override
    public String generateBody() {
        String body;
        ReverseOrthogonalCipher rc=this.reverseOrthogonalCipher;
        if(this.type==ENCRYPT)
        {
            body="Use the Transpositional orthogonal boustrophedon cipher to encrypt the plain text "+rc.getPlainText()+" with the key  "+rc.getKey();
        }
        else
        {
            body="Use the Transpositional orthogonal boustrophedon cipher to decrypt the cipher text "+rc.getCipherText()+" with the key  "+rc.getKey();
        }

        return body;
    }*/

    @Override
    public String generateKeyStr() {
        return reverseOrthogonalCipher.getKey();
    }

    @Override
    public ReverseOrthogonalCipher generateCipher(WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException {

        String plainText= generateText(vm,sentanceEnabled);
        int num= Randoms.generateRandomNumber(2,5);
        Integer [] array=Randoms.generateArray(num);
        String key=Randoms.shuffleArray(array);
        ReverseOrthogonalCipher reverseOrthogonalCipher=new ReverseOrthogonalCipher(plainText,key);
        return reverseOrthogonalCipher;
    }
}
