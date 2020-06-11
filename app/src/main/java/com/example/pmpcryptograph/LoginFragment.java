package com.example.pmpcryptograph;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginFragment extends Fragment {

    private FirebaseAuth fbAuth;
    private GoogleSignInClient googleSignInClient;
    int RC_IN = 322;

    public LoginFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        TextInputEditText etEmail=v.findViewById(R.id.etEmail);
        TextInputEditText etPass=v.findViewById(R.id.etPassword);

        fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
            getActivity().finish();
        }


        Button btnRegularSignIn = v.findViewById(R.id.btnEmailPassSignin);
        btnRegularSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=etEmail.getText().toString();
                String pass=etPass.getText().toString();
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


        Button btnAnonSignIn = v.findViewById(R.id.btnAnonSignIn);
        btnAnonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        Button btnGmailSignIn = v.findViewById(R.id.btnGmailSingIn);
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


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        AuthCredential cred = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        fbAuth.signInWithCredential(cred).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signIn();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signIn()
    {
        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }
}


