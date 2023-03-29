package com.example.deliverable1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivityAdmin extends AppCompatActivity {

    AdminDatabase adminDatabase;

    private Button adminLogin;
    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        adminLogin = (Button) findViewById(R.id.button_login_admin);
        usernameField = (EditText) findViewById(R.id.username_input_admin);
        passwordField = (EditText) findViewById(R.id.password_input_admin);
        adminDatabase = MainActivity.getAdminDatabase();

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameAdmin = usernameField.getText().toString();
                String passwordAdmin = passwordField.getText().toString();

                if(usernameAdmin.equals("") || passwordAdmin.equals("")){
                    Toast.makeText(LoginActivityAdmin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean validateAdmin = adminDatabase.checkAdmin(usernameAdmin, passwordAdmin);

                    if (validateAdmin){
                        Toast.makeText(LoginActivityAdmin.this, "Login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), AdminWelcome.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(LoginActivityAdmin.this, "Admin does not exist!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}