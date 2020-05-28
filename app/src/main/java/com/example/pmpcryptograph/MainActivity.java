package com.example.pmpcryptograph;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        EditText etPtCeaser=(EditText) findViewById(R.id.idPtCeaser);
        EditText etKeyCeaser=(EditText) findViewById(R.id.idKeyCeaser);
        Button btnEncryptCeaser=(Button) findViewById(R.id.btnEncryptCeaser);

        EditText etCtCeaser=(EditText) findViewById(R.id.idCtCeaser);
        EditText etDkCeaser=(EditText) findViewById(R.id.idDkCeaser);
        Button btnDecryptCeaser=(Button) findViewById(R.id.btnDecryptCeaser);

        EditText etPtAffine=(EditText) findViewById(R.id.idPtAffine);
        EditText etEk1Affine=(EditText) findViewById(R.id.idEk1Affine);
        EditText etEk2Affine=(EditText) findViewById(R.id.idEk2Affine);
        Button btnEncryptAffine=(Button) findViewById(R.id.btnEncryptAffine);

        EditText etCtAffine=(EditText) findViewById(R.id.idCtAffine);
        EditText etDk1Affine=(EditText) findViewById(R.id.idDk1Affine);
        EditText etDk2Affine=(EditText) findViewById(R.id.idDk2Affine);
        Button btnDecryptAffine=(Button) findViewById(R.id.btnDecryptAffine);

        EditText etPtVigenere=(EditText) findViewById(R.id.idPtVigenere);
        EditText etEkVigenere=(EditText) findViewById(R.id.idEkVigenere);
        Button btnEncryptVigenere=(Button) findViewById(R.id.btnEncryptVigenere);

        EditText etCtVigenere=(EditText) findViewById(R.id.idCtVigenere);
        EditText etDkVigenere=(EditText) findViewById(R.id.idDkVigenere);
        Button btnDecryptVigenere=(Button) findViewById(R.id.btnDecryptVigenere);

        EditText etPtPlayfair=(EditText) findViewById(R.id.idPtPlayfair);
        EditText etEkPlayfair=(EditText) findViewById(R.id.idEkPlayfair);
        Button btnEncryptPlayfair=(Button) findViewById(R.id.btnEncryptPlayfair);

        EditText etCtPlayfair=(EditText) findViewById(R.id.idCtPlayfair);
        EditText etDkPlayfair=(EditText) findViewById(R.id.idDkPlayfair);
        Button btnDecryptPlayfair=(Button) findViewById(R.id.btnDecryptPlayfair);


        btnEncryptCeaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pt=etPtCeaser.getText().toString();
                int key=Integer.parseInt(etKeyCeaser.getText().toString());
                String ct=CaeserCipher.encrypt(pt,key);
                Toast.makeText(getApplicationContext(),ct,Toast.LENGTH_LONG).show();
            }
        });

        btnDecryptCeaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ct=etCtCeaser.getText().toString();
                int key=Integer.parseInt(etDkCeaser.getText().toString());
                String pt=CaeserCipher.encrypt(ct,key);
                Toast.makeText(getApplicationContext(),pt,Toast.LENGTH_LONG).show();
            }
        });

        btnEncryptAffine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pt=etPtAffine.getText().toString();
                int keyA=Integer.parseInt(etEk1Affine.getText().toString());
                int keyB=Integer.parseInt(etEk2Affine.getText().toString());
                String ct=AffineCipher.encrypt(pt,keyA,keyB);
                Toast.makeText(getApplicationContext(),ct, Toast.LENGTH_SHORT).show();
            }
        });

       btnDecryptAffine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ct = etCtAffine.getText().toString();
                int keyA = Integer.parseInt(etDk1Affine.getText().toString());
                int keyB = Integer.parseInt(etDk2Affine.getText().toString());
                String pt=AffineCipher.decrypt(ct,keyA,keyB);
                Toast.makeText(getApplicationContext(),pt,Toast.LENGTH_LONG).show();
            }
        });

       btnEncryptVigenere.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String pt=etPtVigenere.getText().toString();
               String key=etEkVigenere.getText().toString();
               String ct= VigenereCiphere.encrypt(pt,key);
               Toast.makeText(getApplicationContext(),ct,Toast.LENGTH_LONG).show();
           }
       });

       btnDecryptVigenere.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String ct=etCtVigenere.getText().toString();
               String key=etDkVigenere.getText().toString();
               String pt=VigenereCiphere.decrypt(ct,key);
               Toast.makeText(getApplicationContext(),pt,Toast.LENGTH_LONG).show();
           }
       });

       btnEncryptPlayfair.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String pt=etPtPlayfair.getText().toString();
               String key=etEkPlayfair.getText().toString();
               //String rev= PlayfairCipher.removeRepetableChars(key);
              // String  alpha=PlayfairCipher.matrixToString(PlayfairCipher.generateMatrix(key));

            /*   List<String> txt=PlayfairCipher.textToPairs(pt);
               String fin="";
               for(int i=0;i<txt.size();i++)
               {
                   fin=fin+txt.get(i);
                   fin=fin+" ";
               }
               fin=fin+".";*/
            String ct=PlayfairCipher.encrypt(pt,key);
               Toast.makeText(getApplicationContext(),ct,Toast.LENGTH_LONG).show();
           }
       });

       btnDecryptPlayfair.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String ct=etCtPlayfair.getText().toString();
               String key=etDkPlayfair.getText().toString();
               String pt=PlayfairCipher.decrypt(ct,key);
               Toast.makeText(getApplicationContext(),pt,Toast.LENGTH_LONG).show();
           }
       });
    }
}

