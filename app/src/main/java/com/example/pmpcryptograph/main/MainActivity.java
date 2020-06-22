package com.example.pmpcryptograph.main;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.example.pmpcryptograph.misc.ConnectionLossDialog;
import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.language.LanguageActivity;
import com.example.pmpcryptograph.login.LoginActivity;
import com.facebook.login.LoginManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rw.keyboardlistener.KeyboardUtils;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements ConnectionLossDialog.ConnectionLossDialogListener, NavigationView.OnNavigationItemSelectedListener{



    private FirebaseAuth fbAuth;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Boolean showDialog;
    public Boolean getKeyboardState() {
        return keyboardState;
    }
    Boolean keyboardState=false;


    public SavedExerciseAdapter adapter;
    public static final String TAG_CURRENT_CONNECTION="curent con";
    public static final String TAG_CONNECTION_LOSS="show connection dialog";
    public static final String TAG_CRYPTOGRAPHER_FRAGMENT="cryptographer";
    public static final String TAG_EXERCISES_FRAGMENT="exercises";
    public static final String TAG_SAVED_FRAGMENT="saved";

    private FragmentManager fm;
Fragment fragment1;
Fragment fragment2;
Fragment fragment3;

Tovuti t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.d("Instance ID", FirebaseInstanceId.getInstance().getId());
        prefs = this.getPreferences(this.MODE_PRIVATE);
        SharedPreferences prefs2= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=prefs.edit();
        editor.putString("FILTER","all");
        editor.apply();

      Locale locale;
        locale=new Locale(prefs2.getString("lang","en"),prefs2.getString("country","US"));
        Log.d("pocetok drzava",prefs2.getString("lang","en")+" "+prefs2.getString("country","US"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config,getResources().getDisplayMetrics());

        t= Tovuti.from(this);
                t.monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                if(isConnected)
                {
                    Log.d("statusot", "da");
                }
                else
                {
                    showDialog=prefs.getBoolean(TAG_CONNECTION_LOSS,true);
                    if(showDialog) {
                        showConnectionDialog();
                    }
                    Log.d("statusot","ne");
                }

                editor.putBoolean(TAG_CURRENT_CONNECTION,isConnected);
                editor.apply();

            }
        });
        fbAuth = FirebaseAuth.getInstance();








        fm=getSupportFragmentManager();
        if(savedInstanceState==null) {
          fragment1 = new CryptographerFragment();
           fragment2 = new ExercisesFragment();
         fragment3=new SavedFragment();
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
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment = null;
                    int item = menuItem.getItemId();
                    if (item == R.id.navCryptography) {


                        fm.beginTransaction().hide(fragment2).hide(fragment3).show(fragment1).commit();

                        return true;
                    } else if (item == R.id.navExercises) {

                        fm.beginTransaction().hide(fragment1).hide(fragment3).show(fragment2).commit();

                        return true;
                    } else if (item == R.id.naviSaved) {

                        fm.beginTransaction().hide(fragment1).hide(fragment2).show(fragment3).commit();

                        return true;
                    }

                    return false;
                }
            });

            if(!fragment1.isHidden())
                navigationView.getMenu().getItem(0).setChecked(true);
            else if(!fragment2.isHidden())
                navigationView.getMenu().getItem(1).setChecked(true);
            else
                navigationView.getMenu().getItem(2).setChecked(true);

        }
        else
        {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
            navigationView.setNavigationItemSelectedListener(this);

            if(!fragment1.isHidden())
                navigationView.getMenu().getItem(0).setChecked(true);
            else if(!fragment2.isHidden())
                navigationView.getMenu().getItem(1).setChecked(true);
            else
                navigationView.getMenu().getItem(2).setChecked(true);
        }

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener()
        {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                if(isVisible)
                {
                    editor.putBoolean("STATE",true);
                    keyboardState=true;
                }

                else
                {
                    editor.putBoolean("STATE",false);
                    keyboardState=false;
                }
            }
        });






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
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
                Intent intent = new Intent(this, LanguageActivity.class);
                startActivity(intent);
                break;
            case R.id.logOutStn:
                if(fbAuth.getCurrentUser().isAnonymous())
                {
                    Resources res=getResources();
                    AlertDialog dialog=new AlertDialog.Builder(this)
                            .setTitle(res.getString(R.string.logout))
                            .setMessage(res.getString(R.string.logut_body))
                            .setPositiveButton(res.getString(R.string.YES), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                                    String id=fbAuth.getCurrentUser().getUid();
                                    db.collection("users").document(id).delete();
                                    fbAuth.getInstance().signOut();
                                    LoginManager.getInstance().logOut();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                            })
                            .setNegativeButton(res.getString(R.string.NO), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    dialog.show();
                }
                else
                    {
                    fbAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showConnectionDialog() {

        ConnectionLossDialog dialog = new ConnectionLossDialog();
        dialog.show(fm, "dialog");
    }


   /* @Override
    public void onRestoreInstanceState(Bundle inState){
        fragment1 = getSupportFragmentManager().getFragment(inState,TAG_CRYPTOGRAPHER_FRAGMENT);
    }*/

  /*  @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, TAG_CRYPTOGRAPHER_FRAGMENT, fragment1);
    }*/






    @Override
    public void getCheckboxStatus(boolean status) {
        editor.putBoolean(TAG_CONNECTION_LOSS,!status);
        editor.apply();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        NavigationView nv=findViewById(R.id.nv);
        Fragment fragment = null;
        int item = menuItem.getItemId();

        if (item == R.id.navCryptography) {
            fm.beginTransaction().hide(fragment2).hide(fragment3).show(fragment1).commit();
            menuItem.setChecked(true);
            nv.getMenu().getItem(1).setChecked(false);
            nv.getMenu().getItem(2).setChecked(false);
            return true;
        } else if (item == R.id.navExercises) {

            fm.beginTransaction().hide(fragment1).hide(fragment3).show(fragment2).commit();
            menuItem.setChecked(true);
            nv.getMenu().getItem(0).setChecked(false);
            nv.getMenu().getItem(2).setChecked(false);
            return true;
        } else if (item == R.id.naviSaved) {

            fm.beginTransaction().hide(fragment1).hide(fragment2).show(fragment3).commit();
            menuItem.setChecked(true);
            nv.getMenu().getItem(0).setChecked(false);
            nv.getMenu().getItem(1).setChecked(false);


            return true;
        }

        return false;
    }
}

