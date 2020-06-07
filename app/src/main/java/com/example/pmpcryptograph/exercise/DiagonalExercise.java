package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.DiagonalCipher;

import rita.RiTa;

public class DiagonalExercise extends Exercise {

    private DiagonalCipher diagonalCipher;

    @Override
    public void generateExercise() {
        this.title=DIAGONAL_CPIHER;
        this.diagonalCipher=generateCipher();
        this.cipher=this.diagonalCipher;
        this.type=generateType();
        this.body=generateBody();
        this.answer=generateAnswer(this.diagonalCipher);
    }

    @Override
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
    }

    @Override
    public DiagonalCipher generateCipher() {

        boolean isValid=false;
        String plainText=RiTa.randomWord();
        int num = Randoms.generateRandomNumber(2, plainText.length()-1);
        Integer [] array=Randoms.generateArray(num);
        String key=Randoms.shuffleArray(array);
        DiagonalCipher diagonalCipher=new DiagonalCipher(plainText,key);
        return diagonalCipher;
    }
}
