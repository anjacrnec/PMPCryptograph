package com.example.pmpcryptograph.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.pmpcryptograph.misc.Keyboard;
import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.exercise.SavedExercise;
import com.example.pmpcryptograph.roomdb.VolleyCallback;
import com.example.pmpcryptograph.roomdb.Word;
import com.example.pmpcryptograph.roomdb.WordRequest;
import com.example.pmpcryptograph.roomdb.WordViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
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

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;



public class ExercisesFragment extends Fragment {

    AsyncTask<List<Word>,Void,Boolean> task;
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
        if(savedInstanceState!=null)
        {
            currentSavedExercise=savedInstanceState.getParcelable("current");
            currentExercise=savedInstanceState.getParcelable("current2");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_exercises, container, false);

        prefs=getActivity().getPreferences(getActivity().MODE_PRIVATE);
        editor=prefs.edit();
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
        Chip chipSentence=v.findViewById(R.id.chipSentences);
         ExercisesFragment exercisesFragment = ((ExercisesFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_EXERCISES_FRAGMENT));


        caesarEnabled=true;
        affineEnabled=true;
        vigenereEnabled=true;
       playfairEnabled=true;
       orthoEnabled=true;
        reverseOrthoeEnabled=true;
        diagonalEnabled=true;



        if(savedInstanceState==null) {

            try {
                int s = pcViewModel.size();
                Log.e("size", String.valueOf(s));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                currentExercise = Exercise.generateRandomExercise(getActivity().getBaseContext(), pcViewModel,
                        caesarEnabled,
                        affineEnabled,
                        vigenereEnabled,
                        playfairEnabled,
                        orthoEnabled,
                        reverseOrthoeEnabled,
                        diagonalEnabled,
                        chipSentence.isChecked());
                String body;
                exerciseToUI(currentExercise, txtCipher, txtPlainText, txtKey, txtCipherText, txtBody);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            exerciseToUI(currentExercise, txtCipher, txtPlainText, txtKey, txtCipherText, txtBody);
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
                    layoutAnswer.setError(getResources().getString(R.string.correct_answer));
                    etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                else
                {
                   if(!s.toString().isEmpty()) {
                       layoutAnswer.setErrorEnabled(true);
                       layoutAnswer.setError(getResources().getString(R.string.wrong_answer));
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
                    currentExercise= Exercise.generateRandomExercise(getActivity().getBaseContext(),pcViewModel,
                            caesarEnabled,
                            affineEnabled,
                            vigenereEnabled,
                            playfairEnabled,
                            orthoEnabled,
                            reverseOrthoeEnabled,
                            diagonalEnabled
                    ,chipSentence.isChecked());
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

                    if (currentSavedExercise == null)
                    {
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
                allCiphersDisabled(chipCaeser);
            }
        });

        chipAffine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                affineEnabled=isChecked;
                allCiphersDisabled(chipAffine);
            }
        });

        chipVigenere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vigenereEnabled=isChecked;
                allCiphersDisabled(chipVigenere);
            }
        });

        chipPlayfair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playfairEnabled=isChecked;
                allCiphersDisabled(chipPlayfair);
            }
        });

        chipOrtho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                orthoEnabled=isChecked;
                allCiphersDisabled(chipOrtho);
            }
        });

        chipReverseOrtho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reverseOrthoeEnabled=isChecked;
                allCiphersDisabled(chipReverseOrtho);
            }
        });

        chipDiagonal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diagonalEnabled=isChecked;
                allCiphersDisabled(chipDiagonal);
            }
        });

        btnExerciseExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandibleConfigure.toggle();
            }
        });

        btnConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandibleConfigure.toggle();
            }
        });


        chipSentence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chipSentence.isChecked())
                {
                boolean enabledSentences=prefs.getBoolean("SENTENCE",false);
                if(!enabledSentences)
                {
                    boolean connection=prefs.getBoolean(MainActivity.TAG_CURRENT_CONNECTION,false);
                    if(connection)
                    {
                        AlertDialog dialog=new AlertDialog.Builder(v.getContext())
                                .setTitle(getResources().getString(R.string.alert_sentence_Tconnection_tite))
                                .setMessage(getResources().getString(R.string.alert_sentence_Tconnection_body))
                                .setPositiveButton(getResources().getString(R.string.YES), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {

                                            List<Word> allWords=pcViewModel.getListWords();
                                            updateExamples(allWords,getActivity().getApplicationContext(),getActivity());

                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.NO), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        chipSentence.setChecked(false);
                                    }
                                })
                                .create();
                        dialog.show();

                    }
                    else
                    {
                        AlertDialog dialog=new AlertDialog.Builder(v.getContext())
                                .setTitle(getResources().getString(R.string.alert_sentence_Fconnection_tite))
                                .setMessage(getResources().getString(R.string.alert_sentence_Fconnection_body))
                                .setNeutralButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        chipSentence.setChecked(false);
                                    }
                                }).create();
                        dialog.show();
                    }
                }

                else
                {

                }
            }
            }
        });


        try {
            List <Word> all=pcViewModel.getListWords();
            int k=0;
            for(int i=0;i<all.size();i++)
            {
                if(all.get(i).getExample().isEmpty())
                    k++;

            }

            if(k>=130)
            {
                SharedPreferences prefs=getActivity().getPreferences(getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();
                editor.putBoolean("SENTENCE",false);
                editor.apply();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        return v;
    }


    public void updateExamples(List<Word> lista, Context con,Activity act)
    {
        task= new UpdateExamplesAsyncTask(con,pcViewModel,act);
        task.execute(lista);
    }

    private static class UpdateExamplesAsyncTask extends AsyncTask<List<Word>,Void,Boolean> {

        Boolean finished=false;
        ProgressDialog dialog;
        Context con;
        WordViewModel vm;
        Activity act;

        private UpdateExamplesAsyncTask(Context con, WordViewModel vm,Activity act) {
            this.con = con;
            this.vm = vm;
            this.act=act;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(act);
            dialog.setTitle(con.getResources().getString(R.string.downloading));
            dialog.setMessage(con.getResources().getString(R.string.wait));
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(List... lists) {
            List<Word> words = lists[0];
            for (int i = 0; i < words.size(); i++) {
                Word w = words.get(i);
                WordRequest r = new WordRequest(con);
                String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + w.getWord();
                r.getWord(url, new VolleyCallback() {
                    @Override
                    public void getResponse(JSONArray response) throws JSONException, ExecutionException, InterruptedException {
                        Log.e("ovde", "volley");
                        String example = "";
                        example = String.valueOf(response.getJSONObject(0).getJSONArray("meanings").getJSONObject(0).getJSONArray("definitions").getJSONObject(0).get("example"));
                        Log.e("example", example);
                        w.setExample(example);
                        vm.update(w);
                        //Toast.makeText(getActivity().getApplicationContext(), example, Toast.LENGTH_SHORT).show();
                    }
                });

                finished=true;
            }
            return finished;
        }

        @Override
        protected void onPostExecute(Boolean finished) {

            if(finished!=null)
            {

               SharedPreferences prefs=act.getPreferences(con.MODE_PRIVATE);
               SharedPreferences.Editor editor=prefs.edit();
               editor.putBoolean("SENTENCE",true);
               editor.apply();
            }
            dialog.dismiss();


        }
    }


    public void exerciseToUI(Exercise exercise,TextView txtCipher, TextView txtPlainText, TextView txtKey, TextView txtCipherText, TextView txtBody)
    {
        Resources res=getResources();
        String title=exercise.getTitle();
        switch (title)
        {
            case "Caeser":
                txtCipher.setText(res.getString(R.string.caeser));
                break;
            case "Affine":
                txtCipher.setText(res.getString(R.string.affine));
                break;
            case "Vigenere":
                txtCipher.setText(res.getString(R.string.vigenere));
                break;
            case "Playfair":
                txtCipher.setText(res.getString(R.string.playfair));
                break;
            case "Orthogonal":
                txtCipher.setText(res.getString(R.string.ortho));
                break;
            case "ReverseOrthogonal":
                txtCipher.setText(res.getString(R.string.reverse));
                break;
            case "Diagonal":
                txtCipher.setText(res.getString(R.string.diagonal));
                break;
        }



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

        boolean visible;
        String title=currentExercise.getTitle();
        String filter=prefs.getString("FILTER","all");
        if(title.equals(filter) || filter.equals("all"))
            visible=true;
        else
            visible=false;
        currentSavedExercise=new SavedExercise(currentExercise,false,visible);
        db.collection("users").document(id).collection("savedExercises").document().set(currentSavedExercise).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });


        String message;
        if(prefs.getBoolean(MainActivity.TAG_CURRENT_CONNECTION,true))
            message=getResources().getString(R.string.saved_exercise);
        else
            message=getResources().getString(R.string.saved_exercise)+" "+getResources().getString(R.string.offline_mode);

        btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        Snackbar.make(btnSave, message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unsaveExercise();
                    }
                }).show();

    }

    public void unsaveExercise()
    {
        String id=fbAuth.getCurrentUser().getUid();
        Query query =  db.collection("users").document(id).collection("savedExercises")
                .whereEqualTo("title", currentSavedExercise.getTitle())
                .whereEqualTo("body", currentSavedExercise.getBody())
                .whereEqualTo("answer",currentSavedExercise.getAnswer());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult()) {
                        db.collection("users").document(id).collection("savedExercises")
                                .document(document.getId()).delete();


                    }
                }

        });

        String message;
        if(prefs.getBoolean(MainActivity.TAG_CURRENT_CONNECTION,true))
            message=getResources().getString(R.string.unsave_exercise);
        else
            message=getResources().getString(R.string.unsave_exercise)+" "+getResources().getString(R.string.offline_mode);

        currentSavedExercise=null;
        btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayLight)));
        Snackbar.make(btnSave, message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveExercise();

                    }
                }).show();
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


    @Override
    public void onDestroy() {
        task.cancel(true);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(currentSavedExercise!=null)
        {

            outState.putParcelable("current",currentSavedExercise);
        }
        outState.putParcelable("current2",currentExercise);

    }
}
