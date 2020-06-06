package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.OrthogonalCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;

import rita.RiTa;

public class OrthogonalExercise extends Exercise {

    private OrthogonalCipher orthogonalCipher;

    @Override
    public void generateExercise() {
        this.title=ORTHOGONAL_CIPHER;
        this.orthogonalCipher=generateCipher();
        this.type=generateType();
        this.body=generateBody();
        this.answer=generateAnswer(this.orthogonalCipher);
    }

    @Override
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
    }

    @Override
    public OrthogonalCipher generateCipher() {

        String plainText= RiTa.randomWord();
        int num=Randoms.generateRandomNumber(2,5);
        Integer [] array=Randoms.generateArray(num);
        String key=Randoms.shuffleArray(array);
        OrthogonalCipher orthogonalCipher=new OrthogonalCipher(plainText,key);
        return orthogonalCipher;
    }
}
