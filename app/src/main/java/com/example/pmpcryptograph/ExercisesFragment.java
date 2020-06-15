package com.example.pmpcryptograph;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.exercise.SavedExercise;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.concurrent.ExecutionException;



public class ExercisesFragment extends Fragment {


    private static WordViewModel pcViewModel;
    private FirebaseAuth fbAuth;
    private FirebaseFirestore db;
    private Exercise currentExercise;
    private  SavedExercise currentSavedExercise;

    private boolean caesarEnabled=true;
    private boolean affineEnabled=true;
    private boolean vigenereEnabled=true;
    private boolean playfairEnabled=true;
    private boolean orthoEnabled=true;
    private boolean reverseOrthoeEnabled=true;
    private boolean diagonalEnabled=true;
    FloatingActionButton btnSave;

    SharedPreferences prefs;
   SharedPreferences.Editor editor;
    public static final String  CAESER_ENABLED ="Caeser";
    public static final String  AFFINE_ENABLED ="Affine";
    public static final String  PLAYFAIR_ENABLED ="Playfair";
    public static final String  VIGENERE_ENABLED ="Vigenere";
    public static final String  ORTHO_ENABLED ="Ortho";
    public static final String  DIAGONAL_ENABLED="Diagonal";
    public static final String  REVERSE_ORTHO_ENABLED ="ReverseOrtho";
    Chip chipCaeser;
    Button btnNextExercise;
    ChipGroup cgCipher;
    String id;
    public ExercisesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_exercises, container, false);

        fbAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        pcViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        id=fbAuth.getCurrentUser().getUid();
         ExpandableLayout expandibleConfigure=v.findViewById(R.id.layoutConfigureExpandible);
         TextView txtBody=v.findViewById(R.id.txtBody);
         TextView txtCipher=v.findViewById(R.id.txtCipher);
         TextView txtPlainText=v.findViewById(R.id.txtPlainText);
         TextView txtCipherText=v.findViewById(R.id.txtCipherText);
         TextView txtKey=v.findViewById(R.id.txtKey);
         TextInputEditText etAnswer=v.findViewById(R.id.etAnswer);
         TextInputLayout  layoutAnswer=v.findViewById(R.id.layoutAnswer);
         TextView btnExerciseExpand=v.findViewById(R.id.btnExercise);
         btnNextExercise=v.findViewById(R.id.btnNewExercise);
         Button btnGenerateAnswer=v.findViewById(R.id.btnGenerateAnswer);
         Button btnConfigure=v.findViewById(R.id.btnConfigureExercise);
         btnSave=v.findViewById(R.id.btnSave);
         cgCipher=v.findViewById(R.id.chipgroupCiphers);
         chipCaeser=v.findViewById(R.id.chipCaeser);
         Chip chipAffine=v.findViewById(R.id.chipAffine);
         Chip chipVigenere=v.findViewById(R.id.chipVigenere);
         Chip chipPlayfair=v.findViewById(R.id.chipPlayfair);
         Chip chipOrtho=v.findViewById(R.id.chipOrtho);
         Chip chipReverseOrtho=v.findViewById(R.id.chipOrthoReverse);
         Chip chipDiagonal=v.findViewById(R.id.chipDiagonal);
         ExercisesFragment exercisesFragment = ((ExercisesFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_EXERCISES_FRAGMENT));


        caesarEnabled=true;
        affineEnabled=true;
        vigenereEnabled=true;
       playfairEnabled=true;
       orthoEnabled=true;
        reverseOrthoeEnabled=true;
        diagonalEnabled=true;


        //prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
       // editor=prefs.edit();

            //editor.putBoolean(CAESER_ENABLED, true);
          //  editor.putBoolean(AFFINE_ENABLED, true);
           // editor.putBoolean(VIGENERE_ENABLED, true);
           // editor.putBoolean(PLAYFAIR_ENABLED, true);
           // editor.putBoolean(ORTHO_ENABLED, true);
           // editor.putBoolean(REVERSE_ORTHO_ENABLED, true);
           // editor.putBoolean(DIAGONAL_ENABLED, true);
           // editor.apply();

       /* else
        {*/
           /* caesarEnabled=prefs.getBoolean(CAESER_ENABLED,true);
            chipCaeser.setChecked(caesarEnabled);

            affineEnabled=prefs.getBoolean(AFFINE_ENABLED,true);
            chipAffine.setChecked(affineEnabled);

            vigenereEnabled= prefs.getBoolean(VIGENERE_ENABLED,true);
            chipVigenere.setChecked(vigenereEnabled);

            playfairEnabled=prefs.getBoolean(PLAYFAIR_ENABLED,true);
            chipPlayfair.setChecked(playfairEnabled);

            orthoEnabled=prefs.getBoolean(ORTHO_ENABLED,true);
            chipOrtho.setChecked(orthoEnabled);

            reverseOrthoeEnabled=prefs.getBoolean(REVERSE_ORTHO_ENABLED,true);
            chipReverseOrtho.setChecked(reverseOrthoeEnabled);

            diagonalEnabled=prefs.getBoolean(DIAGONAL_ENABLED,true);
            chipDiagonal.setChecked(diagonalEnabled);*/

        //}


        try {
            int s = pcViewModel.size();
            Log.e("size", String.valueOf(s));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            currentExercise=Exercise.generateRandomExercise(getActivity().getApplicationContext(),pcViewModel,
                    caesarEnabled,
                    affineEnabled,
                    vigenereEnabled,
                    playfairEnabled,
                    orthoEnabled,
                    reverseOrthoeEnabled,
                    diagonalEnabled);
          exerciseToUI(currentExercise,txtCipher,txtPlainText,txtKey,txtCipherText,txtBody);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        btnConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        etAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().isEmpty())
                    layoutAnswer.setErrorEnabled(false);

                if(s.toString().equalsIgnoreCase(currentExercise.getAnswer()))
                {
                    layoutAnswer.setErrorEnabled(true);
                    layoutAnswer.setError("Correct answer");
                    etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                else
                {
                   if(!s.toString().isEmpty()) {
                       layoutAnswer.setErrorEnabled(true);
                       layoutAnswer.setError("Wrong answer");
                       etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.error_orange, 0);
                   }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnNextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(((MainActivity)getActivity()).getKeyboardState())
                        Keyboard.hideKeyboardFrom(getActivity().getApplicationContext(),getActivity().getCurrentFocus());
                    currentExercise= Exercise.generateRandomExercise(getActivity().getApplicationContext(),pcViewModel,
                            caesarEnabled,
                            affineEnabled,
                            vigenereEnabled,
                            playfairEnabled,
                            orthoEnabled,
                            reverseOrthoeEnabled,
                            diagonalEnabled);
                    exerciseToUI(currentExercise,txtCipher,txtPlainText,txtKey,txtCipherText,txtBody);
                    etAnswer.setText("");
                    layoutAnswer.setError(null);
                    layoutAnswer.setErrorEnabled(false);
                    etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayLight)));
                    currentSavedExercise=null;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        btnGenerateAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Keyboard.hideKeyboardFrom(getActivity().getApplicationContext(),getActivity().getCurrentFocus());
                etAnswer.setText(currentExercise.getAnswer());
            }
        });

        if(currentSavedExercise==null)
            btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayLight)));
        else
            btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSavedExercise==null) {
                    saveExercise();
                }
                else
                {
                    unsaveExercise();
                }


            }
        });

        chipCaeser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                caesarEnabled=isChecked;
              //  Log.e("dali",String.valueOf(isChecked));
              //  editor.putBoolean(CAESER_ENABLED,caesarEnabled);
              //  editor.apply();
                allCiphersDisabled(chipCaeser);
            }
        });

        chipAffine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                affineEnabled=isChecked;
              //  editor.putBoolean(AFFINE_ENABLED,affineEnabled);
               // editor.apply();
                allCiphersDisabled(chipAffine);
            }
        });

        chipVigenere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vigenereEnabled=isChecked;
               // editor.putBoolean(VIGENERE_ENABLED,vigenereEnabled);
               // editor.apply();
                allCiphersDisabled(chipVigenere);
            }
        });

        chipPlayfair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playfairEnabled=isChecked;
               // editor.putBoolean(PLAYFAIR_ENABLED,playfairEnabled);
             //   editor.apply();
                allCiphersDisabled(chipPlayfair);
            }
        });

        chipOrtho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                orthoEnabled=isChecked;
               // editor.putBoolean(ORTHO_ENABLED,orthoEnabled);
               // editor.apply();
                allCiphersDisabled(chipOrtho);
            }
        });

        chipReverseOrtho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reverseOrthoeEnabled=isChecked;
               // editor.putBoolean(REVERSE_ORTHO_ENABLED,reverseOrthoeEnabled);
               // editor.apply();
                allCiphersDisabled(chipReverseOrtho);
            }
        });

        chipDiagonal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diagonalEnabled=isChecked;
                //editor.putBoolean(DIAGONAL_ENABLED,diagonalEnabled);
               // editor.apply();
                allCiphersDisabled(chipDiagonal);
            }
        });

        btnExerciseExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandibleConfigure.toggle();
            }
        });



        return v;
    }


    public void exerciseToUI(Exercise exercise,TextView txtCipher, TextView txtPlainText, TextView txtKey, TextView txtCipherText, TextView txtBody)
    {
        txtCipher.setText(exercise.getTitle());
        if(exercise.getType()=="Encrypt")
        {
            txtPlainText.setText(exercise.getCipher().getPlainText());
            txtCipherText.setText("?");
        }
        else
        {
            txtCipherText.setText(exercise.getCipher().getCipherText());
            txtPlainText.setText("?");
        }
        txtKey.setText(exercise.getKeyStr());
        txtBody.setText(exercise.getBody());

    }

    public void saveExercise()
    {

        currentSavedExercise=new SavedExercise(currentExercise.getTitle(),currentExercise.getBody(),currentExercise.getAnswer(),false);
        db.collection("users").document(id).collection("savedExercises").document().set(currentSavedExercise).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                            Snackbar.make(btnSave, R.string.saved_exercise, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.undo, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            unsaveExercise();
                                        }
                                    }).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"must have internet turner on",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void unsaveExercise()
    { String id=fbAuth.getCurrentUser().getUid();
        Query query =  db.collection("users").document(id).collection("savedExercises")
                .whereEqualTo("title", currentSavedExercise.getTitle())
                .whereEqualTo("body", currentSavedExercise.getBody())
                .whereEqualTo("answer",currentSavedExercise.getAnswer());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        db.collection("users").document(id).collection("savedExercises").document(document.getId()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        currentSavedExercise=null;
                                        btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayLight)));
                                        Snackbar.make(btnSave, R.string.unsave_exercise, Snackbar.LENGTH_SHORT)
                                                .setAction(R.string.undo, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        saveExercise();

                                                    }
                                                }).show();
                                    }
                                });

                    }
                } else {

                }
            }
        });
    }


    public void allCiphersDisabled(Chip chip)
    {
        if(!caesarEnabled && !affineEnabled && !vigenereEnabled && !playfairEnabled && !orthoEnabled && !reverseOrthoeEnabled && !diagonalEnabled)
        {
            btnNextExercise.setEnabled(false);
            if(this.isVisible())
            {
            Snackbar.make(cgCipher,R.string.ciphers_disabled,Snackbar.LENGTH_SHORT)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chip.setChecked(true);
                            switch(chip.getId())
                            {
                                case R.id.chipCaeser:caesarEnabled=true; //editor.putBoolean(CAESER_ENABLED,caesarEnabled); editor.apply();
                                break;
                                case R.id.chipAffine:affineEnabled=true;//editor.putBoolean(AFFINE_ENABLED,affineEnabled); editor.apply();
                                break;
                                case R.id.chipPlayfair:playfairEnabled=true;//editor.putBoolean(PLAYFAIR_ENABLED,playfairEnabled); editor.apply();
                                break;
                                case R.id.chipVigenere:vigenereEnabled=true;//editor.putBoolean(VIGENERE_ENABLED,vigenereEnabled); editor.apply();
                                break;
                                case R.id.chipOrtho:orthoEnabled=true;//editor.putBoolean(ORTHO_ENABLED,orthoEnabled); editor.apply();
                                break;
                                case R.id.chipOrthoReverse:reverseOrthoeEnabled=true;//editor.putBoolean(REVERSE_ORTHO_ENABLED,reverseOrthoeEnabled); editor.apply();
                                break;
                                case R.id.chipDiagonal:diagonalEnabled=true;//editor.putBoolean(DIAGONAL_ENABLED,diagonalEnabled); editor.apply();
                            }

                        }
                    }).show();

        }}
        else
            btnNextExercise.setEnabled(true);

    }


}
