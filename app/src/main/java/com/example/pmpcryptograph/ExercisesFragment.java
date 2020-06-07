package com.example.pmpcryptograph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.roomdb.VolleyCallback;
import com.example.pmpcryptograph.roomdb.Word;
import com.example.pmpcryptograph.roomdb.WordRequest;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rita.RiTa;


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

        View v=inflater.inflate(R.layout.fragment_exercises, container, false);

        pcViewModel= ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        TextView tv=(TextView) v.findViewById(R.id.txtRes);
        Button btn=(Button) v.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Exercise e=Exercise.generateRandomExercise(true,true,true,true,true,true,true);
                String s=e.getBody()+" "+e.getAnswer();
                tv.setText(s);
                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });

        Button btn2=(Button) v.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Word> words=pcViewModel.getListWords();
                    String s="";
                    for(int i=0;i<words.size();i++)
                    {
                        s=s+words.get(i).getWord()+" ";
                    }
                    Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        Button btn3=(Button) v.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word=RiTa.randomWord();
                //String url="https://owlbot.info/api/v4/dictionary/"+word;
                String url="https://api.dictionaryapi.dev/api/v2/entries/en/"+word;
                WordRequest w=new WordRequest(getActivity().getApplicationContext());
                w.getWord(url, new VolleyCallback() {
                    @Override
                    public void getResponse(JSONArray response) {
                        String d=getDef(response);
                       tv.setText(word+"="+d);
                    }
                });

            }
        });
        return v;
    }

    public String getDef(JSONArray response)  {
        String def="";
        try {

            Log.d("eve","tuka sme");
            // def= String.valueOf(response.getJSONArray("definitions").getJSONObject(0).get("definition"));
            // response.getJSONArray("definition");

          def = String.valueOf(response.getJSONObject(0).getJSONArray("meanings").getJSONObject(0).getJSONArray("definitions").getJSONObject(0).get("example"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return def;
    }
}
