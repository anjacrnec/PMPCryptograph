package com.example.pmpcryptograph.cryptography;

public class VigenereCiphere extends SubstitiutionCipher {

    public static String encrypt (String plainText, String key)
    {
        char [] pt=plainText.toLowerCase().toCharArray();
        char [] keyChar=key.toLowerCase().toCharArray();
        String cipherText="";
        int j=0;
        for(int i=0;i<pt.length;i++)
        {
            if(Character.isAlphabetic(pt[i]))
            {
                if(j==keyChar.length)
                    j=0;

                int ptIndex=charToIndex(pt[i]);
                int keyIndex=charToIndex(keyChar[j]);
                int ctIndex=(ptIndex+keyIndex)%TOTAL;
                char ctChar=indexToChar(ctIndex);
                cipherText=cipherText+ctChar;
                j++;

            }
            else
                cipherText=cipherText+pt[i];
        }

        return cipherText;
    }

    public static String decrypt (String cipherText, String key)
    {

        char [] ct=cipherText.toLowerCase().toCharArray();
        char [] keyChar=key.toLowerCase().toCharArray();
        String plainText="";
        int j=0;
        for(int i=0;i<ct.length;i++)
        {
            if(Character.isAlphabetic(ct[i]))
            {
                if(j==keyChar.length)
                    j=0;

                int ctIndex=charToIndex(ct[i]);
                int keyIndex=charToIndex(keyChar[j]);
                int ptIndex=(ctIndex-keyIndex)%TOTAL;
                if(ptIndex<0)
                   ptIndex=ptIndex+TOTAL;
                char ptChar=indexToChar(ptIndex);
                plainText=plainText+ptChar;
                j++;
            }
            else
                plainText=plainText+ct[i];
        }

        return plainText;
    }
}
