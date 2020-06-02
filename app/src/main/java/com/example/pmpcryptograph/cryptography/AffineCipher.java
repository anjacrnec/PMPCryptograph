package com.example.pmpcryptograph.cryptography;

//TO DO - implement method that checks if inverse of key is possible
public class AffineCipher extends SubstitiutionCipher {


    //affine cipher encryption method
    // e(x)=ax+b mod 26
    public static String encrypt(String plainText,int keyA,int keyB)
    {
        char [] pt=plainText.toLowerCase().toCharArray();
        String cipherText="";

        for(int i=0;i<pt.length;i++)
        {
            if(Character.isAlphabetic(pt[i]))
            {
                int ptIndex = charToIndex(pt[i]);
                int ctIndex= (keyA*ptIndex+keyB)%TOTAL;
                char ctChar=indexToChar(ctIndex);
                cipherText = cipherText + ctChar;
            }
            else
                cipherText = cipherText + pt[i];
        }
        return cipherText;
    }

    //affine cipher decryption method
    // d(y)=a^(-1)(y-b) mod 26
    public static String decrypt (String cipherText, int keyA, int keyB)
    {
        char [] ct=cipherText.toLowerCase().toCharArray();
        String plainText="";

        for(int i=0;i<ct.length;i++)
        {
            if(Character.isAlphabetic(ct[i]))
            {
                int ctIndex=charToIndex(ct[i]);
                int inv=inverse(keyA);
                int ptIndex=(inv*(ctIndex-keyB))%TOTAL;
                while(ptIndex<0)
                    ptIndex=ptIndex+TOTAL;
                char ptChar=indexToChar(ptIndex);
                plainText=plainText+ptChar;
            }
            else
                plainText=plainText+ct[i];
        }

        return plainText;
    }

    //find the inverse number of keyA
    public  static int inverse(int keyA)
    {
        int inv=-1,remainder;
        for(int i=0;i<25;i++) {
            remainder = (keyA *i) % TOTAL;
            if (remainder == 1) {
                inv=i;
                break;
            }
        }

        return  inv;
    }

  /*  public static Boolean findGCD(int num1,int num2)
    {
        int i;
        int j;
        if(num1>26) {
            i = num1;
            j = 26;
        }
        else
        {
            i=16;
            j=num2;
        }
        if(j == 0)
        {
            return i;
        }
        return findGCD(j, i%j);
    }*/


}
