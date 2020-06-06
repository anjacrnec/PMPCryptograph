package com.example.pmpcryptograph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Randoms {

    public static int generateRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int generateRandomNumber() {
        Random r = new Random();
        return r.nextInt();
    }

    public static Integer [] generateArray(int num)
    {
        Integer [] array=new Integer[num];
        for(int i=0;i<num;i++)
            array[i]=i+1;

        return array;
    }

    public static String shuffleArray(Integer [] array)
    {
        List<Integer> list= Arrays.asList(array);
        Collections.shuffle(list);
        list.toArray(array);
        String shuffled=Arrays.toString(array);
        shuffled=shuffled.replace(" ","");
        shuffled=shuffled.replace(",","");
        shuffled=shuffled.replace("[","");
        shuffled=shuffled.replace("]","");
        return shuffled;
    }
}
