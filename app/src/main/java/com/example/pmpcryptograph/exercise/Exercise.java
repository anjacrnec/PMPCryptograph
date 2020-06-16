package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.Randoms;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.OrthogonalCipher;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;
import com.example.pmpcryptograph.roomdb.Word;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import rita.RiTa;

public abstract class Exercise {

    public static final String CAESER_CIPHER="Caeser";
    public static final String AFFINE_CIPHER="Affine";
    public static final String VIGNERE_CIPHER="Vigenere";
    public static final String PLAYFAIR_CIPHER="Playfair";
    public static final String ORTHOGONAL_CIPHER="Orthogonal";
    public static final String REVERSE_ORTHOGONAL_CIPHER="ReverseOrthogonal";
    public static final String DIAGONAL_CPIHER="Diagonal";
    protected static final String ENCRYPT="Encrypt";
    protected static final String DECRYPT="Decrypt";
    protected static final String [] TYPE_CIPHER={ENCRYPT,DECRYPT};


    public Cipher getCipher() {
        return cipher;
    }


    protected Context con;

    protected Cipher cipher;

    protected String title;

    protected String type;

    protected String body;

    protected String answer;

    protected String keyStr;

    public String getKeyStr() {
        return keyStr;
    }

    public String getTitle() {
        return title;
    }
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

    public abstract void generateExercise(Context con,WordViewModel vm) throws ExecutionException, InterruptedException;

    public abstract String generateKeyStr();

    public abstract Cipher generateCipher(WordViewModel vm) throws ExecutionException, InterruptedException;

    public String generateBody(Context con,String type,String cipher, String plainText, String cipherText, String key) {
        String body;
        Resources res = con.getResources();
        if (type == ENCRYPT) {

            body = res.getString(R.string.body_pt1,
                    cipher,
                    res.getString(R.string.ek),
                    res.getString(R.string.pt),
                    plainText,
                    key);

        } else {
            body = res.getString(R.string.body_pt1,
                    cipher,
                    res.getString(R.string.dk),
                    res.getString(R.string.ct),
                    cipherText,
                    key);

        }
        return body;
    }


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


    public String generateType()
    {
        if(this.title==DIAGONAL_CPIHER || this.title==ORTHOGONAL_CIPHER || this.title==REVERSE_ORTHOGONAL_CIPHER)
            return ENCRYPT;
        else
        {
            return (String) RiTa.randomItem(TYPE_CIPHER);
        }
    }

    public String generateText(WordViewModel vm) throws ExecutionException, InterruptedException {
        Word word=Randoms.generateRandomWord(vm);
        return word.getWord();
    }


    public static Exercise generateRandomExercise(Context con,WordViewModel vm, boolean caeser, boolean affine, boolean vigenere, boolean playfair, boolean orthoganl, boolean reverseOrthogonal, boolean diagonal) throws ExecutionException, InterruptedException {
        String cipher=chooseCipher(caeser, affine, vigenere, playfair, orthoganl, reverseOrthogonal, diagonal);
        switch (cipher)
        {
            case CAESER_CIPHER:
                CaeserExercise caeserExercise=new CaeserExercise();
                caeserExercise.generateExercise(con,vm);
                return caeserExercise;
            case AFFINE_CIPHER:
                AffineExercise affineExercise=new AffineExercise();
                affineExercise.generateExercise(con,vm);
                return  affineExercise;
            case VIGNERE_CIPHER:
                VigenereExercise vigenereExercise=new VigenereExercise();
                vigenereExercise.generateExercise(con,vm);
                return  vigenereExercise;
            case PLAYFAIR_CIPHER:
                PlayfairExercise playfairExercise=new PlayfairExercise();
                playfairExercise.generateExercise(con,vm);
                return playfairExercise;
            case ORTHOGONAL_CIPHER:
                OrthogonalExercise orthogonalExercise=new OrthogonalExercise();
                orthogonalExercise.generateExercise(con,vm);
                return orthogonalExercise;
            case REVERSE_ORTHOGONAL_CIPHER:
                ReverseOrthogonalExcercise reverseOrthogonalExcercise=new ReverseOrthogonalExcercise();
                reverseOrthogonalExcercise.generateExercise(con,vm);;
                return reverseOrthogonalExcercise;
            case DIAGONAL_CPIHER:
                DiagonalExercise diagonalExercise=new DiagonalExercise();
                diagonalExercise.generateExercise(con,vm);
                return diagonalExercise;

        }

        return null;
    }

}
