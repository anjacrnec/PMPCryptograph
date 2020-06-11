package com.example.pmpcryptograph;

import android.os.Bundle;

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


        CaeserFragment caeserFragment=new CaeserFragment();
        AffineFragment affineFragment=new AffineFragment();
        VigenereFragment vigenereFragment=new VigenereFragment();
        PlayfairFragment playfairFragment=new PlayfairFragment();
        TranspositionalFragment transpositionalFragment=new TranspositionalFragment();
        FragmentManager fm=getChildFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.caeserFragmentContainer,caeserFragment);
        ft.replace(R.id.affineFragmentContainer,affineFragment);
        ft.replace(R.id.vigenereFragmentContainer,vigenereFragment);
        ft.replace(R.id.playfairFragmentContainer,playfairFragment);
        ft.replace(R.id.orthogonalFragmentContainer,transpositionalFragment);
        ft.commit();



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
