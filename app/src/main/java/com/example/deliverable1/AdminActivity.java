package com.example.deliverable1;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements AdminEditClass.ExampleDialogListener{

    ClassDatabase classDatabase;
    EditText className;
    EditText classDesc;
    Button addClass;
    Button manageAccounts;
    ListView listview;
    int index;
    HashMap<String, String> list;
    List<HashMap<String, String>> listClasses;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        className = findViewById(R.id.editText_class_name);
        classDesc = findViewById(R.id.editText_class_description);
        addClass = findViewById(R.id.button_addClass);
        manageAccounts = findViewById(R.id.button_accounts);
        listview = (ListView) findViewById(R.id.listview_classes);

        classDatabase = MainActivity.getClassDatabase();
        SQLiteDatabase db = classDatabase.getWritableDatabase();
        String cName;
        String cDesc;
        HashMap<String, String> resultMap;

        list = new HashMap<>();
        listClasses = new ArrayList<>();
        adapter = new SimpleAdapter(this, listClasses, R.layout.list_item, new String[]{"Class", "Description"}, new int[]{R.id.text1, R.id.text2});

        Cursor cursor = db.rawQuery("select * from classes", null);
        if (cursor.moveToFirst()) {
            // Populates ListView List with all classes currently in Class Database
            while (!cursor.isAfterLast()) {
                cName = cursor.getString(0);
                cDesc = cursor.getString(1);

                list.put(cName, cDesc);
                resultMap = new HashMap<>();
                resultMap.put("Class", cName);
                resultMap.put("Description", cDesc);
                listClasses.add(resultMap);

                cursor.moveToNext();
            }
            listview.setAdapter(adapter);
        }
        cursor.close();

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String class_name = className.getText().toString();
                String class_desc = classDesc.getText().toString();

                if (class_name == null || class_name.length() == 0){
                    Toast.makeText(AdminActivity.this, "Enter in a Class Name", Toast.LENGTH_SHORT).show();
                }

                else if (class_desc == null || class_desc.length() == 0){
                    Toast.makeText(AdminActivity.this, "Enter in a Class Description", Toast.LENGTH_SHORT).show();
                }

                else{
                    ContentValues values = new ContentValues();
                    values.put("className", class_name);
                    values.put("classDesc", class_desc);
                    long num = db.insert("classes", null, values); // returns -1 if conflict

                    if (num != -1) {
                        list.put(class_name, class_desc);

                        HashMap<String, String> resultsMap = new HashMap<>();
                        resultsMap.put("Class", class_name);
                        resultsMap.put("Description", class_desc);

                        listClasses.add(resultsMap);
                        listview.setAdapter(adapter);
                        Toast.makeText(AdminActivity.this, "Added Class", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AdminActivity.this, "Class Name Already Exists", Toast.LENGTH_SHORT).show();
                    }

                    className.setText("");
                    classDesc.setText("");
                }

            }
        });

        manageAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AdminActivity.this, ManageAccount.class);

               AdminActivity.this.startActivity(intent2);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                openDialog();
                adapter.notifyDataSetChanged();

            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // CAN REMOVE THIS
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AdminActivity.this, "Class Removed", Toast.LENGTH_SHORT).show();

                listClasses.remove(i);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

    }

    public void openDialog() {
        AdminEditClass exampleDialog = new AdminEditClass();
        exampleDialog.show(getSupportFragmentManager(), "Edit Class");
    }

    @Override
    public void applyTexts(String username, String password) {

        classDatabase = MainActivity.getClassDatabase();
        SQLiteDatabase db = classDatabase.getWritableDatabase();

        String tempName = listClasses.get(index).get("Class").toString();
        String tempDescription = listClasses.get(index).values().toArray()[0].toString();
        if(!username.equals(tempName) || !password.equals(tempDescription)){
            String class_name = username;
            String class_desc = password;

            if (class_name.equals("") || class_name.length() == 0){
                class_name=tempName;
            }

            else if (class_desc.equals("") || class_desc.length() == 0){
                class_desc=tempDescription;
            }

            ContentValues values = new ContentValues();
            values.put("className", class_name);
            values.put("classDesc", class_desc);
            String[] nameArgs = { tempName, tempDescription };
            db.update("classes", values, "className LIKE ? and classDesc LIKE ?", nameArgs);


            listClasses.remove(index);
            list = new HashMap<>();

            adapter.notifyDataSetChanged();

            list.put(class_name, class_desc);

            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("Class", class_name);
            resultsMap.put("Description", class_desc);

            listClasses.add(resultsMap);

            listview.setAdapter(adapter);

            className.setText("");
            classDesc.setText("");
            Toast.makeText(AdminActivity.this, "Edited Class", Toast.LENGTH_SHORT).show();

            adapter.notifyDataSetChanged();
        }
    }

    public void deleteClass() {
        classDatabase = MainActivity.getClassDatabase();
        SQLiteDatabase db = classDatabase.getWritableDatabase();

        String tempName = listClasses.get(index).get("Class").toString();
        String[] nameArgs = { tempName };

        db.delete("classes", "className LIKE ?", nameArgs);
        listClasses.remove(index);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        Toast.makeText(AdminActivity.this, "Removed Class", Toast.LENGTH_SHORT).show();
    }
}


