package com.example.pmpcryptograph;

import android.content.res.ColorStateList;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.TextView;

import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.roomdb.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.concurrent.ExecutionException;


public class ExercisesFragment extends Fragment {


    private static WordViewModel pcViewModel;
    private Exercise currentExercise;
    private boolean saved;


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
        pcViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);

        ExpandableLayout expandibleConfigure=v.findViewById(R.id.layoutConfigureExpandible);
         TextView txtBody=v.findViewById(R.id.txtBody);
         TextView txtCipher=v.findViewById(R.id.txtCipher);
         TextView txtPlainText=v.findViewById(R.id.txtPlainText);
         TextView txtCipherText=v.findViewById(R.id.txtCipherText);
         TextView txtKey=v.findViewById(R.id.txtKey);
         TextInputEditText etAnswer=v.findViewById(R.id.etAnswer);
         TextInputLayout  layoutAnswer=v.findViewById(R.id.layoutAnswer);
         TextView btnExerciseExpand=v.findViewById(R.id.btnExercise);
         Button btnNextExercise=v.findViewById(R.id.btnNewExercise);
         Button btnGenerateAnswer=v.findViewById(R.id.btnGenerateAnswer);
         Button btnConfigure=v.findViewById(R.id.btnConfigureExercise);
         FloatingActionButton btnSave=v.findViewById(R.id.btnSave);

        ExercisesFragment exercisesFragment = ((ExercisesFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_EXERCISES_FRAGMENT));


        try {
            int s = pcViewModel.size();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            currentExercise=Exercise.generateRandomExercise(getActivity().getApplicationContext(),pcViewModel,true,true,true,true,true,true,true);
          exerciseToUI(currentExercise,txtCipher,txtPlainText,txtKey,txtCipherText,txtBody);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getDrawerLayout().openDrawer(GravityCompat.END);
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
                    currentExercise= Exercise.generateRandomExercise(getActivity().getApplicationContext(),pcViewModel,true,true,true,true,true,true,true);
                    exerciseToUI(currentExercise,txtCipher,txtPlainText,txtKey,txtCipherText,txtBody);
                    etAnswer.setText("");
                    layoutAnswer.setError(null);
                    layoutAnswer.setErrorEnabled(false);
                    etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                Snackbar.make(btnSave,R.string.saved_exercise,Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                            }
                        }).show();


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
}
