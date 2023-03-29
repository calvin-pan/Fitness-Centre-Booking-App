package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    InstructorDatabase instructorDatabase;
    MemberDatabase memberDatabase;
    private Button buttonRegister;
    private Button buttonLogin;
    private EditText usernameField;
    private EditText passwordField;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username_input);
        passwordField = findViewById(R.id.password_input);
        buttonRegister = findViewById(R.id.button_register);
        buttonLogin = findViewById(R.id.button_login);
        instructorDatabase = MainActivity.getInstructorDatabase();
        memberDatabase = MainActivity.getMemberDatabase();
        extras = getIntent().getExtras();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String role = extras.getString("role"); // "instructor" or "member"
                long numInstructor = -1;
                long numMember = -1;

                if(username.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else { // don't need to check, try to add and if there's conflict will return -1

                    if (role.equals("instructor")) {
                        SQLiteDatabase db = instructorDatabase.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("username", username);
                        values.put("password", password);
                        numInstructor = db.insert("instructors", null, values);
                    }
                    else if (role.equals("member")) {
                        SQLiteDatabase db = memberDatabase.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("username", username);
                        values.put("password", password);
                        numMember = db.insert("members", null, values);
                    }
                    else {
                        System.out.println("ERROR, ROLE NOT AS EXPECTED");
                    }

                    if(numInstructor != -1){

                        Toast.makeText(LoginActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("role", role);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else if (numMember != -1) {

                        Toast.makeText(LoginActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MemberHomeActivity.class);
                        intent.putExtra("role", role);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "User already exists! Please sign in.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String role = extras.getString("role");
                boolean checkInstructor = false;
                boolean checkMember = false;

                if(username.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (role.equals("instructor")) {
                        checkInstructor = instructorDatabase.checkAdmin(username, password);
                    }
                    else if (role.equals("member")) {
                        checkMember = memberDatabase.checkMember(username, password);
                    }
                    else {
                        System.out.println("ERROR, ROLE NOT AS EXPECTED ON LOGIN");
                    }

                    if(checkInstructor){

                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("role", role);
                        intent.putExtra("username", username);
                        startActivity(intent);

                        } else if(checkMember) {

                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MemberHomeActivity.class);
                        intent.putExtra("role", role);
                        intent.putExtra("username", username);
                        startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }