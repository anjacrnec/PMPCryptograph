package com.example.pmpcryptograph.exercise;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.Cipher;
import com.example.pmpcryptograph.roomdb.Word;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import rita.RiTa;

public class Exercise  implements Parcelable {

    public static final String CAESER_CIPHER="Caeser";
    public static final String AFFINE_CIPHER="Affine";
    public static final String VIGNERE_CIPHER="Vigenere";
    public static final String PLAYFAIR_CIPHER="Playfair";
    public static final String ORTHOGONAL_CIPHER="Orthogonal";
    public static final String REVERSE_ORTHOGONAL_CIPHER="ReverseOrthogonal";
    public static final String DIAGONAL_CPIHER="Diagonal";
    public static final String ENCRYPT="Encrypt";
    public static final String DECRYPT="Decrypt";
    protected static final String [] TYPE_CIPHER={ENCRYPT,DECRYPT};


    Exercise()
    {

    }
    public Cipher getCipher() {
        return cipher;
    }


    protected Context con;

    protected Cipher cipher;


    public void setTitle(String title) {
        this.title = title;
    }

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

    public  void generateExercise(Context con,WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException
    {

    }

    public  String generateKeyStr()
    {
       return  null;
    }

    public Cipher generateCipher(WordViewModel vm,boolean sentanceEnabled) throws ExecutionException, InterruptedException
    {
        return null;
    }

    public String generateBody(Context con,String type,String cipher, String plainText, String cipherText, String key) {
        String body;
        Resources res = con.getResources();
        if (type == ENCRYPT) {

            body = res.getString(R.string.body_pt,
                    cipher,
                    res.getString(R.string.ek),
                    res.getString(R.string.pt),
                    plainText,
                    key);

        } else {
            body = res.getString(R.string.body_pt,
                    cipher,
                    res.getString(R.string.dk),
                    res.getString(R.string.ct),
                    cipherText,
                    key);

        }
        Log.d("str pr",res.getString(R.string.unsave_exercise));
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

    public String generateText(WordViewModel vm,Boolean sentence) throws ExecutionException, InterruptedException {
        if(sentence)
        {
            String txt=generateSentence(vm);
            return txt;
        }
        else
        {
            String word=generateWord(vm);
            return  word;
        }
    }

    public String generateSentence(WordViewModel vm) throws ExecutionException, InterruptedException {
        Word word=Randoms.generateRandomWord(vm);
        while(word.getExample().isEmpty())
        {
            word=Randoms.generateRandomWord(vm);

        }
        String example=word.getExample();
        example=example.replaceAll("[^A-Za-z ]", "");
        return example;
    }

    public String generateWord(WordViewModel vm) throws ExecutionException, InterruptedException {
        Word word=Randoms.generateRandomWord(vm);
        String w=word.getWord();
        w=w.replace("[^A-Za-z ]", "");
        return w;
    }



    public static Exercise generateRandomExercise(Context con,WordViewModel vm, boolean caeser, boolean affine, boolean vigenere, boolean playfair, boolean orthoganl, boolean reverseOrthogonal, boolean diagonal, boolean sentenceEnabled) throws ExecutionException, InterruptedException {
        String cipher=chooseCipher(caeser, affine, vigenere, playfair, orthoganl, reverseOrthogonal, diagonal);
        switch (cipher)
        {
            case CAESER_CIPHER:
                CaeserExercise caeserExercise=new CaeserExercise();
                caeserExercise.generateExercise(con,vm,sentenceEnabled);
                return caeserExercise;
            case AFFINE_CIPHER:
                AffineExercise affineExercise=new AffineExercise();
                affineExercise.generateExercise(con,vm,sentenceEnabled);
                return  affineExercise;
            case VIGNERE_CIPHER:
                VigenereExercise vigenereExercise=new VigenereExercise();
                vigenereExercise.generateExercise(con,vm,sentenceEnabled);
                return  vigenereExercise;
            case PLAYFAIR_CIPHER:
                PlayfairExercise playfairExercise=new PlayfairExercise();
                playfairExercise.generateExercise(con,vm,sentenceEnabled);
                return playfairExercise;
            case ORTHOGONAL_CIPHER:
                OrthogonalExercise orthogonalExercise=new OrthogonalExercise();
                orthogonalExercise.generateExercise(con,vm,sentenceEnabled);
                return orthogonalExercise;
            case REVERSE_ORTHOGONAL_CIPHER:
                ReverseOrthogonalExcercise reverseOrthogonalExcercise=new ReverseOrthogonalExcercise();
                reverseOrthogonalExcercise.generateExercise(con,vm,sentenceEnabled);;
                return reverseOrthogonalExcercise;
            case DIAGONAL_CPIHER:
                DiagonalExercise diagonalExercise=new DiagonalExercise();
                diagonalExercise.generateExercise(con,vm,sentenceEnabled);
                return diagonalExercise;

        }

        return null;
    }
    private int mData;

    public int describeContents() {
        return 0;
    }

    /** save object in parcel */
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<Exercise> CREATOR
            = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    /** recreate object from parcel */
    private Exercise(Parcel in) {
        mData = in.readInt();
    }

}
