package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.exercise.Exercise;

import rita.RiTa;

public class AffineExercise extends Exercise {

    private AffineCipher affineCipher;


    @Override
    public void generateExercise() {
        this.title=AFFINE_CIPHER;
        this.affineCipher=generateCipher();
        this.type=generateType();
        this.body=generateBody();
        this.answer=generateAnswer(this.affineCipher);
    }




    @Override
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
    }

    @Override
    public AffineCipher generateCipher()
    {
        String plainText= RiTa.randomWord();
        int keyA=4;
        while(!AffineCipher.isKeyAValid(keyA))
        {
            keyA= Randoms.generateRandomNumber(1,100);
        }
        int keyB=Randoms.generateRandomNumber(1,100);

        return new AffineCipher(plainText,keyA,keyB);
    }

}
