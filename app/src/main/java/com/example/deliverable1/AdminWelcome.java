package com.example.deliverable1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminWelcome extends AppCompatActivity {

    TextView welcomeText;
    Button toNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        welcomeText = findViewById(R.id.admin_placeholder_text);

        welcomeText.setText(R.string.welcome_admin);

        toNextPage = findViewById(R.id.button_admin_welcome);

        toNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);

                startActivity(intent);
            }
        });

    }
}
