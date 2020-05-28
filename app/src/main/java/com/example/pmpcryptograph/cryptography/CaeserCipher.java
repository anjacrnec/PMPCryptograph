package com.example.pmpcryptograph.cryptography;

public  class CaeserCipher extends  SubstitiutionCipher{



    public static String encrypt(String plainText, int key)
    {
        char [] pt=plainText.toLowerCase().toCharArray();
        String cipherText="";
        for(int i=0;i<pt.length;i++)
        {
            if(Character.isAlphabetic(pt[i])) {
                int ptIndex = charToIndex(pt[i]);
                int ctIndex = (ptIndex + key) % TOTAL;
                char ctChar = indexToChar(ctIndex);
                cipherText = cipherText + ctChar;
            }
            else
                cipherText = cipherText + pt[i];


        }
        return cipherText;
    }

    public static String decrypt (String cipherText, int key)
    {
        char [] ct=cipherText.toLowerCase().toCharArray();
        String plainText="";
        for(int i=0;i<ct.length;i++)
        {
            if(Character.isAlphabetic(ct[i]))
            {
                int ctIndex=charToIndex(ct[i]);
                int ptIndex=(ctIndex-key)%TOTAL;
                if(ptIndex<0)
                    ptIndex=ptIndex+TOTAL;
                char ptChar=indexToChar(ptIndex);
                plainText=plainText+ptChar;
            }
            else
                plainText=plainText+ct[i];
        }
        return plainText;
    }

}
