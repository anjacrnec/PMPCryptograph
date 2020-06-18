package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class CryptographerFragment extends Fragment {


    public static final String TAG_CAESER_FRAGMENT="caeser fr";
    public static final String TAG_AFFINE_FRAGMENT="affine fr";
    public static final String TAG_VIGENERE_FRAGMENT="vigenere fr";
    public static final String TAG_PLAYFAIR_FRAGMENT="playfair fr";
    public static final String TAG_TRANSPOSITIONAL_FRAGMENT="trans fr";

    CaeserFragment caeserFragment;
    public CryptographerFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_cryptographer, container, false);


        FragmentManager fm=getChildFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        if(savedInstanceState==null) {
            caeserFragment = new CaeserFragment();
            ft.replace(R.id.caeserFragmentContainer,caeserFragment,TAG_CAESER_FRAGMENT);

            AffineFragment affineFragment=new AffineFragment();
            ft.replace(R.id.affineFragmentContainer,affineFragment,TAG_AFFINE_FRAGMENT);

            VigenereFragment vigenereFragment=new VigenereFragment();
            ft.replace(R.id.vigenereFragmentContainer,vigenereFragment,TAG_VIGENERE_FRAGMENT);

            PlayfairFragment playfairFragment=new PlayfairFragment();
            ft.replace(R.id.playfairFragmentContainer,playfairFragment,TAG_PLAYFAIR_FRAGMENT);

            TranspositionalFragment transpositionalFragment=new TranspositionalFragment();
            ft.replace(R.id.orthogonalFragmentContainer,transpositionalFragment,TAG_TRANSPOSITIONAL_FRAGMENT);

            ft.commit();
        }
        else {

            getChildFragmentManager().findFragmentByTag(TAG_CAESER_FRAGMENT);
            getChildFragmentManager().findFragmentByTag(TAG_AFFINE_FRAGMENT);
            getChildFragmentManager().findFragmentByTag(TAG_VIGENERE_FRAGMENT);
            getChildFragmentManager().findFragmentByTag(TAG_PLAYFAIR_FRAGMENT);
            getChildFragmentManager().findFragmentByTag(TAG_TRANSPOSITIONAL_FRAGMENT);
        }







        return v;
    }


    public void enableButtons(Button btnEncrypt,Button btnDecrypt,boolean toEnable)
    {
        if(toEnable)
        {
            btnEncrypt.setEnabled(true);
            btnDecrypt.setEnabled(true);
        }
        else
        {
            btnEncrypt.setEnabled(false);
            btnDecrypt.setEnabled(false);
        }
    }

    public void enableButtons(Button btnEncrypt,boolean toEnable)
    {
        if(toEnable)
        {
            btnEncrypt.setEnabled(true);

        }
        else
        {
            btnEncrypt.setEnabled(false);

        }
    }


    public void clearFields(TextInputEditText input, TextInputEditText key,TextInputEditText output)
    {
        input.setText("");
        key.setText("");
        output.setText("");
        clearFocus(input,key);
    }

    public void clearFields(TextInputEditText input, TextInputEditText keyA, TextInputEditText keyB,TextInputEditText output)
    {
        input.setText("");
        keyA.setText("");
        keyB.setText("");
        output.setText("");
        clearFocus(input,keyA,keyB);
    }

    public void clearFocus(TextInputEditText input, TextInputEditText key)
    {
        input.clearFocus();
        key.clearFocus();
    }



    public void clearFocus(TextInputEditText input, TextInputEditText keyA,TextInputEditText keyB)
    {
        input.clearFocus();
        keyA.clearFocus();
        keyB.clearFocus();
    }



}
