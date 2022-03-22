package com.example.uscrecsport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.List;

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

    @Override
    public void onBackPressed() {
        DBHelper db = new DBHelper(this);

        String username = getIntent().getStringExtra("username");
        String currmonth = getIntent().getStringExtra("currmonth");
        String currday = getIntent().getStringExtra("currday");
        String currhour = getIntent().getStringExtra("currhour");

        TextView currentAppt = findViewById(R.id.currentAppointmentTextView);
        List<Appointment> resultCurrentAppt = db.getCurrentAppointments(username,Integer.valueOf(currmonth), Integer.valueOf(currday),Integer.valueOf(currhour));
        String resultCurAppt = "Current Appointments: \n";
        if(resultCurrentAppt.isEmpty()){
            resultCurAppt += "No current appointments";
        }
        for(int i = 0; i < resultCurrentAppt.size();i++){
            String temp = "";
            temp += (resultCurrentAppt.get(i).getRecCenter() + ": " + resultCurrentAppt.get(i).getMonth() + "/" + resultCurrentAppt.get(i).getDate() + " " + resultCurrentAppt.get(i).getTime() + ":00 \n");
            resultCurAppt += temp;
        }
        currentAppt.setText(resultCurAppt);

        Intent intent = new Intent(BookingPage.this, MainPage.class);
        startActivity(intent);
    }
}