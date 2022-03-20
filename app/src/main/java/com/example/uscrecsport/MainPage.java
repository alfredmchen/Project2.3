package com.example.uscrecsport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        String username = getIntent().getStringExtra("username");
        Button lyon = findViewById(R.id.lyon_button);
        Button village = findViewById(R.id.village_button);
        Button summaryPage = findViewById(R.id.summarypagebutton);
        TextView welcome = findViewById(R.id.welcomeusertextview);
        TextView currentAppt = findViewById(R.id.currentAppointmentTextView);
        DBHelper db = new DBHelper(this);
        Calendar cal = Calendar.getInstance();
        int currmonth = cal.get(Calendar.MONTH) + 1;
        int currday = cal.get(Calendar.DAY_OF_MONTH);
        int currhour = cal.get(Calendar.HOUR_OF_DAY);



        String resultCurrentAppt = db.getCurrentAppointments(username,currmonth,currday,currhour);
        currentAppt.setText(resultCurrentAppt);
        String welcometxt = "Welcome " + username + "\n" + Integer.toString(currmonth) + "/" + Integer.toString(currday) + " " + Integer.toString(currhour);
        welcome.setText(welcometxt);
        currentAppt.setMovementMethod(new ScrollingMovementMethod());


        lyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, BookingPage.class);
                intent.putExtra("gymName","lyon");
                intent.putExtra("username",username);
                intent.putExtra("currmonth", currmonth);
                intent.putExtra("currday", currday);
                intent.putExtra("currhour", currhour);
                startActivity(intent);
            }
        });

        village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, BookingPage.class);
                intent.putExtra("gymName","village");
                intent.putExtra("username",username);
                intent.putExtra("currmonth", currmonth);
                intent.putExtra("currday", currday);
                intent.putExtra("currhour", currhour);
                startActivity(intent);
            }
        });

        summaryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, SummaryPage.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }
}