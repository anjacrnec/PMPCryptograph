package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pmpcryptograph.cryptography.DiagonalCipher;
import com.example.pmpcryptograph.cryptography.OrthogonalCipher;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;
import com.example.pmpcryptograph.cryptography.TranspositionalCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.cachapa.expandablelayout.ExpandableLayout;


public class TranspositionalFragment extends Fragment {

    String input,output,key;
    boolean enableButtons;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_transpositional, container, false);


        ExpandableLayout expandableTranspositional = v.findViewById(R.id.layoutTranspositionalExpandible);
        TextView btnTranspositional=(TextView)v.findViewById(R.id.btnTranspositionalExpand);
        TextInputLayout layoutTranspostionalKey=(TextInputLayout) v.findViewById(R.id.layoutTranspositionalKey);
        TextInputEditText etTranspositionalInput =(TextInputEditText) v.findViewById(R.id.etTranspositionalInput);
        TextInputEditText etTranspositionalKey=(TextInputEditText) v.findViewById(R.id.etTranspositionalKey);
        TextInputEditText txtTranspositionalOutput=(TextInputEditText) v.findViewById(R.id.txtTranspositionalOutput);
        Button btnTranspositionalEncrypt=(Button) v.findViewById(R.id.btnTranspositionalEncrypt);
        Button btnTranspositionalClear=(Button) v.findViewById(R.id.btnTranspositionalClear);

        RadioGroup groupMethods=(RadioGroup) v.findViewById(R.id.radioGroupMethods);
        RadioButton rbOrthogonal=(RadioButton) v.findViewById(R.id.rbOrthogonal);
        RadioButton rbOrthogonalReverse=(RadioButton) v.findViewById(R.id.rbOrthogonalReverse);
        RadioButton rbDiagonal=(RadioButton) v.findViewById(R.id.rbDiagonal);

        btnTranspositionalEncrypt.setEnabled(false);

        CryptographerFragment cryptographerFragment = ((CryptographerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.baseFragmentContainer));

        btnTranspositional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableTranspositional.toggle();
            }
        });


        groupMethods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String transpositionalHelper=getString(R.string.transpositional_helper_key);
                String diagonalHelper=transpositionalHelper+getString(R.string.transpositional_diagonal_key);
                if(rbDiagonal.isChecked())
                {
                    layoutTranspostionalKey.setHelperText(diagonalHelper);
                    if(!DiagonalCipher.isKeyShorter(etTranspositionalInput.getText().toString(),etTranspositionalKey.getText().toString()))
                        enableButtons=false;
                }
                else
                {
                    layoutTranspostionalKey.setHelperText(transpositionalHelper);
                    if(!etTranspositionalInput.getText().toString().isEmpty() && !etTranspositionalKey.getText().toString().isEmpty() && TranspositionalCipher.isKeyValid(etTranspositionalKey.getText().toString()))
                        enableButtons=true;
                }
                cryptographerFragment.enableButtons(btnTranspositionalEncrypt,enableButtons);
            }
        });

        etTranspositionalInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() || etTranspositionalKey.getText().toString().isEmpty()) {
                    enableButtons = false;
                }
                else if(rbDiagonal.isChecked() && !DiagonalCipher.isKeyShorter(s.toString(),etTranspositionalKey.getText().toString()))
                {
                    enableButtons=false;
                }
                else
                    enableButtons=true;

                cryptographerFragment.enableButtons(btnTranspositionalEncrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etTranspositionalKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().isEmpty() || etTranspositionalInput.getText().toString().isEmpty())
                {
                    enableButtons=false;
                }
                else if (!TranspositionalCipher.isKeyValid(s.toString()))
                {
                    enableButtons=false;
                }
                else if(rbDiagonal.isChecked() && !DiagonalCipher.isKeyShorter(etTranspositionalInput.getText().toString(),s.toString()))
                {
                    enableButtons=false;
                }
                else
                    enableButtons=true;

                cryptographerFragment.enableButtons(btnTranspositionalEncrypt,enableButtons);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnTranspositionalEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=etTranspositionalInput.getText().toString();
                key=etTranspositionalKey.getText().toString();

                if(rbOrthogonal.isChecked())
                {
                    output = OrthogonalCipher.encrypt(input, key);

                }
                else if(rbOrthogonalReverse.isChecked())
                {
                    output= ReverseOrthogonalCipher.encrypt(input,key);
                }
                else if(rbDiagonal.isChecked())
                {
                    output= DiagonalCipher.encrypt(input,key);
                }

                txtTranspositionalOutput.setText(output);
                cryptographerFragment.clearFocus(etTranspositionalInput, etTranspositionalKey);
            }
        });

        btnTranspositionalClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cryptographerFragment.clearFields(etTranspositionalInput,etTranspositionalKey,txtTranspositionalOutput);
                rbOrthogonal.setChecked(true);
            }

        });
        return v;
    }
}
