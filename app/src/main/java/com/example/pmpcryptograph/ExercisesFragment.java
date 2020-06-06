package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmpcryptograph.exercise.Exercise;


public class ExercisesFragment extends Fragment {


    public ExercisesFragment() {
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_exercises, container, false);

        TextView tv=(TextView) v.findViewById(R.id.txtRes);
        Button btn=(Button) v.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Exercise e=Exercise.generateRandomExercise(false,false,false,false,false,false,true);
                String s=e.getBody()+" "+e.getAnswer();
                tv.setText(s);
                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
