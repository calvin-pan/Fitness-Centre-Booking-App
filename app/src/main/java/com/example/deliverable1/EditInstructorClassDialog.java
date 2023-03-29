package com.example.deliverable1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EditInstructorClassDialog extends AppCompatDialogFragment {

    private Spinner classDiff;
    private Spinner classHours;
    private Spinner classDays;
    private Spinner classTime;
    private EditText maxCap;

    private String dayOfWeek;
    private String startTime;
    private String duration;
    private String difficulty;
    private String capacity;

    private String instructorName;
    private String className;
    private String sDay;
    private String sTime;
    private String sDuration;
    private String sDifficulty;
    private String sCapacity;

    private String[] decDay = MainActivity.getdecDay();
    private String[] dDuration = MainActivity.getdDuration();
    private String[] dDifficulty = MainActivity.getdDifficulty();
    private String[] dTime = MainActivity.getdTime();

    private int nDay;
    private int nDuration;
    private int nDifficulty;
    private int nTime;

    public EditInstructorClassDialog(String name, String type1, String day, String dur, String diff, String cap, String time) {
        instructorName = name;
        className = type1;
        sDay = day;
        sTime = time;
        sDuration = dur;
        sDifficulty = diff;
        sCapacity = cap;
    }

    private EditInstructorDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edit_instructor_class, null);

        ClassDatabase classDatabase = MainActivity.getClassDatabase(); // Getting ClassDatabase for spinner.

        decode(); // figure out current spinner values for all

        classDiff = view.findViewById(R.id.difficulty_spinner);
        classHours = view.findViewById(R.id.hours_spinner);
        classDays = view.findViewById(R.id.day_spinner);
        classTime = view.findViewById(R.id.time_spinner);
        maxCap = view.findViewById(R.id.editText_max_capacity);
        maxCap.setText(sCapacity);
        // set to class' current capacity

        builder.setView(view).setTitle("Edit Class")
                .setNegativeButton("View Enrolled", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ArrayList<String> enrollInfo = new ArrayList<>();
                        enrollInfo.add(instructorName);
                        enrollInfo.add(className);
                        enrollInfo.add(sDay);

                        listener.viewEnrolled(enrollInfo);
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        capacity = maxCap.getText().toString();

                        boolean isNumeric = true;
                        boolean possibleConflict = false;
                        String instructorConflict = classDatabase.itemExists(className, dayOfWeek);
                        int num = 0;

                        if ((!dayOfWeek.equals(sDay)) || (!startTime.equals(sTime)) || (!duration.equals(sDuration))) {
                            possibleConflict = true;
                        }

                        try{
                            num = Integer.parseInt(capacity);
                        } catch (NumberFormatException e){
                            isNumeric = false;
                        }
                        if (num <= 0) {
                            Toast.makeText(view.getContext(), "Invalid Capacity. Please enter a number greater than 0.", Toast.LENGTH_SHORT).show();
                        }
                        else if (capacity.isEmpty() || isNumeric == false){
                            Toast.makeText(view.getContext(), "Please enter an integer number", Toast.LENGTH_SHORT).show();
                        }

                        else if (dayOfWeek.isEmpty() || duration.isEmpty()
                                || difficulty.isEmpty() || startTime.isEmpty()){
                            Toast.makeText(view.getContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                        }

                        else if (sDay.equals(dayOfWeek)) {
                            listener.editData(dayOfWeek, duration, difficulty, capacity, startTime, sDay, possibleConflict);
                        }

                        else if (instructorConflict != null) {
                            String conflictMessage = new String(new StringBuilder().append("Cannot make changes, ").append(instructorConflict).append(" is already teaching ").append(className).append(" on ").append(dayOfWeek).append(".").toString());
                            Toast.makeText(view.getContext(), conflictMessage, Toast.LENGTH_SHORT).show();
                        }

                        else{

                            listener.editData(dayOfWeek, duration, difficulty, capacity, startTime, sDay, possibleConflict);

                        }

                    }
                })
                .setNeutralButton("Remove Class", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.deleteClass();
                    }
                });


        List<String> classTypes = classDatabase.getAllClassTypes(); // Get the list of classes.

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.difficulty_level, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(view.getContext(), R.array.time_interval, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(view.getContext(), R.array.day_of_week, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(view.getContext(), R.array.start_time, android.R.layout.simple_spinner_item);

        classDiff.setAdapter(adapter);
        classHours.setAdapter(adapter2);
        classDays.setAdapter(adapter3);
        classTime.setAdapter(adapter5);

        classDiff.setSelection(nDifficulty);
        classHours.setSelection(nDuration);
        classDays.setSelection(nDay);
        classTime.setSelection(nTime);

        classDiff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficulty = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                difficulty = sDifficulty;
            }
        });

        classHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                duration = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                duration = sDuration;
            }
        });

        classDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dayOfWeek = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dayOfWeek = sDay;
            }
        });

        classTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startTime = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                startTime = sTime;
            }
        });


        return builder.create();
    }

    public void decode() {
        int i;
        for (i = 0; i < decDay.length; i++) {
            if (decDay[i].equals(sDay)) {
                nDay = i;
                break;
            }
        }

        for (i = 0; i < dDuration.length; i++) {
            if (dDuration[i].equals(sDuration)) {
                nDuration = i;
                break;
            }
        }

        for (i = 0; i < dDifficulty.length; i++) {
            if (dDifficulty[i].equals(sDifficulty)) {
                nDifficulty = i;
                break;
            }
        }

        for (i = 0; i < dTime.length; i++) {
            if (dTime[i].equals(sTime)) {
                nTime = i;
                break;
            }
        }
    }

    public int getnDay() {
        return nDay;
    }

    public int getnDuration() {
        return nDuration;
    }

    public int getnDifficulty() {
        return nDifficulty;
    }

    public int getnTime() {
        return nTime;
    }

    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (EditInstructorDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement InstructorDialogListener");

        }

    }

    public interface EditInstructorDialogListener{
        void editData(String day, String hours, String difficulty, String cap, String start, String orgDay, boolean possibleConflict);
        void deleteClass();
        void viewEnrolled(ArrayList<String> list);
    }
}
