package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class SummaryPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);

        TextView currappt = findViewById(R.id.currentApptTextviewSummaryPage);
        TextView pastappt = findViewById(R.id.pastApptTextviewSummaryPage);
        String username = getIntent().getStringExtra("username");
        Calendar cal = Calendar.getInstance();
        int currmonth = cal.get(Calendar.MONTH) + 1;
        int currday = cal.get(Calendar.DAY_OF_MONTH);
        int currhour = cal.get(Calendar.HOUR_OF_DAY);
        DBHelper db = new DBHelper(this);
        String resultCurrentAppt = db.getCurrentAppointments(username,currmonth,currday,currhour);
        String resultPastAppt = db.getPastAppointments(username,currmonth,currday,currhour);
        currappt.setText(resultCurrentAppt);
        pastappt.setText(resultPastAppt);
    }
}