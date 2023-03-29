package com.example.deliverable1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstructorCreateClassDialog extends AppCompatDialogFragment {
    private Spinner classType;
    private Spinner classDiff;
    private Spinner classHours;
    private Spinner classDays;
    private Spinner classTime;
    private EditText maxCap;

    private InstructorDialogListener listener;

    private String instructorName;
    private String className;
    private String dayOfWeek;
    private String startTime;
    private String duration;
    private String difficulty;
    private String capacity;

    public InstructorCreateClassDialog(String name) {
        instructorName = name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_instructor_create_class, null);

        ClassDatabase classDatabase = MainActivity.getClassDatabase(); // Getting ClassDatabase for spinner.

        builder.setView(view).setTitle("Create a new Class")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add Class", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Pass the data to .
                        capacity = maxCap.getText().toString();

                        boolean isNumeric = true;
                        String instructorConflict = classDatabase.itemExists(className, dayOfWeek);
                        int num = 0;

                        try{
                            num = Integer.parseInt(capacity);
                        } catch (NumberFormatException e){
                            isNumeric = false;
                        }
                        if (num <= 0) {
                            Toast.makeText(view.getContext(), "Invalid Capacity. Please enter a number greater than 0.", Toast.LENGTH_SHORT).show();
                        }
                        else if (capacity.isEmpty() || !isNumeric || className.isEmpty() || dayOfWeek.isEmpty() || duration.isEmpty()
                        || difficulty.isEmpty() || startTime.isEmpty()){
                            Toast.makeText(view.getContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                        }

                        //Search Database to check if instructor and class exist on the same day.
                        else if (instructorConflict == null){
                            listener.insertData(className, dayOfWeek, duration, difficulty, capacity, startTime);

                        }

                        else{
                            String conflictMessage = new String(new StringBuilder().append("Cannot add class. ").append(instructorConflict).append(" is already teaching ").append(className).append(" on ").append(dayOfWeek).append(".").toString());
                            Toast.makeText(view.getContext(), conflictMessage, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        classDiff = view.findViewById(R.id.difficulty_spinner);
        classHours = view.findViewById(R.id.hours_spinner);
        classDays = view.findViewById(R.id.day_spinner);
        classType = view.findViewById(R.id.classType_spinner);
        classTime = view.findViewById(R.id.time_spinner);
        maxCap = view.findViewById(R.id.editText_max_capacity);

        List<String> classTypes = classDatabase.getAllClassTypes(); // Get the list of classes.

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.difficulty_level, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(view.getContext(), R.array.time_interval, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(view.getContext(), R.array.day_of_week, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(view.getContext(), R.array.start_time, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, classTypes);

        classDiff.setAdapter(adapter);
        classHours.setAdapter(adapter2);
        classDays.setAdapter(adapter3);
        classType.setAdapter(adapter4);
        classTime.setAdapter(adapter5);

        classDiff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficulty = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                difficulty = "";
            }
        });

        classHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                duration = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                duration = "";
            }
        });

        classDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dayOfWeek = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dayOfWeek = "";
            }
        });

        classType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                className = "";
            }
        });

        classTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startTime = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                startTime = "";
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (InstructorDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InstructorDialogListener");
        }

    }

    public interface InstructorDialogListener{
        void insertData(String classType, String day, String hours, String difficulty, String cap, String start);

    }


}
