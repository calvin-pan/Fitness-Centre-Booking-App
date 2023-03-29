package com.example.deliverable1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AdminEditClass extends AppCompatDialogFragment {


        private EditText editTextName;
        private EditText editTextDescription;
        private ExampleDialogListener listener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_admin_edit_class, null);

            builder.setView(view)
                    .setTitle("Edit Class")
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = editTextName.getText().toString();
                            String description = editTextDescription.getText().toString();
                            listener.applyTexts(name, description);
                        }
                    })
                    .setNeutralButton("remove class", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.deleteClass();
                        }
                    });

            editTextName = view.findViewById(R.id.edit_username);
            editTextDescription = view.findViewById(R.id.edit_password);

            return builder.create();
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            try {
                listener = (ExampleDialogListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() +
                        "must implement ExampleDialogListener");
            }
        }

        public interface ExampleDialogListener {
            void applyTexts(String name, String description);
            void deleteClass();
        }
    }
