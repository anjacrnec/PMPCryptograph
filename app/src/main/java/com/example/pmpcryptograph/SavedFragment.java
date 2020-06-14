package com.example.pmpcryptograph;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pmpcryptograph.exercise.SavedExercise;
import com.facebook.login.LoginManager;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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

         /*   ItemTouchHelper.SimpleCallback itemTouchHelperCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
                @Override
                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    return 0;
                }

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    if(direction==ItemTouchHelper.LEFT || direction== ItemTouchHelper.RIGHT) {
                        adapter.deleteItem(viewHolder.getAdapterPosition());
                        Toast.makeText(getActivity().getApplicationContext(), "swip", Toast.LENGTH_LONG).show();
                    }
                }
            };
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler);*/






            Button btnLoguot=v.findViewById(R.id.btnLogout);
            btnLoguot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fbAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                    getActivity().finish();;
                }
            });

            Button btnProba=v.findViewById(R.id.btnProba);
            btnProba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s=fbAuth.getCurrentUser().getDisplayName();
                    Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                }
            });

            return v;
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


