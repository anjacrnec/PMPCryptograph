package com.example.pmpcryptograph.exercise;

import com.example.pmpcryptograph.roomdb.Word;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Randoms {


    public static int generateRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
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

    public static Word generateRandomWord(WordViewModel vm) throws ExecutionException, InterruptedException {
       int size=vm.size();
      int id= Randoms.generateRandomNumber(1,size);
        Word word= vm.getWord(id);
        return word;
    }



}
