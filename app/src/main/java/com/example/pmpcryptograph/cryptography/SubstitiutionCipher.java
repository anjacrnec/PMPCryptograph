package com.example.pmpcryptograph.cryptography;

public abstract class SubstitiutionCipher {

    public static final char[] ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public  static final int TOTAL=26;

    public static int charToIndex(char c)
    {
        Boolean findChar=false;
        int posTemp=0;
        int index=-1;
        while(!findChar & posTemp<26)
        {
            if(c==ALPHABET[posTemp]) {
                findChar = true;
                index=posTemp;
            }
            posTemp++;
        }
        return index;
    }


    public static char indexToChar(int index)
    {
        return ALPHABET[index];
    }


}
