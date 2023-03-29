package com.example.deliverable1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberHomeActivity extends AppCompatActivity implements ViewMemberClassDialog.ViewMemberDialogListener {

    TextView message;
    Bundle extras;

    Button buttonFindInstructorClass;
    Button buttonFindByDayOfTheWeek;
    Button buttonViewClasses;
    Button buttonViewEnrolledClasses;

    ListView listview;
    HashMap<String, String> list;
    ArrayList<HashMap<String, String>> arrayHashes;
    SimpleAdapter simpleAdapter;

    ClassDatabase classDatabase;

    EditText classInput;
    EditText dayOfTheWeekInput;

    String username;

    static String className;
    static String dayOfTheWeek;

    private Bundle bundle;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_member);

        message = findViewById(R.id.message);
        extras = getIntent().getExtras();
        listview = findViewById(R.id.listview_home);

        buttonFindInstructorClass = findViewById(R.id.button_findByClassName);
        buttonFindByDayOfTheWeek = findViewById(R.id.button_findByDayOfTheWeek);
        buttonViewClasses = findViewById(R.id.button_viewAll);
        buttonViewEnrolledClasses = findViewById(R.id.button_viewEnrolledClasses);

        classInput = findViewById(R.id.editText_instructor_class_name);
        dayOfTheWeekInput = findViewById(R.id.editText_day_of_the_week);

        classDatabase = MainActivity.getClassDatabase();

        arrayHashes = new ArrayList<HashMap<String, String>>();

        simpleAdapter = new SimpleAdapter(this, arrayHashes, R.layout.list_item4, new String[] {"classType", "instructorName", "classDays"}, new int[]{R.id.text1111, R.id.text2222, R.id.text3333});

        buildList("*","*");

        String role = extras.getString("role");
        username = extras.getString("username");
        message.setText(new StringBuilder().append("Welcome ").append(username).append("!").append(" You are logged in as ").append(role).append(".").toString());

        buttonFindInstructorClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                className = classInput.getText().toString();
                dayOfTheWeek = "";

                if (checkInfo()) {
                    buildList(className, "classType");
                }
                if (arrayHashes.isEmpty()) {
                    noClasses();
                    buildList("*","*");
                }

            }
        });

        buttonFindByDayOfTheWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                className = "";
                dayOfTheWeek = dayOfTheWeekInput.getText().toString();

                if (checkInfo()) {
                    buildList(dayOfTheWeek, "classDays");
                }
                if (arrayHashes.isEmpty()) {
                    noClasses();
                    buildList("*","*");
                }

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

                openViewMemberClassDialog();
            }
        });

        buttonViewEnrolledClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemberEnrolledClassesActivity.class);

                intent.putExtra("username", username);

                startActivity(intent);
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

                //list.put(cName, cDesc);
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

        if (className.equals("") && dayOfTheWeek.equals("")){
            Toast.makeText(this, "Please enter in the proper field!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void openViewMemberClassDialog(){
        ArrayList<String> items = getItemInfo();

        if (items != null){
            ViewMemberClassDialog viewClass = new ViewMemberClassDialog();

            bundle = new Bundle();

            bundle.putStringArrayList("items", items);
            bundle.putString("username", username);

            viewClass.setArguments(bundle);

            viewClass.show((MemberHomeActivity.this).getSupportFragmentManager(), "Class View Dialog");
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

    public long enroll() {
        classDatabase = MainActivity.getClassDatabase();
        SQLiteDatabase db = classDatabase.getWritableDatabase();
        long num = -1;


        ArrayList<String> items = bundle.getStringArrayList("items");
        Integer[] changedStartTimeADuration = MainActivity.timeConversion(items.get(6), items.get(3));
        
        int startTime = changedStartTimeADuration[0];
        int length = changedStartTimeADuration[1];

        ContentValues values = new ContentValues();
        values.put("username", bundle.getString("username"));
        values.put("classType", items.get(1));
        values.put("instructorName", items.get(0));
        values.put("classDays", items.get(2));
        values.put("classHours", items.get(3));
        values.put("classDiff", items.get(4));
        values.put("classCap", items.get(5));
        values.put("startTime", items.get(6));

        if (checkEnrollment(values)) {
            Toast.makeText(this, "Already enrolled, cannot enroll again.", Toast.LENGTH_SHORT).show();
            return -1;
        }
        else if (this.checkConflict(bundle.getString("username"),startTime,items.get(2),length)){
            Toast.makeText(this, "A time conflict occurred! Operation Failed.", Toast.LENGTH_SHORT).show();
            return -1;
            }

        else if (classDatabase.checkFullClass(items.get(1),items.get(2))){
            Toast.makeText(this, "Class is Full! Operation Failed.", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else {
            num = db.insert("enrollment", null, values);
        }

        if(num ==-1){//database error
            Toast.makeText(this, "An error occurred! Operation Failed.", Toast.LENGTH_SHORT).show();
        }
        else {//if successfully inserted
            Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
        }

        return num;
    }

    private boolean checkEnrollment(ContentValues values) {
        classDatabase = MainActivity.getClassDatabase();
        SQLiteDatabase db = classDatabase.getWritableDatabase();
        Cursor cursor1 = db.rawQuery("select * from enrollment WHERE username = ? AND classType = ? AND instructorName = ? AND classDays = ?",
                new String[] {values.get("username").toString(), values.get("classType").toString(), values.get("instructorName").toString(), values.get("classDays").toString()});

        boolean check = (cursor1.moveToFirst());
        return check;
    }

    public boolean checkConflict(String userName, int startTime, String day, int length) {// function to check if there is any time conflicts between selected class and other enrolled classes
        ClassDatabase classd=MainActivity.getClassDatabase();
        SQLiteDatabase db = classd.getWritableDatabase();
        String start;
        String length2;
        Integer[] startTimeADuration;
        int cStartTime;
        int cLength;
        boolean conflict = false;

        Cursor cursor = db.rawQuery("select * from enrollment WHERE username = ? AND classDays = ?", new String[]{userName, day});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                start = cursor.getString(8);
                length2 = cursor.getString(5);
                startTimeADuration = MainActivity.timeConversion(start, length2);
                cStartTime = startTimeADuration[0];
                cLength = startTimeADuration[1];

                conflict = checkTimes(startTime, length, cStartTime, cLength);

                if (conflict) {
                    return true;
                }
                cursor.moveToNext();
            }
        }
        return false;//false otherwise
    }

    protected static boolean checkTimes(int startTime, int length, int cStartTime, int cLength) {
        if ((startTime <= cStartTime) && ((startTime + length) > (cStartTime))) {
            return true;
        }
        else if ((cStartTime <= startTime) && ((cStartTime + cLength) > (startTime))) {
            return true;
        }
        else {
            return false;
        }
    }

    protected void setBundle(String username, ArrayList<String> items) {
        bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putStringArrayList("items", items);
    }
}
