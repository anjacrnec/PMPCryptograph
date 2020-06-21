package com.example.pmpcryptograph.language;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.pmpcryptograph.R;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.lang));
        Fragment languageFragment;

        if(savedInstanceState==null)
        {
            languageFragment=new LanguageFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.languageFragmentContainer,languageFragment,"LANGUAGE").commit();
        }
        else
        {
            languageFragment=getSupportFragmentManager().findFragmentByTag("LANGUAGE");
        }


    }


}
