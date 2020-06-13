package com.example.pmpcryptograph;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;

import java.util.Arrays;

import me.piruin.quickaction.ActionItem;
import me.piruin.quickaction.QuickAction;


public class LoginFragment extends Fragment {

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener fbAuthListner;
    private GoogleSignInClient googleSignInClient;
    private AccessTokenTracker tokenTracker;
    CallbackManager callbackManager;

    int RC_IN = 322;
    int RC_FB = 333;
    public LoginFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        firebaseFacebookAuth(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        TextInputLayout layoutEmail=v.findViewById(R.id.layoutEmail);
        TextInputEditText etEmail=v.findViewById(R.id.etEmail);
        TextInputEditText etPass=v.findViewById(R.id.etPassword);

        fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
            getActivity().finish();
        }

        ImageButton btnfb=v.findViewById(R.id.btnFacebookSign);
        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("public_profile", "user_friends"));
            }
        });


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(!isEmailValid(s))
                    {
                        layoutEmail.setErrorEnabled(true);
                        layoutEmail.setError(getResources().getString(R.string.invalid_email_format));
                        etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.error_orange, 0);

                    }
                    else
                    {
                        layoutEmail.setErrorEnabled(false);
                        etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       Button btnRegister=v.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email =etEmail.getText().toString();
                String password = etPass.getText().toString();

                if(email.isEmpty() || password.isEmpty())
                {

                }
                else {
                    fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Snackbar.make(btnRegister, R.string.register_success, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.signin_question, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                signIn();
                                            }
                                        }).show();

                            } else {

                            }

                        }
                    });
                }
            }
        });


        Button btnRegularSignIn = v.findViewById(R.id.btnEmailPassSignin);
        btnRegularSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=etEmail.getText().toString();
                String pass=etPass.getText().toString();
                addLoadingFragment();
                fbAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            signIn();
                        }
                        else{
                                Toast.makeText(getActivity().getApplicationContext(),"No",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        ImageButton btnAnonSignIn = v.findViewById(R.id.btnAnonSignIn);
        btnAnonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLoadingFragment();
                fbAuth.signInAnonymously().addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           signIn();
                        }
                        else{

                        }

                    }
                });
            }
        });



        ImageButton btnGmailSignIn = v.findViewById(R.id.btnGmailSingIn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("361995239778-ldcqe4kuir3i0tmsvlake85hftqsft9c.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        btnGmailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_IN);
            }
        });




       /* LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        signIn();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
        });*/

       /* LoginButton btnFacebookSignIn=(LoginButton) v.findViewById(R.id.btnFacebookSignIn);
        btnFacebookSignIn.setReadPermissions("email","public_profile");
       btnFacebookSignIn.setFragment(this);
       btnFacebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
           @Override
           public void onSuccess(LoginResult loginResult) {
               firebaseFacebookAuth(loginResult.getAccessToken());
           }

           @Override
           public void onCancel() {
               Toast.makeText(getActivity().getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(FacebookException error) {
               Toast.makeText(getActivity().getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
           }
       });*/

        fbAuthListner = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    signIn();
                }
            }
        };

        tokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null)
                    fbAuth.signOut();;
            }
        };



        QuickAction quickFacebook=new QuickAction(getActivity().getApplicationContext());
        quickFacebook.setColorRes(R.color.orange);
        ActionItem aiFacebook=new ActionItem(1,getResources().getString(R.string.facebookSignIn),0);
        quickFacebook.addActionItem(aiFacebook);
        aiFacebook.setSticky(true);

        quickFacebook.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(ActionItem item) {


            }
        });

        quickFacebook.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });


        btnfb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                quickFacebook.show(v);
                return false;
            }


        });


        return v;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                firebaseGoogleAuth(acc);
            } catch (ApiException e) {
                firebaseGoogleAuth(null);
            }
        }




    }

    private void firebaseGoogleAuth(GoogleSignInAccount acc) {
        addLoadingFragment();
        AuthCredential cred = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        fbAuth.signInWithCredential(cred).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {

                    Toast.makeText(getActivity().getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    signIn();
                }

            }
        });

    }

    private void firebaseFacebookAuth(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        addLoadingFragment();
        fbAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    signIn();
                }
                else
                {
                    Log.e("TAG","auth fail",task.getException());
                    Toast.makeText(getActivity().getApplicationContext(),"greshka", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addLoadingFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoadingFragment loadingFragment=new LoadingFragment();
        fragmentTransaction.hide(this);
        fragmentTransaction.add(R.id.loginFragmentContainer, loadingFragment);
        fragmentTransaction.commit();
    }

    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void signIn()
    {
        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        fbAuth.addAuthStateListener(fbAuthListner);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(fbAuthListner!=null)
            fbAuth.removeAuthStateListener(fbAuthListner);
    }
}


