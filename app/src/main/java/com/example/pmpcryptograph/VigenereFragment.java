package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;
import com.google.android.material.textfield.TextInputEditText;

import net.cachapa.expandablelayout.ExpandableLayout;


public class VigenereFragment extends Fragment {

    String input,output,key;

    public VigenereFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_vigenere, container, false);

        ExpandableLayout expandableVigenere = v.findViewById(R.id.layoutVigenereExpandible);
        TextView btnVigenere=(TextView)v.findViewById(R.id.btnVigenereExpand);
        TextInputEditText etVigenereInput =(TextInputEditText) v.findViewById(R.id.etVigenereInput);
        TextInputEditText etVigenereKey=(TextInputEditText) v.findViewById(R.id.etVigenereKey);
        TextInputEditText txtVigenereOutput=(TextInputEditText) v.findViewById(R.id.txtVigenereOutput);
        Button btnVigenereEncrypt=(Button) v.findViewById(R.id.btnVigenereEncrypt);
        Button btnVigenereDecrypt=(Button) v.findViewById(R.id.btnVigenereDecrypt);
        Button btnVigenereClear=(Button) v.findViewById(R.id.btnVigenereClear);
        btnVigenereDecrypt.setEnabled(false);
        btnVigenereEncrypt.setEnabled(false);

        CryptographerFragment cryptographerFragment = ((CryptographerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_CRYPTOGRAPHER_FRAGMENT));

        btnVigenere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableVigenere.toggle();
            }
        });

        etVigenereInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etVigenereKey.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnVigenereEncrypt,btnVigenereDecrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etVigenereKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etVigenereInput.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnVigenereEncrypt,btnVigenereDecrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnVigenereEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etVigenereInput.getText().toString();
                key=etVigenereKey.getText().toString();
                output = VigenereCiphere.encrypt(input,key);
                txtVigenereOutput.setText(output);
                cryptographerFragment.clearFocus(etVigenereInput,etVigenereKey);
            }
        });

        btnVigenereClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cryptographerFragment.clearFields(etVigenereInput,etVigenereKey,txtVigenereOutput);
            }
        });

        btnVigenereDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etVigenereInput.getText().toString();
                key=etVigenereKey.getText().toString();
                output = VigenereCiphere.decrypt(input,key);
                txtVigenereOutput.setText(output);
                cryptographerFragment.clearFocus(etVigenereInput,etVigenereKey);
            }
        });

        expandableVigenere.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        return v;
    }
}
