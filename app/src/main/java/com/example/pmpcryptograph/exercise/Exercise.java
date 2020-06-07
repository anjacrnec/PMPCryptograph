package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.OrthogonalCipher;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rita.RiTa;

public abstract class Exercise {

    protected static final String CAESER_CIPHER="Caeser";
    protected static final String AFFINE_CIPHER="Affine";
    protected static final String VIGNERE_CIPHER="Vigenere";
    protected static final String PLAYFAIR_CIPHER="Playfair";
    protected static final String ORTHOGONAL_CIPHER="Orthogonal";
    protected static final String REVERSE_ORTHOGONAL_CIPHER="ReverseOrthogonal";
    protected static final String DIAGONAL_CPIHER="Diagonal";
    protected static final String ENCRYPT="Encrypt";
    protected static final String DECRYPT="Decrypt";
    protected static final String [] TYPE_CIPHER={ENCRYPT,DECRYPT};


    public Cipher getCipher() {
        return cipher;
    }

    protected Cipher cipher;
    protected String title;
    protected String type;
    protected String body;
    protected String answer;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String generateAnswer(Cipher cipher)
    {
        if(this.type==ENCRYPT)
        {
            answer=cipher.getCipherText();
        }
        else
        {
            answer=cipher.getPlainText();
        }
        return answer;
    }

    public abstract void generateExercise();

    public abstract String generateBody();

    public abstract Cipher generateCipher();


    public static String chooseCipher (boolean caeser, boolean affine, boolean vigenere, boolean playfair, boolean orthoganl, boolean reverseOrthogonal, boolean diagonal)
    {
        HashMap<String,Boolean> allCiphers=new HashMap<>();
        allCiphers.put(CAESER_CIPHER,caeser);
        allCiphers.put(AFFINE_CIPHER,affine);
        allCiphers.put(VIGNERE_CIPHER,vigenere);
        allCiphers.put(PLAYFAIR_CIPHER,playfair);
        allCiphers.put(ORTHOGONAL_CIPHER,orthoganl);
        allCiphers.put(REVERSE_ORTHOGONAL_CIPHER,reverseOrthogonal);
        allCiphers.put(DIAGONAL_CPIHER,diagonal);

        List<String> enabledCiphers=new ArrayList<>();
        Iterator it=allCiphers.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry cipher=(Map.Entry)it.next();
            if((Boolean)cipher.getValue())
                enabledCiphers.add(cipher.getKey().toString());
        }

        String chosenCipher=(String) RiTa.randomItem(enabledCiphers);
        return chosenCipher;
    }

    public String generateType()
    {
        if(this.title==DIAGONAL_CPIHER || this.title==ORTHOGONAL_CIPHER || this.title==REVERSE_ORTHOGONAL_CIPHER)
            return ENCRYPT;
        else
        {
            return (String) RiTa.randomItem(TYPE_CIPHER);
        }
    }


    public static Exercise generateRandomExercise(boolean caeser, boolean affine, boolean vigenere, boolean playfair, boolean orthoganl, boolean reverseOrthogonal, boolean diagonal)
    {
        String cipher=chooseCipher(caeser, affine, vigenere, playfair, orthoganl, reverseOrthogonal, diagonal);
        switch (cipher)
        {
            case CAESER_CIPHER:
                CaeserExercise caeserExercise=new CaeserExercise();
                caeserExercise.generateExercise();
                return caeserExercise;
            case AFFINE_CIPHER:
                AffineExercise affineExercise=new AffineExercise();
                affineExercise.generateExercise();
                return  affineExercise;
            case VIGNERE_CIPHER:
                VigenereExercise vigenereExercise=new VigenereExercise();
                vigenereExercise.generateExercise();
                return  vigenereExercise;
            case PLAYFAIR_CIPHER:
                PlayfairExercise playfairExercise=new PlayfairExercise();
                playfairExercise.generateExercise();
                return playfairExercise;
            case ORTHOGONAL_CIPHER:
                OrthogonalExercise orthogonalExercise=new OrthogonalExercise();
                orthogonalExercise.generateExercise();
                return orthogonalExercise;
            case REVERSE_ORTHOGONAL_CIPHER:
                ReverseOrthogonalExcercise reverseOrthogonalExcercise=new ReverseOrthogonalExcercise();
                reverseOrthogonalExcercise.generateExercise();;
                return reverseOrthogonalExcercise;
            case DIAGONAL_CPIHER:
                DiagonalExercise diagonalExercise=new DiagonalExercise();
                diagonalExercise.generateExercise();
                return diagonalExercise;

        }

        return null;
    }

}
