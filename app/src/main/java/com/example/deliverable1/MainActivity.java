package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    Button buttonInstructor;
    Button buttonAdmin;
    Button buttonMember;
    private static AdminDatabase adminDatabase;
    private static InstructorDatabase instructorDatabase;
    private static MemberDatabase memberDatabase;
    private static ClassDatabase classDatabase;
    // creating databases in main so they persist

    private static String[] decDay = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static String[] dDuration = new String[] {"0.5 hours", "1 hour", "1.5 hours", "2 hours", "2.5 hours", "3 hours"};
    private static String[] dDifficulty = new String[] {"Beginner", "Intermediate", "Advanced"};
    private static String[] dTime = new String[] {"07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
    // for use in setting default spinner values when editing classes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonInstructor = findViewById(R.id.button_Instructor);
        buttonAdmin = findViewById(R.id.button_Admin);
        buttonMember = findViewById(R.id.button_Member);

        adminDatabase = new AdminDatabase(this);
        instructorDatabase = new InstructorDatabase(this);
        memberDatabase = new MemberDatabase(this);
        classDatabase = new ClassDatabase(this);

        buttonInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("role", "instructor");
                MainActivity.this.startActivity(intent);
            }
        });

        buttonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("role", "member");
                MainActivity.this.startActivity(intent);
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, LoginActivityAdmin.class);
                MainActivity.this.startActivity(intent2);
            }
        });

    }

    public static Integer[] timeConversion(String time, String duration) {
        char[] charHold = time.toCharArray();
        String timeHold = Character.toString(charHold[0]) + Character.toString(charHold[1]) + Character.toString(charHold[3]) + Character.toString(charHold[4]);
        int nStart = Integer.parseInt(timeHold);
        if (charHold[3] == '3') {
            nStart = nStart + 20;
        }

        charHold = duration.toCharArray();
        String durHold = Character.toString(charHold[0]);
        int nLength = Integer.parseInt(durHold) * 100;


        if(charHold[2] != 'h') {
            nLength = nLength + 50;
        }

        return new Integer[] {nStart, nLength};
    }

    public static String[] getdecDay() {
        return decDay;
    }

    public static String[] getdDuration() {
        return dDuration;
    }

    public static String[] getdDifficulty() {
        return dDifficulty;
    }

    public static String[] getdTime() {
        return dTime;
    }

    public static AdminDatabase getAdminDatabase() {
        return adminDatabase;
    }

    public static InstructorDatabase getInstructorDatabase() {
        return instructorDatabase;
    }

    public static MemberDatabase getMemberDatabase() {
        return memberDatabase;
    }

    public static ClassDatabase getClassDatabase() {
        return classDatabase;
    }


}