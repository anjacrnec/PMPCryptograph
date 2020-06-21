package com.example.pmpcryptograph.misc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pmpcryptograph.R;

public class ConnectionLossDialog extends AppCompatDialogFragment {

    CheckBox cbMessage;
    Button btn;
    private ConnectionLossDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(ConnectionLossDialogListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_connection_loss,null);
        builder.setView(view);

        btn=view.findViewById(R.id.btnDialogOk);
        cbMessage=view.findViewById(R.id.cbMessage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status=cbMessage.isChecked();
                listener.getCheckboxStatus(status);
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public interface ConnectionLossDialogListener
    {
        void getCheckboxStatus(boolean status);
    }
}
