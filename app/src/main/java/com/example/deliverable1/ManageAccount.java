package com.example.deliverable1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageAccount extends AppCompatActivity implements AdminEditAccounts.ExampleDialogListener {

    InstructorDatabase instructorDatabase;
    MemberDatabase memberDatabase;
    ListView instructorListView;
    ListView memberListView;
    int index;
    HashMap<String, String> instructorList;
    HashMap<String, String> memberList;
    List<HashMap<String, String>> listInstructors;
    List<HashMap<String, String>> listMembers;
    SimpleAdapter instructorAdapter;
    SimpleAdapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        instructorListView = (ListView) findViewById(R.id.listview_instructors);
        memberListView = (ListView) findViewById(R.id.listview_members);

        instructorDatabase = MainActivity.getInstructorDatabase();
        memberDatabase = MainActivity.getMemberDatabase();
        SQLiteDatabase dbI = instructorDatabase.getWritableDatabase();
        SQLiteDatabase dbM = memberDatabase.getWritableDatabase();
        String userAccount;
        String userPW;

        HashMap<String, String> resultMap;

        instructorList = new HashMap<>();
        memberList = new HashMap<>();

        listInstructors = new ArrayList<>();
        listMembers = new ArrayList<>();

        instructorAdapter = new SimpleAdapter(this, listInstructors, R.layout.list_item, new String[]{"Username", "Password"},
                new int[]{R.id.text1, R.id.text2});
        memberAdapter = new SimpleAdapter(this, listMembers, R.layout.list_item2, new String[]{"Username", "Password"},
                new int[]{R.id.text11, R.id.text22});

        // This populates our listviews with current accounts whenever the page is opened

        Cursor cursorI = dbI.rawQuery("select * from instructors", null);

        if (cursorI.moveToFirst()) {
            // Populates ListView List with all classes currently in Instructor Database
            while (!cursorI.isAfterLast()) {
                userAccount = cursorI.getString(0);
                userPW = cursorI.getString(1);

                instructorList.put(userAccount, userPW);
                resultMap = new HashMap<>();
                resultMap.put("Username", userAccount);
                resultMap.put("Password", userPW);
                listInstructors.add(resultMap);

                cursorI.moveToNext();
            }
            instructorListView.setAdapter(instructorAdapter);
        }
        cursorI.close();

        Cursor cursorM = dbM.rawQuery("select * from members", null);
        if (cursorM.moveToFirst()) {
            // Populates ListView List with all classes currently in Member Database
            while (!cursorM.isAfterLast()) {
                userAccount = cursorM.getString(1);
                userPW = cursorM.getString(2);

                memberList.put(userAccount, userPW);
                resultMap = new HashMap<>();
                resultMap.put("Username", userAccount);
                resultMap.put("Password", userPW);
                listMembers.add(resultMap);

                cursorM.moveToNext();
            }
            memberListView.setAdapter(memberAdapter);
        }
        cursorM.close();

        instructorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                openDialog(instructorListView);
                instructorAdapter.notifyDataSetChanged();
            }
        });

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                openDialog(memberListView);
                memberAdapter.notifyDataSetChanged();
            }
        });
    }

    public void openDialog(ListView select) {
        AdminEditAccounts exampleDialog = new AdminEditAccounts(select);
        exampleDialog.show(getSupportFragmentManager(), "Remove Account");
    }

    public void deleteAccount(ListView accountType) {
        SQLiteDatabase db;
        String tempName;

        if (accountType.equals(instructorListView)) {
            instructorDatabase = MainActivity.getInstructorDatabase();
            db = instructorDatabase.getWritableDatabase();
            tempName = listInstructors.get(index).get("Username").toString();
            String[] nameArgs = { tempName };
            db.delete("instructors", "username LIKE ?", nameArgs);
            listInstructors.remove(index);
            instructorAdapter.notifyDataSetChanged();
            instructorListView.setAdapter(instructorAdapter);
            Toast.makeText(ManageAccount.this, "Removed Account", Toast.LENGTH_SHORT).show();
        }
        else if (accountType.equals(memberListView)) {
            memberDatabase = MainActivity.getMemberDatabase();
            db = memberDatabase.getWritableDatabase();
            tempName = listMembers.get(index).get("Username").toString();
            String[] nameArgs = { tempName };
            db.delete("members", "username LIKE ?", nameArgs);
            listMembers.remove(index);
            memberAdapter.notifyDataSetChanged();
            memberListView.setAdapter(memberAdapter);
            Toast.makeText(ManageAccount.this, "Removed Account", Toast.LENGTH_SHORT).show();
        }
        else {
            System.out.println("ERROR");
        }
    }
}














































