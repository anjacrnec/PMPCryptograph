package com.example.pmpcryptograph;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.facebook.login.LoginManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.rw.keyboardlistener.KeyboardUtils;



public class MainActivity extends AppCompatActivity implements ConnectionLossDialog.ConnectionLossDialogListener{


    private FirebaseAuth fbAuth;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Boolean showDialog;
    public Boolean getKeyboardState() {
        return keyboardState;
    }
    Boolean keyboardState=false;


    public SavedExerciseAdapter adapter;
    public static final String  CAESER_FILER="caeser filter";
    public static final String  PLAYFAIR_FILTER="playfair filter";
    public static final String AFFINE_FILTER="affine filter";
    public static final String VIGENERE_FILTER="vigenere filter";
    public static final String ORTHOGONAL_FILTER="orthogonal filter";
    public static final String REVERSE_ORTHOGONAL_FILTER="reverse orthogonal filter";
    public static final String DIAGONAL_FILTER="diagonal filter";

    public static final String TAG_CONNECTION_LOSS="show connection dialog";
    public static final String TAG_CRYPTOGRAPHER_FRAGMENT="cryptographer";
    public static final String TAG_EXERCISES_FRAGMENT="exercises";
    public static final String TAG_SAVED_FRAGMENT="saved";
    private FragmentManager fm;
    private Fragment currentFragment;

    private Fragment fragment1 = new CryptographerFragment();
    private Fragment fragment2 = new ExercisesFragment();
    private Fragment fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = this.getPreferences(this.MODE_PRIVATE);
        editor=prefs.edit();
        editor.putString("FILTER","all");
        editor.apply();
        fragment3=new SavedFragment();
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                if(isConnected)
                    Log.d("statusot","da");
                else
                {
                    showDialog=prefs.getBoolean(TAG_CONNECTION_LOSS,true);
                    if(showDialog) {
                        showConnectionDialog();
                    }
                    Log.d("statusot","ne");
                }

            }
        });
        fbAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigationView=(BottomNavigationView) findViewById(R.id.navigationView);

        fm=getSupportFragmentManager();
        if(savedInstanceState==null) {
            fm.beginTransaction().add(R.id.baseFragmentContainer, fragment1, TAG_CRYPTOGRAPHER_FRAGMENT).commit();
            fm.beginTransaction().add(R.id.baseFragmentContainer, fragment2, TAG_EXERCISES_FRAGMENT).hide(fragment2).commit();
            fm.beginTransaction().add(R.id.baseFragmentContainer, fragment3, TAG_SAVED_FRAGMENT).hide(fragment3).commit();
        }
        else
        {
            fragment1=getSupportFragmentManager().findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT);
            fragment2=getSupportFragmentManager().findFragmentByTag(TAG_EXERCISES_FRAGMENT);
            fragment3=getSupportFragmentManager().findFragmentByTag(TAG_SAVED_FRAGMENT);
        }

        getSupportActionBar().show();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment=null;
                int item=menuItem.getItemId();
                if(item==R.id.navCryptography)
                {
                   /* fragment=fm.findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT);
                    if(fragment==null)
                        changeBaseFragment(new CryptographerFragment(),TAG_CRYPTOGRAPHER_FRAGMENT);
                    else
                        changeBaseFragment(fragment,TAG_CRYPTOGRAPHER_FRAGMENT);*/

                    fm.beginTransaction().hide(fragment2).hide(fragment3).show(fragment1).commit();

                    return true;
                }
                else if (item==R.id.navExercises)
                {
                    /*fragment=fm.findFragmentByTag(TAG_EXERCISES_FRAGMENT);
                    if(fragment==null)
                        changeBaseFragment(new ExercisesFragment(),TAG_EXERCISES_FRAGMENT);
                    else
                        changeBaseFragment(fragment,TAG_EXERCISES_FRAGMENT);*/
                    fm.beginTransaction().hide(fragment1).hide(fragment3).show(fragment2).commit();

                    return true;
                }
                else if(item==R.id.naviSaved)
                {

                    fm.beginTransaction().hide(fragment1).hide(fragment2).show(fragment3).commit();

                   //fbAuth.getInstance().signOut();
                  // LoginManager.getInstance().logOut();
                    //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                   // finish();
                    return true;
                }

                return false;
            }
        });



        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener()
        {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                if(isVisible)
                    keyboardState=true;
                else
                    keyboardState=false;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.languageStn:
                break;
            case R.id.logOutStn:
                fbAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, LoginActivity.class));
                this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void showConnectionDialog() {

        ConnectionLossDialog dialog = new ConnectionLossDialog();
        dialog.show(fm, "dialog");
    }


    @Override
    protected void onStop(){
        super.onStop();
    }


    @Override
    public void getCheckboxStatus(boolean status) {
        editor.putBoolean(TAG_CONNECTION_LOSS,!status);
        editor.apply();
    }
}

