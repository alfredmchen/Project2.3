package com.example.uscrecsport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class BookingPage extends AppCompatActivity {
    CalendarView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        String gymName = getIntent().getStringExtra("gymName");
        String username = getIntent().getStringExtra("username");
        TextView title = (TextView) findViewById(R.id.booking_page_textview);
        if (gymName.equals("village")) {
            title.setText("Village Gym Booking Page");
        }else{
            title.setText("Lyon Gym Booking Page");
        }

        cv = (CalendarView) findViewById(R.id.villagecalendar);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                Intent intent = new Intent(BookingPage.this,AppointmentBookingPage.class);
                intent.putExtra("gymName", gymName);
                intent.putExtra("username",username);
                intent.putExtra("year", Integer.toString(year));
                intent.putExtra("month", Integer.toString(month+1));
                intent.putExtra("date", Integer.toString(date));
                startActivity(intent);
            }
        });


    }
}