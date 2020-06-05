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


public class CaeserFragment extends Fragment {

    String input,output;
    int key;

    public CaeserFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_caeser, container, false);

        ExpandableLayout expandableCaeser = v.findViewById(R.id.layoutCaeserExpandible);
        TextView btnCaeser=(TextView)v.findViewById(R.id.btnCaeserExpand);
        TextInputEditText etCaeserPt =(TextInputEditText) v.findViewById(R.id.etCeaserPt);
        TextInputEditText etCaeserKey=(TextInputEditText) v.findViewById(R.id.etCaeserKey);
        TextInputEditText txtCaeserResult=(TextInputEditText) v.findViewById(R.id.txtCaeserResult);
        Button btnCaeserEncrypt=(Button) v.findViewById(R.id.btnCaeserEncrypt);
        Button btnCaeserDecrypt=(Button) v.findViewById(R.id.btnCaeserDecrypt);
        Button btnCaeserClear=(Button) v.findViewById(R.id.btnCaeserClear);
        btnCaeserDecrypt.setEnabled(false);
        btnCaeserEncrypt.setEnabled(false);
        //FragmentManager fm=getChildFragmentManager();
        CryptographerFragment cryptographerFragment = ((CryptographerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_CRYPTOGRAPHER_FRAGMENT));



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
                cryptographerFragment.enableButtons(btnCaeserEncrypt,btnCaeserDecrypt,enableButtons);
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
                cryptographerFragment.enableButtons(btnCaeserEncrypt,btnCaeserDecrypt,enableButtons);
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
                cryptographerFragment.clearFocus(etCaeserPt,etCaeserKey);
                txtCaeserResult.setText(output);
            }
        });

        btnCaeserClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cryptographerFragment.clearFields(etCaeserPt,etCaeserKey,txtCaeserResult);
                cryptographerFragment.clearFocus(etCaeserPt,etCaeserKey);
            }
        });

        btnCaeserDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etCaeserPt.getText().toString();
                key=Integer.parseInt(etCaeserKey.getText().toString());
                output = CaeserCipher.decrypt(input,key);
                cryptographerFragment.clearFocus(etCaeserPt,etCaeserKey);
                txtCaeserResult.setText(output);
            }
        });

        btnCaeser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableCaeser.toggle();
            }
        });


        expandableCaeser.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        return v;
    }
}
