package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class ViewEnrolledActivity extends AppCompatActivity {

    Bundle extras;

    ListView listview;
    ArrayList<HashMap<String, String>> arrayHashes;
    SimpleAdapter simpleAdapter;

    ClassDatabase classDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrolled_member_view);

        extras = getIntent().getExtras();
        listview = findViewById(R.id.listview_class_members);

        classDatabase = MainActivity.getClassDatabase();
        SQLiteDatabase sq = classDatabase.getWritableDatabase();

        arrayHashes = new ArrayList<HashMap<String, String>>();
        ArrayList<String> holder = extras.getStringArrayList("enrollInfo");

        simpleAdapter = new SimpleAdapter(this, arrayHashes, R.layout.list_item5,
                new String[] {"memberName"}, new int[]{R.id.text11111});

        Cursor cursor = sq.rawQuery("select * from enrollment WHERE instructorName = ? AND classType = ? AND classDays = ?"
        , new String[] {holder.get(0), holder.get(1), holder.get(2)});

        String mName;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                mName = cursor.getString(1); // username

                HashMap<String,String> resultMap = new HashMap<>();
                resultMap.put("memberName", mName);
                arrayHashes.add(resultMap);

                cursor.moveToNext();
            }

            listview.setAdapter(simpleAdapter);
        }
        cursor.close();

    }
}
