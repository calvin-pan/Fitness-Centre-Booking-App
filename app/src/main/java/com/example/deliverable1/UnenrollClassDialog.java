package com.example.deliverable1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class UnenrollClassDialog extends AppCompatDialogFragment {

    private UnenrollClassDialogListener listener;

    TextView className;
    TextView instructorName;
    TextView classDay;
    TextView sTime;
    TextView difficulty;
    TextView capacity;

    ArrayList<String> items;

    Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_view_class, null);

        bundle = getArguments();

        items = bundle.getStringArrayList("items");

        className = view.findViewById(R.id.text_class_name);
        instructorName = view.findViewById(R.id.text_instructor_name);
        classDay = view.findViewById(R.id.text_class_day);
        sTime = view.findViewById(R.id.text_class_length);
        difficulty = view.findViewById(R.id.text_difficulty);
        capacity = view.findViewById(R.id.text_capacity);

        instructorName.setText(items.get(0));
        className.setText(items.get(1));
        classDay.setText(items.get(2));
        sTime.setText(ViewMemberClassDialog.createClassTime(items.get(6), items.get(3)));
        difficulty.setText(items.get(4));
        capacity.setText(items.get(5));

        builder.setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("Unenroll", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { listener.unenroll();                   }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (UnenrollClassDialogListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UnenrollClassDialogListener");
        }
    }

    public interface UnenrollClassDialogListener {
        int unenroll();
    }


}
