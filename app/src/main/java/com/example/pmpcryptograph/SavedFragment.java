package com.example.pmpcryptograph;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pmpcryptograph.cryptography.AffineCipher;
import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.exercise.SavedExercise;
import com.facebook.login.LoginManager;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;


public class SavedFragment extends Fragment {

    private FirebaseAuth fbAuth;
    private FirebaseFirestore db;
    String id;
    private CollectionReference savedRef;
    private SavedExerciseAdapter adapter;
    private SavedExercise currentDeletedexercise;
    FirestoreRecyclerOptions<SavedExercise>options;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    List <String> listFilters;


    public SavedFragment() {
    }

        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {

            }
            setRetainInstance(true);
        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            View v=inflater.inflate(R.layout.fragment_saved, container, false);
            currentDeletedexercise=null;
            fbAuth = FirebaseAuth.getInstance();
            db=FirebaseFirestore.getInstance();
             id=fbAuth.getCurrentUser().getUid();

            prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
            editor=prefs.edit();


            Spinner spinFilter=v.findViewById(R.id.spinnerFilter);
            savedRef=db.collection("users").document(id).collection("savedExercises");
            Query query=savedRef.orderBy("time", Query.Direction.DESCENDING);
           options=new FirestoreRecyclerOptions.Builder<SavedExercise>()
                    .setQuery(query,SavedExercise.class)
                    .build();
            adapter=new SavedExerciseAdapter(options);
            RecyclerView recycler=v.findViewById(R.id.rviewExercises);
            //recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            recycler.setAdapter(adapter);


            adapter.setOnItemClickListener(new SavedExerciseAdapter.onItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position,boolean isExpanded) {
                    if(isExpanded)
                        documentSnapshot.getReference().update("expanded",false);
                    else
                        documentSnapshot.getReference().update("expanded",true);
                }

                @Override
                public void onViewClick(DocumentSnapshot documentSnapshot, int position,View view) {

                    deleteExercise(documentSnapshot);


                }


            });

            ExpandableLayout expandableFilter=v.findViewById(R.id.layoutFilterExpandible);
        Button btnFilter=v.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableFilter.toggle();
            }
        });

        spinFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:filterAll();
                    break;
                    case 1:
                        filterSpecific(Exercise.CAESER_CIPHER);
                        editor.putString("FILTER",Exercise.CAESER_CIPHER);
                        editor.apply();
                    break;
                    case 2:

                        filterSpecific(Exercise.AFFINE_CIPHER);
                        editor.putString("FILTER",Exercise.AFFINE_CIPHER);
                        editor.apply();
                    break;
                    case 3:

                        filterSpecific(Exercise.PLAYFAIR_CIPHER);
                        editor.putString("FILTER",Exercise.PLAYFAIR_CIPHER);
                        editor.apply();
                    break;
                    case 4:
                        filterSpecific(Exercise.VIGNERE_CIPHER);
                        editor.putString("FILTER",Exercise.VIGNERE_CIPHER);
                        editor.apply();
                    break;
                    case 5:
                        filterSpecific(Exercise.ORTHOGONAL_CIPHER);
                        editor.putString("FILTER",Exercise.ORTHOGONAL_CIPHER);
                        editor.apply();
                    break;
                    case 6:
                        filterSpecific(Exercise.REVERSE_ORTHOGONAL_CIPHER);
                        editor.putString("FILTER",Exercise.REVERSE_ORTHOGONAL_CIPHER);
                        editor.apply();
                    break;
                    case 7:

                        filterSpecific(Exercise.DIAGONAL_CPIHER);
                        editor.putString("FILTER",Exercise.DIAGONAL_CPIHER);
                        editor.apply();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



            return v;
        }

        public void initializaFilters()
        {
            listFilters=new ArrayList<String>();
            listFilters.add(Exercise.CAESER_CIPHER);
            listFilters.add(Exercise.AFFINE_CIPHER);
            listFilters.add(Exercise.PLAYFAIR_CIPHER);
            listFilters.add(Exercise.VIGNERE_CIPHER);
            listFilters.add(Exercise.ORTHOGONAL_CIPHER);
            listFilters.add(Exercise.REVERSE_ORTHOGONAL_CIPHER);
            listFilters.add(Exercise.DIAGONAL_CPIHER);
        }

    public void deleteExercise(DocumentSnapshot doc)
    {

        SavedExercise deletedExercise=doc.toObject(SavedExercise.class);
        currentDeletedexercise=deletedExercise;
        doc.getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(getView(), R.string.unsave_exercise, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                restorExercise(doc);
                            }
                        }).show();

            }
        });


    }

    public void filterAll()
    {
        db.collection("users").document(id).collection("savedExercises").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection("users").document(id).collection("savedExercises")
                                    .document(document.getId()).update("visible", true);
                        }
                    }
                });
    }

    public void filterOneSpecific(String cipher)
    {
        db.collection("users").document(id).collection("savedExercises").whereEqualTo("title",cipher).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection("users").document(id).collection("savedExercises")
                                    .document(document.getId()).update("visible", true);

                        }
                    }
                });
    }

    public void filterSpecific(String cipher)
    {
        Log.d("cipher pr", cipher);
        db.collection("users").document(id).collection("savedExercises").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String title=document.getString("title");
                            Log.d("title pr", title);
                            if(title.equals(cipher))
                               document.getReference().update("visible",true);
                            else
                                document.getReference().update("visible",false);


                        }
                    }
                });
    }

    public void restorExercise(DocumentSnapshot doc)
    {
        db.collection("users").document(id).collection("savedExercises").document().set(currentDeletedexercise).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Snackbar.make(getView(), R.string.restore_exercise, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.undo, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            currentDeletedexercise=null;
                                            deleteExercise(doc);
                                        }
                                    }).show();
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

}


