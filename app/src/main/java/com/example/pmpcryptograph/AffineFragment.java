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

import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.google.android.material.textfield.TextInputEditText;

import net.cachapa.expandablelayout.ExpandableLayout;


public class AffineFragment extends Fragment {


    String input,output;
    int keyA, keyB;
    public AffineFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_affine, container, false);

        ExpandableLayout expandableAffine = v.findViewById(R.id.layoutAffineExpandible);
        TextView btnAffine=(TextView)v.findViewById(R.id.btnAffineExpand);
        TextInputEditText etAffineInput =(TextInputEditText) v.findViewById(R.id.etAffineInput);
        TextInputEditText etAffineKeyA=(TextInputEditText) v.findViewById(R.id.etAffineKeyA);
        TextInputEditText etAffineKeyB=(TextInputEditText) v.findViewById(R.id.etAffineKeyB);
        TextInputEditText txtAffineOutput=(TextInputEditText) v.findViewById(R.id.txtAffineOutput);
        Button btnAffineEncrypt=(Button) v.findViewById(R.id.btnAffineEncrypt);
        Button btnAffineDecrypt=(Button) v.findViewById(R.id.btnAffineDecrypt);
        Button btnAffineClear=(Button) v.findViewById(R.id.btnAffineClear);
        btnAffineDecrypt.setEnabled(false);
        btnAffineEncrypt.setEnabled(false);
        CryptographerFragment cryptographerFragment = ((CryptographerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_CRYPTOGRAPHER_FRAGMENT));

        expandableAffine.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);
            }
        });



        btnAffine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                expandableAffine.toggle();
            }
        });

        etAffineInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etAffineKeyA.getText().toString().isEmpty() || etAffineKeyB.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnAffineEncrypt,btnAffineDecrypt,enableButtons);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAffineKeyB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etAffineKeyA.getText().toString().isEmpty() || etAffineInput.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnAffineEncrypt,btnAffineDecrypt,enableButtons);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAffineKeyA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons=false;
                if(s.toString().isEmpty() || !AffineCipher.isKeyAValid(Integer.parseInt(s.toString())) || etAffineKeyB.getText().toString().isEmpty() || etAffineInput.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                cryptographerFragment.enableButtons(btnAffineEncrypt,btnAffineDecrypt,enableButtons);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAffineEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etAffineInput.getText().toString();
                keyA=Integer.parseInt(etAffineKeyA.getText().toString());
                keyB=Integer.parseInt(etAffineKeyB.getText().toString());
                output= AffineCipher.encrypt(input,keyA,keyB);
                txtAffineOutput.setText(output);
                cryptographerFragment.clearFocus(etAffineInput,etAffineKeyA,etAffineKeyB);
            }
        });

        btnAffineDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etAffineInput.getText().toString();
                keyA=Integer.parseInt(etAffineKeyA.getText().toString());
                keyB=Integer.parseInt(etAffineKeyB.getText().toString());
                output= AffineCipher.decrypt(input,keyA,keyB);
                cryptographerFragment.clearFocus(etAffineInput,etAffineKeyA,etAffineKeyB);
                txtAffineOutput.setText(output);
            }
        });

        btnAffineClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cryptographerFragment.clearFields(etAffineInput,etAffineKeyA,etAffineKeyB);
                cryptographerFragment.clearFocus(etAffineInput,etAffineKeyA,etAffineKeyB);
            }
        });

        return v;
    }


}

