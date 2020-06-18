package com.example.pmpcryptograph;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;


public class SavedFragment extends Fragment {

    RecyclerView recycler;
    private FirebaseAuth fbAuth;
    private FirebaseFirestore db;
    String id;
    int posScroll;
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
            recycler=v.findViewById(R.id.rviewExercises);
           recycler.setHasFixedSize(true);


            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            } else {
                recycler.setLayoutManager(new LinearLayoutManager((getActivity().getApplicationContext())));
            }

            recycler.setAdapter(adapter);
            recycler.setSaveEnabled(true);

            adapter.setOnItemClickListener(new SavedExerciseAdapter.onItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position,boolean isExpanded) {
                    if(isExpanded) {
                        documentSnapshot.getReference().update("expanded", false);

                    }
                    else {
                        documentSnapshot.getReference().update("expanded", true);

                    }
                }

                @Override
                public void onViewClick(DocumentSnapshot documentSnapshot, int position,View view) {

                    SavedExercise deletedExercise=documentSnapshot.toObject(SavedExercise.class);
                    deleteExercise(deletedExercise);



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
                    case 0:
                        filterAll();
                        editor.putString("FILTER","all");
                        editor.apply();
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

    public void deleteExercise(SavedExercise del)
    {

        currentDeletedexercise=del;
        Query query =  db.collection("users").document(id).collection("savedExercises")
                .whereEqualTo("title", currentDeletedexercise.getTitle())
                .whereEqualTo("body", currentDeletedexercise.getBody())
                .whereEqualTo("answer",currentDeletedexercise.getAnswer());

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
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restorExercise(currentDeletedexercise);
                    }
                }).show();

    }

    public void restorExercise(SavedExercise del)
    {
        currentDeletedexercise=del;
       db.collection("users").document(id).collection("savedExercises").add(currentDeletedexercise)
               .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {

                   }
               });

        String message;
        if(prefs.getBoolean(MainActivity.TAG_CURRENT_CONNECTION,true))
            message=getResources().getString(R.string.restore_exercise);
        else
            message=getResources().getString(R.string.restore_exercise)+" "+getResources().getString(R.string.offline_mode);

        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //currentDeletedexercise=null;
                        deleteExercise(currentDeletedexercise);
                    }
                }).show();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


