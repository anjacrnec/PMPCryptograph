package com.example.pmpcryptograph.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.pmpcryptograph.R;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String TAG_LOGIN="login fr";
    FragmentManager fm;
    Fragment fragmentLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        getSupportActionBar().hide();
        fm=getSupportFragmentManager();
        if(savedInstanceState==null)
        {
            fragmentLogin = new LoginFragment();
            fm.beginTransaction().add(R.id.loginFragmentContainer,fragmentLogin,TAG_LOGIN).commit();
        }
        else
            fm.findFragmentByTag(TAG_LOGIN);




    }


    @Override
    public void onBackPressed() {

    }
}


