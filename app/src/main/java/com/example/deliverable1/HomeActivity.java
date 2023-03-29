package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements InstructorCreateClassDialog.InstructorDialogListener {

    TextView message;
    Bundle extras;

    Button buttonInstructorCreateClass;
    Button buttonFindInstructorClass;
    Button buttonFindByInstructorName;
    Button buttonEditClasses;
    Button buttonViewClasses;

    ListView listview;
    HashMap<String, String> list;
    ArrayList<HashMap<String, String>> arrayHashes;
    SimpleAdapter simpleAdapter;

    ClassDatabase classDatabase;

    EditText classInput;
    EditText instructorInput;

    String username;

    static String className;
    static String instructorName;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        message = findViewById(R.id.message);
        extras = getIntent().getExtras();
        listview = findViewById(R.id.listview_home);

        buttonInstructorCreateClass = findViewById(R.id.button_createClass);
        buttonFindInstructorClass = findViewById(R.id.button_findByClassName);
        buttonFindByInstructorName = findViewById(R.id.button_findByInstructorName);
        buttonEditClasses = findViewById(R.id.button_EditClass);
        buttonViewClasses = findViewById(R.id.button_viewAll);

        classInput = findViewById(R.id.editText_instructor_class_name);
        instructorInput = findViewById(R.id.editText_instructor_name);

        classDatabase = MainActivity.getClassDatabase();

        arrayHashes = new ArrayList<HashMap<String, String>>();

        simpleAdapter = new SimpleAdapter(this, arrayHashes, R.layout.list_item4, new String[] {"classType", "instructorName", "classDays"}, new int[]{R.id.text1111, R.id.text2222, R.id.text3333});

        buildList("*","*");

        String role = extras.getString("role");
        username = extras.getString("username");
        message.setText(new StringBuilder().append("Welcome ").append(username).append("!").append(" You are logged in as ").append(role).append(".").toString());

        buttonInstructorCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        buttonFindInstructorClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                className = classInput.getText().toString();
                instructorName = "";

                if (checkInfo()) {
                    buildList(className, "classType");
                }
                if (arrayHashes.isEmpty()) {
                    noClasses();
                    buildList("*","*");
                }

            }
        });

        buttonFindByInstructorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                className = "";
                instructorName = instructorInput.getText().toString();

                if (checkInfo()) {
                    buildList(instructorName, "instructorName");
                }
                if (arrayHashes.isEmpty()) {
                    noClasses();
                    buildList("*","*");
                }

            }
        });

        buttonEditClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(HomeActivity.this, InstructorEditClass.class);
                intent3.putExtra("username", username);
                HomeActivity.this.startActivity(intent3);
            }
        });

        buttonViewClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildList("*","*"); // TEST THIS -- make sure resets to all classes
                removeMessage();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                index = i;

                openViewClassDialog();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        buildList("*","*");
        // Little cluttered screen, welcome message no longer needed - removing on refresh will look cleaner
    }

    public void removeMessage() {
        message.setText("");
    }

    //this will update listview based on search term, will be modular so term is a parameter
    // with more time itd be nice to use an enum rather than the String 'columnName'
    public void buildList(String searchTerm, String columnName) {
        classDatabase = MainActivity.getClassDatabase();
        arrayHashes.clear();
        SQLiteDatabase database = classDatabase.getWritableDatabase();
        Cursor cursor;
        if (searchTerm.equals("*")) {
            cursor = database.rawQuery("select * from instructorClasses", null);
        }
        else {
            cursor = database.rawQuery("select * from instructorClasses WHERE " + columnName + " = ?", new String[] { searchTerm });
        }

        String IName;
        String cName;
        String day;

        if (cursor.moveToFirst()) {
            // Populates listView with all classes currently in Class Database
            while (!cursor.isAfterLast()) {
                IName = cursor.getString(1);
                cName = cursor.getString(2);
                day = cursor.getString(3);

                HashMap<String,String> resultMap = new HashMap<>();
                resultMap.put("classType", cName);
                resultMap.put("instructorName", IName);
                resultMap.put("classDays", day);
                arrayHashes.add(resultMap);

                cursor.moveToNext();
            }

            listview.setAdapter(simpleAdapter);
        }
        cursor.close();
    }

    public void noClasses() {
        Toast.makeText(this, "No classes found! Please try another search term. Resetting list to all scheduled classes.", Toast.LENGTH_SHORT).show();
    }

    public boolean checkInfo() {

        if (className.equals("") && instructorName.equals("")){
            Toast.makeText(this, "Please enter in the proper field!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void openDialog(){
        InstructorCreateClassDialog instructorCreateClassDialog = new InstructorCreateClassDialog(username);
        instructorCreateClassDialog.show(getSupportFragmentManager(), "Instructor Create Class Dialog");
    }

    public void openViewClassDialog(){
        ArrayList<String> items = getItemInfo();

        if (items != null){
            ViewClassDialog viewClass = new ViewClassDialog();

            Bundle bundle = new Bundle();

            bundle.putStringArrayList("items", items);

            viewClass.setArguments(bundle);

            viewClass.show((HomeActivity.this).getSupportFragmentManager(), "Class View Dialog");
        }
    }

    @Override
    public void insertData(String classType, String day, String hours, String difficulty, String cap, String time) {

        classDatabase = MainActivity.getClassDatabase();

        SQLiteDatabase db = classDatabase.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put("instructorName", username);
        content.put("classType", classType);
        content.put("classDays", day);
        content.put("classHours", hours);
        content.put("classDiff", difficulty);
        content.put("classCap", cap);
        content.put("startTime", time);

        long num = db.insert("instructorClasses", null, content);

        if (num != -1){
            Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();

            HashMap<String, String> resultsMap = new HashMap<String, String>();
            resultsMap.put("classType", classType);
            resultsMap.put("instructorName", username);
            resultsMap.put("classDays", day);
            resultsMap.put("classHours", hours);
            resultsMap.put("classDiff", difficulty);
            resultsMap.put("classCap", cap);
            resultsMap.put("startTime", time);

            arrayHashes.add(resultsMap);

            listview.setAdapter(simpleAdapter);
        }
    }

    public ArrayList<String> getItemInfo() {

        ArrayList<String> items = new ArrayList<>();
        SQLiteDatabase db = classDatabase.getWritableDatabase();

        String cType = new String(arrayHashes.get(index).get("classType").toString());
        String iName = new String(arrayHashes.get(index).get("instructorName").toString());
        String day = new String(arrayHashes.get(index).get("classDays").toString());

        Cursor cursor = db.rawQuery("select * from instructorClasses WHERE instructorName = ? AND classType = ? AND classDays = ?", new String[] {iName, cType, day});
        cursor.moveToFirst(); // if this fails, we have an issue where our click isn't corresponding correctly to the arrayHashes array

        for (int i = 1; i < 8; i++) {
            items.add(cursor.getString(i));
        }
        cursor.close();
        return items;
    }
}