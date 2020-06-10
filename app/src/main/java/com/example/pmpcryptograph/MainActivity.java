package com.example.pmpcryptograph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.rw.keyboardlistener.KeyboardUtils;

import rita.RiTa;


public class MainActivity extends AppCompatActivity {

    public Boolean getKeyboardState() {
        return keyboardState;
    }

    Boolean keyboardState=false;

    public static final String TAG_CRYPTOGRAPHER_FRAGMENT="cryptographer";
    public static final String TAG_EXERCISES_FRAGMENT="exercises";
    public static final String TAG_SAVED_FRAGMENT="saved";
    private FragmentManager fm;
    private Fragment currentFragment;

    private Fragment fragment1 = new CryptographerFragment();
    private Fragment fragment2 = new ExercisesFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView=(BottomNavigationView) findViewById(R.id.navigationView);
        fm=getSupportFragmentManager();
        /*Fragment fragment=fm.findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT);

        if(fragment==null)
            fragment=new CryptographerFragment();
        changeBaseFragment(fragment,TAG_CRYPTOGRAPHER_FRAGMENT);*/
        if(savedInstanceState==null) {
            fm.beginTransaction().add(R.id.baseFragmentContainer, fragment1, TAG_CRYPTOGRAPHER_FRAGMENT).commit();
            fm.beginTransaction().add(R.id.baseFragmentContainer, fragment2, TAG_EXERCISES_FRAGMENT).hide(fragment2).commit();
        }
        else
        {
            fragment1=getSupportFragmentManager().findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT);
            fragment2=getSupportFragmentManager().findFragmentByTag(TAG_EXERCISES_FRAGMENT);
        }



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

                    fm.beginTransaction().hide(fragment2).show(fragment1).commit();

                    return true;
                }
                else if (item==R.id.navExercises)
                {
                    /*fragment=fm.findFragmentByTag(TAG_EXERCISES_FRAGMENT);
                    if(fragment==null)
                        changeBaseFragment(new ExercisesFragment(),TAG_EXERCISES_FRAGMENT);
                    else
                        changeBaseFragment(fragment,TAG_EXERCISES_FRAGMENT);*/
                    fm.beginTransaction().hide(fragment1).show(fragment2).commit();

                    return true;
                }
                else if(item==R.id.naviSaved)
                {

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


    public void changeBaseFragment(Fragment fragment,String tag)
    {
        if(!fragment.equals(currentFragment))
        {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.baseFragmentContainer, fragment, tag);
            ft.commit();
            currentFragment=fragment;
        }
    }



}

