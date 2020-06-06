package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;

import rita.RiTa;

public class PlayfairExercise  extends Exercise{

    private PlayfairCipher playfairCipher;

    @Override
    public void generateExercise() {
        this.title=PLAYFAIR_CIPHER;
        this.playfairCipher=generateCipher();
        this.type=generateType();
        this.body=generateBody();
        this.answer=generateAnswer(this.playfairCipher);
    }



    @Override
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
    }

    @Override
    public PlayfairCipher generateCipher() {
        String plainText= RiTa.randomWord();
        String key=RiTa.randomWord();
        PlayfairCipher playfairCipher=new PlayfairCipher(plainText,key);
        return playfairCipher;
    }
}
