package com.example.pmpcryptograph;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rita.RiTa;

public class ExerciseGeneration {

    private static final String CAESER_CIPHER="Caeser";
    private static final String AFFINE_CIPHER="Affine";
    private static final String VIGNERE_CIPHER="Vigenere";
    private static final String PLAYFAIR_CIPHER="Playfair";
    private static final String ORTHOGONAL_CIPHER="Orthogonal";
    private static final String REVERSE_ORTHOGONAL_CIPHER="ReverseOrthogonal";
    private static final String DIAGONAL_CPIHER="Diagonal";
    private static final String ENCRYPT="Encrypt";
    private static final String DECRYPT="Decrypt";
    private static final String [] TYPE_CIPHER={ENCRYPT,DECRYPT};


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

        List <String> enabledCiphers=new ArrayList<>();
        Iterator it=allCiphers.entrySet().iterator();
        while (it.hasNext())
        {
           Map.Entry cipher=(Map.Entry)it.next();
           if((Boolean)cipher.getValue())
               enabledCiphers.add(cipher.getKey().toString());
        }

        String chosenCipher=(String)RiTa.randomItem(enabledCiphers);
        return chosenCipher;
    }

    public static String chooseCipherType(String chosenCipher)
    {
        if(chosenCipher==DIAGONAL_CPIHER)
            return ENCRYPT;
        else
        {
            return (String) RiTa.randomItem(TYPE_CIPHER);
        }
    }

    public String generateText()
    {
        String text=RiTa.randomWord();
        return text;
    }

    public static Cipher generateCipher(String cipher, String type)
    {
        switch (cipher)
        {
            case CAESER_CIPHER:
                return CaeserCipher.generateCipher();


        }

        return null;
    }



}
