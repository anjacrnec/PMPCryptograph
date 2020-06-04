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
import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.google.android.material.textfield.TextInputEditText;

import net.cachapa.expandablelayout.ExpandableLayout;


public class CryptographerFragment extends Fragment {

    private ExpandableLayout expandableCaeser;
    private ExpandableLayout expandableAffine;
    String input,output;
    int key,keyA,keyB;

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

        expandableCaeser = v.findViewById(R.id.layoutCaeserExpandible);
        expandableAffine = v.findViewById(R.id.layoutAffineExpandible);

        TextView btnCaeser=(TextView)v.findViewById(R.id.btnCaeserExpand);
        TextInputEditText etCaeserPt =(TextInputEditText) v.findViewById(R.id.etCeaserPt);
        TextInputEditText etCaeserKey=(TextInputEditText) v.findViewById(R.id.etCaeserKey);
        TextInputEditText txtCaeserResult=(TextInputEditText) v.findViewById(R.id.txtCaeserResult);
        Button btnCaeserEncrypt=(Button) v.findViewById(R.id.btnCaeserEncrypt);
        Button btnCaeserDecrypt=(Button) v.findViewById(R.id.btnCaeserDecrypt);
        Button btnCaeserClear=(Button) v.findViewById(R.id.btnCaeserClear);
        btnCaeserDecrypt.setEnabled(false);
        btnCaeserEncrypt.setEnabled(false);

        etCaeserPt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || etCaeserKey.getText().toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                enableButtons(btnCaeserEncrypt,btnCaeserDecrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCaeserKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || !CaeserCipher.isKeyValid(Integer.parseInt(s.toString())) || etCaeserPt.getText().toString().isEmpty())
                   enableButtons=false;
                else
                    enableButtons=true;
                enableButtons(btnCaeserEncrypt,btnCaeserDecrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCaeserEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etCaeserPt.getText().toString();
                key=Integer.parseInt(etCaeserKey.getText().toString());
                output = CaeserCipher.encrypt(input,key);
                clearFocus(etCaeserPt,etCaeserKey);
                txtCaeserResult.setText(output);
            }
        });

        btnCaeserClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields(etCaeserPt,etCaeserKey,txtCaeserResult);
                clearFocus(etCaeserPt,etCaeserKey);
            }
        });

        btnCaeserDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etCaeserPt.getText().toString();
                key=Integer.parseInt(etCaeserKey.getText().toString());
                output = CaeserCipher.decrypt(input,key);
                clearFocus(etCaeserPt,etCaeserKey);
                txtCaeserResult.setText(output);
            }
        });



        expandableCaeser.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        expandableAffine.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);
            }
        });

        btnCaeser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableCaeser.toggle();
            }
        });

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
                if(s.toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                enableButtons(btnAffineEncrypt,btnAffineDecrypt,enableButtons);

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
                if(s.toString().isEmpty())
                    enableButtons=false;
                else
                    enableButtons=true;
                enableButtons(btnAffineEncrypt,btnAffineDecrypt,enableButtons);

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
                if(s.toString().isEmpty() || !AffineCipher.isKeyAValid(Integer.parseInt(s.toString())))
                    enableButtons=false;
                else
                    enableButtons=true;
                enableButtons(btnAffineEncrypt,btnAffineDecrypt,enableButtons);

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
            }
        });

        btnAffineDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etAffineInput.getText().toString();
                keyA=Integer.parseInt(etAffineKeyA.getText().toString());
                keyB=Integer.parseInt(etAffineKeyB.getText().toString());
                output= AffineCipher.decrypt(input,keyA,keyB);
                txtAffineOutput.setText(output);
            }
        });

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

    public void clearFields(TextInputEditText input, TextInputEditText key,TextInputEditText output)
    {
        input.setText("");
        key.setText("");
        output.setText("");
    }

    public void clearFocus(TextInputEditText input, TextInputEditText key)
    {
        input.clearFocus();
        key.clearFocus();
    }

}
