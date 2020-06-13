package com.example.pmpcryptograph;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.rw.keyboardlistener.KeyboardUtils;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;
    public Boolean getKeyboardState() {
        return keyboardState;
    }
    DrawerLayout drawerConfigure;
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

        fbAuth = FirebaseAuth.getInstance();
         drawerConfigure=findViewById(R.id.drawerConfigure);
         drawerConfigure.setElevation(0);

        BottomNavigationView navigationView=(BottomNavigationView) findViewById(R.id.navigationView);

        drawerConfigure.closeDrawers();
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

                   fbAuth.getInstance().signOut();
                   LoginManager.getInstance().logOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
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

    public DrawerLayout getDrawerLayout()
    {
        return drawerConfigure;
    }

    @Override
    public void onBackPressed() {

    }


}

