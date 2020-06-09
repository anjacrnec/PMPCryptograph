package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import java.util.concurrent.ExecutionException;


public class ExercisesFragment extends Fragment {


    private static WordViewModel pcViewModel;


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

         TextView txtBody=v.findViewById(R.id.txtBody);
         TextView txtCipher=v.findViewById(R.id.txtCipher);
         TextView txtPlainText=v.findViewById(R.id.txtPlainText);
         TextView txtCipherText=v.findViewById(R.id.txtCipherText);
         TextView txtKey=v.findViewById(R.id.txtKey);
         Button btnNextExercise=v.findViewById(R.id.btnNewExercise);

        try {
            int s = pcViewModel.size();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Exercise ex;
        try {
            ex=Exercise.generateRandomExercise(getActivity().getApplicationContext(),pcViewModel,true,true,true,true,true,true,true);
          exerciseToUI(ex,txtCipher,txtPlainText,txtKey,txtCipherText,txtBody);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnNextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Exercise ex = Exercise.generateRandomExercise(getActivity().getApplicationContext(),pcViewModel,true,true,true,true,true,true,true);
                    exerciseToUI(ex,txtCipher,txtPlainText,txtKey,txtCipherText,txtBody);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
