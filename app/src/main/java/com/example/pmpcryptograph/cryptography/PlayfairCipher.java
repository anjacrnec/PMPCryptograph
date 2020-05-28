package com.example.pmpcryptograph.cryptography;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PlayfairCipher extends SubstitiutionCipher {




       /* public static String removeRepetableChars(String key)
        {
            char [] keyChar=key.toLowerCase().toCharArray();
            String transformedKey="";
            for(int i=0;i<key.length();i++)
            {
                if(keyChar[i]!=' ') {
                    for (int j = i + 1; j < key.length(); j++) {
                        if (keyChar[i] == keyChar[j])
                            keyChar[j] = ' ';
                    }
                    transformedKey = transformedKey + keyChar[i];
                }

            }
            return transformedKey;
        }*/

    public static String removeRepetableChars(String key)
    {
        key=key.toLowerCase();
        key=key.replaceAll("j","i");
        String transformedKey="";
        for(int i=0;i<key.length();i++)
        {
            if(key.charAt(i)!=' ')
            {
                transformedKey = transformedKey + key.charAt(i);
                String keyCharStr=""+key.charAt(i);
                key= key.replaceAll(keyCharStr, " ");
            }

        }

        return transformedKey;
    }

    public static String removeRepetableCharsAlphabet(String key)
    {
       String alphabet=new String(ALPHABET);
       alphabet=alphabet.replaceAll("j","");
       for(int i=0;i<key.length();i++)
       {
           String keyCharStr=""+key.charAt(i);
           alphabet = alphabet.replaceAll(keyCharStr,"");
       }

       return alphabet;
    }

    public static char [] [] generateMatrix(String key)
        {

         key=removeRepetableChars(key);
         String alphabet=removeRepetableCharsAlphabet(key);
         alphabet=key+alphabet;
         char [][] matrix = new char[5][5];
         int pos=0;

         for(int i=0;i<5;i++)
         {
             for(int j=0;j<5;j++)
             {
                 matrix[i][j]=alphabet.charAt(pos);
                 pos++;
             }
         }

            return matrix;
        }

        public static List<String> textToPairs(String text)
        {
            text=text.replaceAll(" ","");
            text=text.replaceAll("j","i");
            text=text.toLowerCase();

            List <Character> textList=new ArrayList<Character>();
            for(int i=0;i<text.length();i++)
                textList.add(text.charAt(i));

            List<String> textPairs=new ArrayList<String>();

            for(int i=0;i<textList.size();i=i+2)
            {

                if(i==textList.size()-1 || textList.get(i)==textList.get(i+1))
                {
                    textList.add(i+1,'x');

                }
                if(i<textList.size()-1)
                {
                    String pair = textList.get(i).toString() + textList.get(i + 1).toString();
                    textPairs.add(pair);
                }


            }

            return textPairs;
        }

        public static String encrypt(String plainText, String key)
        {
            List <String> ptPairs=textToPairs(plainText);
            char [][] matrix= generateMatrix(key);
            String cipherText="";

            for(int i=0;i<ptPairs.size();i++)
            {
                Log.d("pairs",ptPairs.size()-1+"");
                char[] pair=ptPairs.get(i).toCharArray();

                Char first=new Char(pair[0],-1,-1,false);
                Char second=new Char(pair[1],-1,-1,false);

                Log.d("prv",first.letter+"");
                Log.d("vtor",second.letter+"");
                for(int x=0;x<5;x++)
                {
                    for(int y=0;y<5;y++)
                    {
                        if(!first.found && first.letter==matrix[x][y])
                        {
                            first.posX=x;
                            first.posY=y;
                            first.found=true;
                            Log.d("najden prv",first.letter+"");
                            Log.d("prvX",first.posX+"");
                            Log.d("prvY",first.posY+"");
                        }
                        else if (!second.found && second.letter==matrix[x][y])
                        {
                            second.posX=x;
                            second.posY=y;
                            second.found=true;
                            Log.d("najden vtor",second.letter+"");
                            Log.d("vtorX",second.posX+"");
                            Log.d("vtorY",second.posY+"");
                        }
                        else if (first.found && second.found)
                            break;
                    }

                    if(first.found && second.found)
                        break;
                }

                char ctFirst;
                char ctSecond;
                if(first.posX==second.posX)
                {
                    if(first.posY!=4)
                        ctFirst=matrix[first.posX][first.posY+1];
                    else
                        ctFirst=matrix[first.posX][0];

                    if(second.posY!=4)
                        ctSecond=matrix[second.posX][second.posY+1];
                    else
                        ctSecond=matrix[second.posX][0];
                }
                else if (first.posY==second.posY)
                {
                    if(first.posX!=4)
                        ctFirst=matrix[first.posX+1][first.posY];
                    else
                        ctFirst=matrix[0][first.posY];

                    if(second.posX!=4)
                        ctSecond=matrix[second.posX+1][second.posY];
                    else
                        ctSecond=matrix[0][second.posY];
                }
                else
                {
                    ctSecond=matrix[second.posX][first.posY];
                    ctFirst=matrix[first.posX][second.posY];
                }

                cipherText=cipherText+ctFirst+ctSecond;
            }

            return cipherText;
        }



    public static String decrypt(String cipherText, String key)
    {
        List <String> ptPairs=textToPairs(cipherText);
        char [][] matrix= generateMatrix(key);
        String plainText="";

        for(int i=0;i<ptPairs.size();i++)
        {
            Log.d("pairs",ptPairs.size()-1+"");
            char[] pair=ptPairs.get(i).toCharArray();

            Char first=new Char(pair[0],-1,-1,false);
            Char second=new Char(pair[1],-1,-1,false);

            Log.d("prv",first.letter+"");
            Log.d("vtor",second.letter+"");
            for(int x=0;x<5;x++)
            {
                for(int y=0;y<5;y++)
                {
                    if(!first.found && first.letter==matrix[x][y])
                    {
                        first.posX=x;
                        first.posY=y;
                        first.found=true;
                        Log.d("najden prv",first.letter+"");
                        Log.d("prvX",first.posX+"");
                        Log.d("prvY",first.posY+"");
                    }
                    else if (!second.found && second.letter==matrix[x][y])
                    {
                        second.posX=x;
                        second.posY=y;
                        second.found=true;
                        Log.d("najden vtor",second.letter+"");
                        Log.d("vtorX",second.posX+"");
                        Log.d("vtorY",second.posY+"");
                    }
                    else if (first.found && second.found)
                        break;
                }

                if(first.found && second.found)
                    break;
            }

            char ptFirst;
            char ptSecond;
            if(first.posX==second.posX)
            {
                if(first.posY!=0)
                    ptFirst=matrix[first.posX][first.posY-1];
                else
                    ptFirst=matrix[first.posX][0];

                if(second.posY!=4)
                    ptSecond=matrix[second.posX][second.posY-1];
                else
                    ptSecond=matrix[second.posX][0];
            }
            else if (first.posY==second.posY)
            {
                if(first.posX!=0)
                    ptFirst=matrix[first.posX-1][first.posY];
                else
                    ptFirst=matrix[0][first.posY];

                if(second.posX!=4)
                    ptSecond=matrix[second.posX-1][second.posY];
                else
                    ptSecond=matrix[0][second.posY];
            }
            else
            {
                ptSecond=matrix[second.posX][first.posY];
                ptFirst=matrix[first.posX][second.posY];
            }

            plainText=plainText+ptFirst+ptSecond;
        }

        return plainText;
    }








    public static String matrixToString (char [][]charMatrix)
        {
            String m="";
            for(int i=0;i<5;i++)
            {
                for(int j=0;j<5;j++)
                {
                    m=m+charMatrix[i][j];
                }
                m=m+"\n";
            }

            return m;
        }


        protected static class Char {

            protected char letter;
            protected int posX;
            protected int posY;
            protected boolean found;

            protected Char(char letter, int posX,int posY,boolean found)
            {
                this.letter=letter;
                this.posX=posX;
                this.posY=posY;
                this.found=found;
            }
        }
}
