package com.example.uscrecsport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

public class VillageBookingPage extends AppCompatActivity {
    Button mon_10;
    CalendarView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String gymName = getIntent().getStringExtra("gymName");
        setContentView(R.layout.activity_village_booking_page);
        cv = (CalendarView) findViewById(R.id.villagecalendar);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                Intent intent = new Intent(VillageBookingPage.this,AppointmentBookingPage.class);
                intent.putExtra("gymName", gymName);
                intent.putExtra("year", Integer.toString(year));
                intent.putExtra("month", Integer.toString(month+1));
                intent.putExtra("date", Integer.toString(date));
                startActivity(intent);
            }
        });


    }
}