package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.cryptography.CaeserCipher;


public class ExercisesFragment extends Fragment {


    public ExercisesFragment() {
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_exercises, container, false);

        TextView tv=(TextView) v.findViewById(R.id.txtRes);
        Button btn=(Button) v.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s=Exercise.chooseCipher(true,true,true,true,false,false,false);

               /*CaeserCipher cipher=(CaeserCipher)Exercise.generateCipher("Caeser","Encrypt");
                String s=cipher.getCipherText();*/

                AffineCipher ac=(AffineCipher) Exercise.generateCipher("Affine","Encrypt");


               CaeserCipher cs=CaeserCipher.generateCipher();
               String s=ac.getPlainText()+" "+ac.getKeyA()+" "+ac.getKeyB()+" "+ac.getCipherText();
               tv.setText(s);
                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
