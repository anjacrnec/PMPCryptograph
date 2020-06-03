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
import com.google.android.material.textfield.TextInputEditText;

import net.cachapa.expandablelayout.ExpandableLayout;


public class CryptographerFragment extends Fragment {

    private ExpandableLayout expandableLayout0;
    private ExpandableLayout expandableLayout1;
    String input;
    int key;
    String output;

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

        expandableLayout0 = v.findViewById(R.id.layoutCaeserExpandible);
        expandableLayout1 = v.findViewById(R.id.expandable_layout_1);

        TextView btnCaeser=(TextView)v.findViewById(R.id.btnCaeserExpand);
        TextInputEditText etCaeserPt =(TextInputEditText) v.findViewById(R.id.etCeaserPt);
        TextInputEditText etCaeserKey=(TextInputEditText) v.findViewById(R.id.etCaeserKey);
        TextInputEditText txtCaeserResult=(TextInputEditText) v.findViewById(R.id.txtCaeserResult);
        Button btnCaeserEncrypt=(Button) v.findViewById(R.id.btnCaeserEncrypt);
        Button btnCaeserDecrypt=(Button) v.findViewById(R.id.btnCaeserDecrypt);
        Button btnCaeserClear=(Button) v.findViewById(R.id.btnCaeserClear);
        btnCaeserDecrypt.setEnabled(false);
        btnCaeserEncrypt.setEnabled(false);

        etCaeserKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enableButtons;
                if(s.toString().isEmpty() || !CaeserCipher.isKeyValid(Integer.parseInt(s.toString())))
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



        expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        expandableLayout1.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);
            }
        });

        btnCaeser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout0.toggle();
            }
        });

        TextView btn2=(TextView)v.findViewById(R.id.expand_button_1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout1.toggle();
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
