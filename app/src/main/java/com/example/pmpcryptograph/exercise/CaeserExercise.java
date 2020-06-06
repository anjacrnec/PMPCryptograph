package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.exercise.Exercise;

import rita.RiTa;

public class CaeserExercise extends Exercise {

    private CaeserCipher caeserCipher;

    public CaeserExercise()
    {

    }

    @Override
    public void generateExercise()
    {
        this.title=CAESER_CIPHER;
        this.caeserCipher=generateCipher();
        this.type=generateType();
        this.body=generateBody();
        this.answer=generateAnswer(this.caeserCipher);
    }


    @Override
    public String generateBody() {
        String body;
        if(this.type=="Encrypt")
        {
            body="Use the Caeser mehtod to encrypt the word"+this.caeserCipher.getPlainText()+" with the key "+this.caeserCipher.getKey();
        }
        else
        {
            body="Use the Caeser mehtod to decrypt the word"+this.caeserCipher.getCipherText()+" with the key "+this.caeserCipher.getKey();
        }

        return body;
    }

    @Override
    public CaeserCipher generateCipher()
    {
        String plainText= RiTa.randomWord();
        int key= Randoms.generateRandomNumber(1,25);
        CaeserCipher caeserCipher=new CaeserCipher(plainText,key);
        return  caeserCipher;
    }

}
