package com.example.deliverable1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewClassDialog extends AppCompatDialogFragment {

    TextView className;
    TextView instructorName;
    TextView classDay;
    TextView sTime;
    TextView difficulty;
    TextView capacity;

    ArrayList<String> items;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_view_class, null);

        Bundle bundle = getArguments();

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
        sTime.setText(createClassTime(items.get(6), items.get(3)));
        difficulty.setText(items.get(4));
        capacity.setText(items.get(5));

        builder.setView(view).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing.
            }
        });


        return builder.create();
    }

    // pull duration and start time, convert to number, add duration to start time, convert both back to string
    public static String createClassTime(String startTime, String duration) {
        char[] charHold = startTime.toCharArray();
        String timeHold = Character.toString(charHold[0]) + Character.toString(charHold[1]) + Character.toString(charHold[3]) + Character.toString(charHold[4]);
        String durHold;
        int start = Integer.parseInt(timeHold);
        int length;

        charHold = duration.toCharArray();
        if (charHold[2] == 'h') { // make sure this comparison is working correctly -- TEST!!!!!!!!!
            durHold = Character.toString(charHold[0]);
            length = Integer.parseInt(durHold) * 100;
        }
        else {
            durHold = Character.toString(charHold[0]);
            length = (Integer.parseInt(durHold) * 100) + 30;
        }
        int endNum = start + length;
        String endConvert = Integer.toString(endNum);
        if (endConvert.length() == 3) {
            endConvert = "0".concat(endConvert);
        }
        char[] endChar = endConvert.toCharArray();

        if (endChar[2] == '6') {
            endNum = endNum + 40;
            endConvert = Integer.toString(endNum);
            if (endConvert.length() == 3) {
                endConvert = "0".concat(endConvert);
            }
            endChar = endConvert.toCharArray();
        }

        String endTime = Character.toString(endChar[0]) + Character.toString(endChar[1]) + ":" + Character.toString(endChar[2]) + Character.toString(endChar[3]);
        String classTime = startTime + " - " + endTime;

        return classTime;
    }

}
