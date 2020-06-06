package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;

import rita.RiTa;

public class VigenereExercise extends Exercise {

    private VigenereCiphere vigenereCiphere;


    @Override
    public void generateExercise() {
        this.title=VIGNERE_CIPHER;
       this.vigenereCiphere=generateCipher();
       this.type=generateType();
       this.body=generateBody();
       this.answer=generateAnswer(this.vigenereCiphere);
    }


    @Override
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
    }

    @Override
    public VigenereCiphere generateCipher() {
        String plainText= RiTa.randomWord();
        String key=RiTa.randomWord();
        VigenereCiphere vigenereCiphere=new VigenereCiphere(plainText,key);
        return vigenereCiphere;
    }
}
