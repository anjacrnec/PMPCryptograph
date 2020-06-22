package com.example.pmpcryptograph.main;

import android.content.SharedPreferences;
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

import com.example.pmpcryptograph.misc.Keyboard;
import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.google.android.material.textfield.TextInputEditText;

import net.cachapa.expandablelayout.ExpandableLayout;


public class PlayfairFragment extends Fragment {

    String input, output, key;
SharedPreferences prefs;
    public PlayfairFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_playfair, container, false);

        ExpandableLayout expandablePlayfair = v.findViewById(R.id.layoutPlayfairExpandible);
        TextView btnPlayfair=(TextView)v.findViewById(R.id.btnPlayfairExpand);
        TextInputEditText etPlayfairInput =(TextInputEditText) v.findViewById(R.id.etPlayfairInput);
        TextInputEditText etPlayfairKey=(TextInputEditText) v.findViewById(R.id.etPlayfairKey);
        TextInputEditText txtPlayfairOutput=(TextInputEditText) v.findViewById(R.id.txtPlayfairOutput);
        Button btnPlayfairEncrypt=(Button) v.findViewById(R.id.btnPlayfairEncrypt);
        Button btnPlayfairDecrypt=(Button) v.findViewById(R.id.btnPlayfairDecrypt);
        Button btnPlayfairClear=(Button) v.findViewById(R.id.btnPlayfairClear);
        btnPlayfairDecrypt.setEnabled(false);
        btnPlayfairEncrypt.setEnabled(false);
        CryptographerFragment cryptographerFragment = ((CryptographerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_CRYPTOGRAPHER_FRAGMENT));
        prefs=getActivity().getPreferences(getActivity().MODE_PRIVATE);


        btnPlayfair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandablePlayfair.toggle();
            }
        });

        etPlayfairInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etPlayfairKey.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnPlayfairEncrypt,btnPlayfairDecrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPlayfairKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etPlayfairInput.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnPlayfairEncrypt,btnPlayfairDecrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnPlayfairEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getBoolean("STATE",false))
                    Keyboard.hideKeyboardFrom(getActivity().getApplicationContext(),getActivity().getCurrentFocus());

                input=etPlayfairInput.getText().toString();
                key=etPlayfairKey.getText().toString();
                output = PlayfairCipher.encrypt(input,key);
                txtPlayfairOutput.setText(output);
                cryptographerFragment.clearFocus(etPlayfairInput,etPlayfairKey);
            }
        });

        btnPlayfairDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getBoolean("STATE",false))
                    Keyboard.hideKeyboardFrom(getActivity().getApplicationContext(),getActivity().getCurrentFocus());
                input=etPlayfairInput.getText().toString();
                key=etPlayfairKey.getText().toString();
                output = PlayfairCipher.decrypt(input,key);
                txtPlayfairOutput.setText(output);
                cryptographerFragment.clearFocus(etPlayfairInput,etPlayfairKey);
            }
        });

        btnPlayfairClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cryptographerFragment.clearFields(etPlayfairInput,etPlayfairKey,txtPlayfairOutput);
            }
        });



        expandablePlayfair.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });


        return v;
    }
}
