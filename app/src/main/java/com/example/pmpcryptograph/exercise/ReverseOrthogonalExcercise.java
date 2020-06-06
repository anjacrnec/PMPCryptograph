package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;

import rita.RiTa;

public class ReverseOrthogonalExcercise extends Exercise {

    private ReverseOrthogonalCipher reverseOrthogonalCipher;

    @Override
    public void generateExercise() {
        this.title=REVERSE_ORTHOGONAL_CIPHER;
        this.reverseOrthogonalCipher=generateCipher();
        this.type=generateType();
        this.body=generateBody();
        this.answer=generateAnswer(this.reverseOrthogonalCipher);
    }

    @Override
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
    }

    @Override
    public ReverseOrthogonalCipher generateCipher() {

        String plainText= RiTa.randomWord();
        int num= Randoms.generateRandomNumber(2,5);
        Integer [] array=Randoms.generateArray(num);
        String key=Randoms.shuffleArray(array);
        ReverseOrthogonalCipher reverseOrthogonalCipher=new ReverseOrthogonalCipher(plainText,key);
        return reverseOrthogonalCipher;
    }
}
