package com.example.pmpcryptograph;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmpcryptograph.exercise.SavedExercise;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import net.cachapa.expandablelayout.ExpandableLayout;

public class SavedExerciseAdapter extends FirestoreRecyclerAdapter<SavedExercise, SavedExerciseAdapter.SavedExerciseHolder> {

    private onItemClickListener listener;

    public SavedExerciseAdapter(@NonNull FirestoreRecyclerOptions<SavedExercise> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SavedExerciseHolder holder, int position, @NonNull SavedExercise model) {

        holder.txtTitle.setText(model.getTitle());
        holder.txtBody.setText(model.getBody());
        holder.txtAnswer.setText(model.getAnswer());
        if(model.getExpanded())
            holder.expandableAnswer.expand();
        else
            holder.expandableAnswer.collapse();


    }

    @NonNull
    @Override
    public SavedExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item,parent,false);
        return new SavedExerciseHolder(v);
    }

    public void deleteItem(int position)
    {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class SavedExerciseHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtBody;
        TextView txtAnswer;
        ExpandableLayout expandableAnswer;

        public SavedExerciseHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtSavedExerciseTitle);
            txtBody=itemView.findViewById(R.id.txtSavedExerciseBody);
            txtAnswer=itemView.findViewById(R.id.txtSaveExerciseAnswer);
            expandableAnswer=itemView.findViewById(R.id.layoutAnswerExpandible);


         /* txtBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();
                    boolean isExpanded=expandableAnswer.isExpanded();
                    if(position!=RecyclerView.NO_POSITION && listener!=null)
                        listener.onItemClick(getSnapshots().getSnapshot(position),position,isExpanded);
                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();
                    boolean isExpanded=expandableAnswer.isExpanded();
                    if(position!=RecyclerView.NO_POSITION && listener!=null)
                        listener.onItemClick(getSnapshots().getSnapshot(position),position,isExpanded);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position =getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null)
                        listener.onViewClick(getSnapshots().getSnapshot(position),position);
                    return false;
                }
            });


          /*  txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(expandableAnswer.isExpanded())
                        getSnapshots().getSnapshot(getAdapterPosition()).getReference().update("expanded",false);
                    else
                        getSnapshots().getSnapshot(getAdapterPosition()).getReference().update("expanded",true);
                }
            });

          itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteItem(getAdapterPosition());
                    return  false;
                }
            });*/

        }
    }

    public interface onItemClickListener
    {
        void onItemClick(DocumentSnapshot documentSnapshot, int position, boolean isExpanded);
        void onViewClick(DocumentSnapshot documentSnapshot,int position);

    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
        this.listener=listener;
    }


}
