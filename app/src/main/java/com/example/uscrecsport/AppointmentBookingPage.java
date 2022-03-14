package com.example.uscrecsport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AppointmentBookingPage extends AppCompatActivity {

    TextView titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking_page);
        Intent temp = getIntent();
        String gymName = "";
        String username = "";
        String apptyear = "";
        String apptmonth ="";
        String apptdate = "";
        if(temp.getStringExtra("username") != null){
            username = temp.getStringExtra("username");
        }
        if(temp.getStringExtra("gymName") != null){
            gymName = temp.getStringExtra("gymName");
        }
        if(temp.getStringExtra("year") != null){
            apptyear = temp.getStringExtra("year");
        }
        if(temp.getStringExtra("month") != null){
            apptmonth = temp.getStringExtra("month");
        }
        if(temp.getStringExtra("date") != null){
            apptdate = temp.getStringExtra("date");
        }
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(apptmonth + " / " + apptdate + " Appointment Times");
    }
}