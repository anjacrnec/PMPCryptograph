package com.example.pmpcryptograph.main;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmpcryptograph.R;
import com.example.pmpcryptograph.exercise.Exercise;
import com.example.pmpcryptograph.exercise.SavedExercise;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import net.cachapa.expandablelayout.ExpandableLayout;

public class SavedExerciseAdapter extends FirestoreRecyclerAdapter<SavedExercise, SavedExerciseAdapter.SavedExerciseHolder> {

    private onItemClickListener listener;
    private Context con;


    public SavedExerciseAdapter(@NonNull FirestoreRecyclerOptions<SavedExercise> options, Context con) {
        super(options);
        this.con=con;

    }

    @Override
    protected void onBindViewHolder(@NonNull SavedExerciseHolder holder, int position, @NonNull SavedExercise model) {

        String title=model.getTitle();
        holder.txtTitleCode.setText(title);

        holder.txtBodyCode.setText(model.getBody());
        Resources res=con.getResources();
        String cipher="";
        switch (title)
        {
            case "Caeser":
                holder.txtTitle.setText(res.getString(R.string.caeser));
                cipher=res.getString(R.string.caeser_encryption);
                break;
            case "Affine":
                holder.txtTitle.setText(res.getString(R.string.affine));
                cipher=res.getString(R.string.affine_cipher);
                break;
            case "Vigenere":
                holder.txtTitle.setText(res.getString(R.string.vigenere));
                cipher=res.getString(R.string.vignere_cipher);
                break;
            case "Playfair":
                holder.txtTitle.setText(res.getString(R.string.playfair));
                cipher=res.getString(R.string.playfair_cipher);
                break;
            case "Orthogonal":
                holder.txtTitle.setText(res.getString(R.string.ortho));
                cipher=res.getString(R.string.trans_ortho_cipher);
                break;
            case "ReverseOrthogonal":
                holder.txtTitle.setText(res.getString(R.string.reverse));
                cipher=res.getString(R.string.trans_ortho_boustrophedon_cipher);
                break;
            case "Diagonal":
                holder.txtTitle.setText(res.getString(R.string.diagonal));
                cipher=res.getString(R.string.trans_diagonal_cipher);
                break;
        }

        String bodyShown;
            if (model.getType().equals(Exercise.ENCRYPT)) {

                bodyShown = res.getString(R.string.body_pt,
                        cipher,
                        res.getString(R.string.ek),
                        res.getString(R.string.pt),
                        model.getPt(),
                        model.getKey());

            } else {
                bodyShown = res.getString(R.string.body_pt,
                        cipher,
                        res.getString(R.string.dk),
                        res.getString(R.string.ct),
                        model.getCt(),
                        model.getKey());

            }


        holder.txtBody.setText(bodyShown);
        holder.txtAnswer.setText(model.getAnswer());


            if(model.getExpanded())
            {
                holder.expandableAnswer.expand();
            }
            else
                holder.expandableAnswer.collapse();



        if(model.getVisible())
        {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.expandableExercise.expand();
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setMargins(10, 20, 10, 0);
            holder.itemView.requestLayout();

        }
        else {
            holder.itemView.setVisibility(View.INVISIBLE);
            holder.expandableExercise.collapse();
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setMargins(10, 0, 10, 0);
            holder.itemView.requestLayout();

        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        TextView txtTitleCode;
        TextView txtBodyCode;
        ExpandableLayout expandableAnswer;
        ExpandableLayout expandableExercise;

        public SavedExerciseHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtSavedExerciseTitle);
            txtTitleCode=itemView.findViewById(R.id.txtTitleCode);
            txtBodyCode=itemView.findViewById(R.id.txtBodyCode);
            txtBody=itemView.findViewById(R.id.txtSavedExerciseBody);
            txtAnswer=itemView.findViewById(R.id.txtSaveExerciseAnswer);
            expandableAnswer=itemView.findViewById(R.id.layoutAnswerExpandible);
            expandableExercise=itemView.findViewById(R.id.layoutAnswerExpandible2);


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
                       listener.onViewClick(getSnapshots().getSnapshot(position),position,itemView);
                    return false;
                }
            });


        }
    }


    public interface onItemClickListener
    {
        void onItemClick(DocumentSnapshot documentSnapshot, int position, boolean isExpanded);
        void onViewClick(DocumentSnapshot documentSnapshot,int position,View view);

    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
        this.listener=listener;
    }


}
