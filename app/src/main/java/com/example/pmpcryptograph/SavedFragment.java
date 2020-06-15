package com.example.pmpcryptograph;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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


public class SavedFragment extends Fragment {

    private FirebaseAuth fbAuth;
    private FirebaseFirestore db;
    String id;
    private CollectionReference savedRef;
    private SavedExerciseAdapter adapter;
    private SavedExercise currentDeletedexercise;
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
            savedRef=db.collection("users").document(id).collection("savedExercises");
            Query query=savedRef.orderBy("time", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<SavedExercise>options=new FirestoreRecyclerOptions.Builder<SavedExercise>()
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
                public void onViewClick(DocumentSnapshot documentSnapshot, int position) {

                    deleteExercise(documentSnapshot);

                }


            });





            return v;
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


