package com.example.pmpcryptograph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.cryptography.ReverseOrthogonalCipher;
import com.example.pmpcryptograph.cryptography.CaeserCipher;
import com.example.pmpcryptograph.cryptography.DiagonalCipher;
import com.example.pmpcryptograph.cryptography.PlayfairCipher;
import com.example.pmpcryptograph.cryptography.OrthogonalCipher;
import com.example.pmpcryptograph.cryptography.VigenereCiphere;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import rita.RiTa;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView=(BottomNavigationView) findViewById(R.id.navigationView);
        CryptographerFragment cryptographerFragment=new CryptographerFragment();
        changeBaseFragment(cryptographerFragment);

    }



    public void changeBaseFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.baseFragmentContainer,fragment);
        ft.commit();
    }



}

